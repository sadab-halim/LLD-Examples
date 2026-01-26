# Decorator Examples

- [Examples](#examples)
- [Evolution from LLD to HLD](#evolution-from-lld-to-hld)
- [How to Transition from LLD to HLD in an Interview (Using Existing Designs)](#how-to-transition-from-lld-to-hld-in-an-interview-using-existing-designs)

# Examples

<details>
    <summary>Example 1: Multi-Layered Data Caching Service</summary>

**Domain:** Distributed Systems / Infrastructure

1. **Context / Problem**
    * **Scenario:** Design an in-memory caching library (like a simplified Guava or Caffeine) used by multiple teams.
    * **Constraints:** Different teams need different eviction policies (LRU, LFU, FIFO), expiration strategies (Time-to-Live, Time-to-Idle), and instrumentation (metrics logging).
    * **Requirement:** Users should be able to mix and match behaviors (e.g., "LRU with TTL" or "LFU with Metrics") without the library maintainers writing a unique class for every permutation (`LRUCacheWithTTL`, `LFUCacheWithMetrics`, etc.).
2. **Why This Pattern Fits**
    * **Reasoning:** Behavior needs to be composed dynamically at runtime. Inheritance would lead to a combinatorial class explosion.
    * **Alternatives Rejected:** *Inheritance* (rigid, prevents mixing policies easily). *Strategy Pattern* (better for swapping one algorithm, but harder to stack multiple independent behaviors like logging + eviction + expiration simultaneously).
    * **Trade-offs:** Adds complexity to the object creation process (Client needs to build the chain). Slight performance overhead due to indirection, but negligible for I/O bound or network-bound cache misses.
3. **Key Classes / Interfaces**
    * `Cache<K, V>`: The core interface (methods: `put`, `get`, `evict`).
    * `SimpleCache<K, V>`: Concrete implementation (simple `HashMap` backing).
    * `CacheDecorator<K, V>`: Abstract decorator implementing `Cache` and holding a `Cache` reference.
    * `LRUEvictionDecorator`: Adds LRU logic on top of any cache.
    * `TTLDecorator`: Adds expiration logic checks on `get()`.
    * `MetricsDecorator`: Wraps calls to emit latency/hit-rate metrics.
4. **How the Pattern Is Applied (LLD View)**
    * The client instantiates the base cache: `Cache c = new SimpleCache(...)`.
    * To add LRU: `c = new LRUEvictionDecorator(c)`.
    * To add Metrics: `c = new MetricsDecorator(c)`.
    * When `c.get(key)` is called:

5. `MetricsDecorator` starts a timer, calls `delegate.get(key)`, stops timer, records metric.
6. `LRUEvictionDecorator` (the delegate) updates the access order, then calls its delegate.
7. `SimpleCache` returns the value.
1. **Interview Insight (FAANG-Specific)**
    * **Evaluation:** Are you handling the "stacking" of responsibilities correctly?
    * **Weak Signal:** Creating a "God Class" `ConfigurableCache` with boolean flags (`enableLRU`, `enableTTL`) instead of decorators.
    * **Follow-up:** "How do you handle thread safety if each decorator maintains its own state (like an LRU list)?"

**Focus:** Shows how behaviors (TTL, Metrics) are stacked via composition around a core storage component.

**UML Diagram**:

![img.png](img.png)

[**Code**]()

</details>

---

<details>
    <summary>Example 2: Resilient HTTP Client Wrapper</summary>

**Domain:** Microservices Communication

1. **Context / Problem**
    * **Scenario:** You are building an SDK for internal services to communicate with each other. The core HTTP logic is standard, but different services require different resilience patterns.
    * **Constraints:** Some services need automatic Retries. Others need Circuit Breakers. Some need Auth token injection. Some need all three. The order of execution matters (e.g., Auth header injection must happen before the Retry logic).
    * **Requirement:** The consumption code should look identical regardless of the resilience features enabled.
2. **Why This Pattern Fits**
    * **Reasoning:** Cross-cutting concerns (logging, auth, resilience) should be decoupled from the business logic of making a request.
    * **Alternatives Rejected:** *Proxy Pattern* (usually static at compile time or 1:1 relationship, whereas we need 1:N chaining). *Aspect-Oriented Programming (AOP)* (Valid, but often "magic" and harder to debug in a pure LLD interview context).
    * **Trade-offs:** Debugging stack traces can become deep and confusing.
3. **Key Classes / Interfaces**
    * `HttpClient`: Interface (`Response send(Request req)`).
    * `BaseHttpClient`: Concrete implementation (wraps actual network call).
    * `HttpClientDecorator`: Abstract base.
    * `RetryDecorator`: Implements exponential backoff logic.
    * `CircuitBreakerDecorator`: Tracks failure rates and "opens" the circuit if threshold crossed.
    * `OAuthDecorator`: Injects `Authorization: Bearer <token>` header.
4. **How the Pattern Is Applied (LLD View)**
    * The ordering is critical here.
    * `client = new CircuitBreakerDecorator(new RetryDecorator(new OAuthDecorator(new BaseHttpClient())))`.
    * When `send()` is called:

5. **Circuit Breaker** checks state. If closed, proceeds.
6. **Retry** wraps the next call in a `try/catch` loop.
7. **OAuth** modifies the `Request` object to add headers.
8. **Base** executes the network call.
1. **Interview Insight (FAANG-Specific)**
    * **Evaluation:** Do you understand that the *order* of wrapping affects behavior? (e.g., if you wrap Retry *outside* Circuit Breaker, the Retry might hammer an open circuit).
    * **Follow-up:** "How would you configure these decorators dynamically based on a config file reload?"

**Focus:** Highlights the importance of ordering. The diagram visually suggests that `CircuitBreaker` wraps `Retry`, which wraps `Auth`, which wraps the `BaseHttpClient`.

**UML Diagram**:

![img_1.png](img_1.png)

[**Code**]()

</details>

---

<details>
    <summary>Example 3: Notification Service with Channel Fallback</summary>

**Domain:** Product Notification Platform

1. **Context / Problem**
    * **Scenario:** Design a notification system that sends alerts to users.
    * **Constraints:** Users have preferences (SMS, Email, Push). Critical alerts (e.g., "Account Hacked") have "fallback" logic: try Push first; if it fails, send SMS; if that fails, send Email.
    * **Requirement:** The core system just wants to "send a notification." The logic for fallback or multi-channel broadcasting should be abstracted away.
2. **Why This Pattern Fits**
    * **Reasoning:** We are "decorating" the sending behavior. The core behavior is "send," but we need to augment it with "send on secondary channel if primary fails."
    * **Alternatives Rejected:** *Chain of Responsibility* (This is a close contender, but CoR usually implies "one handler handles it and stops." Here, we might want "broadcast" or "fallback" logic which is structurally closer to decoration/wrapping).
    * **Trade-offs:** Can be confusing to distinguish between "decorating" behavior and purely business logic routing.
3. **Key Classes / Interfaces**
    * `Notifier`: Interface (`void send(Message m)`).
    * `EmailNotifier`, `SMSNotifier`: Concrete implementations.
    * `FallbackNotifier`: A decorator that takes *two* Notifiers: a primary and a secondary.
    * `DeduplicationNotifier`: Prevents sending the same message ID twice within a window.
4. **How the Pattern Is Applied (LLD View)**
    * We can compose a complex policy: `new FallbackNotifier(new PushNotifier(), new SMSNotifier())`.
    * The `FallbackNotifier` implementation of `send()`:

```java
try {
    primary.send(msg);
} catch (Exception e) {
    secondary.send(msg);
}
```

    * This allows arbitrary chaining: A fallback to SMS, which itself falls back to Email.
5. **Interview Insight (FAANG-Specific)**
    * **Evaluation:** This tests if you can adapt the pattern. Standard Decorator has *one* delegate. Here, `FallbackNotifier` might technically hold *two* (primary and fallback), yet it still fulfills the Decorator intent (augmenting the `send` capability).
    * **Weak Signal:** Hardcoding the fallback sequence (`if push fails then sms`) inside a `NotificationManager` class rather than composing it.

**Focus:** This diagram demonstrates a variation where the Decorator holds *two* references (Primary and Secondary) to enable the fallback logic, while still adhering to the core `Notifier` interface.

**UML Diagram**:

![img_2.png](img_2.png)

[**Code**]()

</details>

---

<details>
    <summary>Example 4: Pizza/Coffee Pricing (The Reframed "Textbook" Example)</summary>

**Domain:** E-commerce / Cart Pricing Engine

1. **Context / Problem**
    * **Scenario:** Design the pricing engine for a food delivery app (e.g., UberEats/DoorDash).
    * **Constraints:** Items have base prices. There are endless dynamic modifiers: "Extra Cheese," "Gluten-Free Crust" (surcharge), "Lunch Special" (discount), "Surge Pricing" (multiplier).
    * **Requirement:** We need to calculate the final cost and generate a receipt description line.
2. **Why This Pattern Fits**
    * **Reasoning:** This is the canonical example for a reason—it fits perfectly. However, in an interview, you must focus on the *receipt generation* and *price composition*, not just "adding string descriptions."
    * **Alternatives Rejected:** *Database Lookup Tables* (Valid for simple attributes, but hard for algorithmic modifiers like "10% off if order > \$20").
    * **Trade-offs:** The object graph can become deep for a complex order (a pizza with 15 toppings = 15 wrappers).
3. **Key Classes / Interfaces**
    * `OrderItem`: Interface (`double getCost()`, `String getDescription()`).
    * `BaseItem`: Concrete (Pizza, Burger).
    * `ItemDecorator`: Abstract.
    * `TaxDecorator`: Adds percentage to cost.
    * `ToppingDecorator`: Adds fixed amount.
    * `DiscountDecorator`: Subtracts amount or percentage.
4. **How the Pattern Is Applied (LLD View)**
    * `OrderItem item = new Pizza()`.
    * `item = new Cheese(item)` -> `getCost()` returns `base + cheese`.
    * `item = new Discount(item)` -> `getCost()` returns `(base + cheese) * 0.9`.
    * Crucially, `getDescription()` chains recursively: "Pizza, Extra Cheese, Discount Applied".
5. **Interview Insight (FAANG-Specific)**
    * **Evaluation:** Efficiency.
    * **Follow-up:** "Realistically, creating 20 objects for one burger is bad for memory in Java. How would you optimize this?"
    * **Staff-Level Answer:** Acknowledge the pattern is good for *modeling* logic, but in production, we might use a hybrid approach: A `List<Modifier>` inside the `OrderItem` class (Strategy pattern) is often more memory-efficient than pure Decorator recursion for simple pricing data.

**Focus:** Illustrates the recursive nature of the pattern. Unlike the previous examples which largely focused on *void* side-effects or flow control, this one emphasizes **aggregating return values** (summing costs and concatenating strings).

**UML Diagram**:

![img_3.png](img_3.png)

[**Code**]()

</details>

---

<details>
    <summary>Example 5: Data Stream Processing / Compression Pipeline</summary>

**Domain:** Big Data / Logging Infrastructure

1. **Context / Problem**
    * **Scenario:** Design a logging library that writes application logs to a centralized file system (e.g., S3 or HDFS).
    * **Constraints:** Logs contain PII (Personally Identifiable Information) that must be masked. Logs must be compressed to save space. Logs must be encrypted for security.
    * **Requirement:** The application developer just calls `logger.write("msg")`. The infrastructure controls whether masking, compression, or encryption happens, and in what order.
2. **Why This Pattern Fits**
    * **Reasoning:** This is a classic "Stream" problem. We are transforming data as it passes through layers.
    * **Alternatives Rejected:** *Inheritance* (`EncryptedCompressedMaskedLogger` is unmanageable).
    * **Trade-offs:** Debugging a corrupted stream is difficult because you don't know which layer corrupted the data without inspecting the chain.
3. **Key Classes / Interfaces**
    * `DataSource`: Interface (`void write(byte[] data)`).
    * `FileDataSource`: Writes to disk.
    * `DataSourceDecorator`: Abstract.
    * `EncryptionDecorator`: Encrypts data buffer, passes to delegate.
    * `CompressionDecorator`: GZips data buffer, passes to delegate.
    * `PIIMaskDecorator`: Regex replace sensitive patterns, passes to delegate.
4. **How the Pattern Is Applied (LLD View)**
    * Chain: `new EncryptionDecorator(new CompressionDecorator(new PIIMaskDecorator(new FileDataSource())))`.
    * Write:

5. **Mask**: "User password123" -> "User *****"
6. **Compress**: Reduces size.
7. **Encrypt**: Secures the compressed bits.
8. **File**: Writes blob.
    * Read path (Inverse): `Decrypt -> Decompress -> Read`. (Note: Masking is irreversible, so it's not on the read path).
1. **Interview Insight (FAANG-Specific)**
    * **Evaluation:** Handling of byte streams vs. character streams. Understanding that `Encryption` must usually happen *after* `Compression` (encrypting compressed data is fine; compressing encrypted data is ineffective because encryption maximizes entropy).
    * **Typical Mistake:** Getting the order wrong (Compressing encrypted data).

**Focus:** Demonstrates **data transformation** chains. Each decorator alters the data payload before passing it down. This visualizes the "Pipe and Filter" architectural style implemented via the Decorator pattern.

**UML Diagram**:

![img_4.png](img_4.png)

[**Code**]()

</details>

---

# Evolution from LLD to HLD

<details>
    <summary>Example 1: Multi-Layered Data Caching Service</summary>

**Recap Anchor:** An in-memory `Cache<K,V>` interface with `TTLDecorator` and `MetricsDecorator`.

* **HLD Pressure Point:**
    * **Memory Bound:** A single JVM heap cannot hold terabytes of data.
    * **Cold Start:** If the application instance restarts, the in-memory cache is empty, causing a spike in database load (Thundering Herd).
    * **GC Pauses:** Large Java heaps (>32GB) cause significant Garbage Collection pauses, increasing tail latency (p99).
* **Boundary Evolution:**
    * The **Interface** (`Cache`) stays in the application SDK/Client Library.
    * The **Storage** (`SimpleCache`) evolves from a `HashMap` to a remote Distributed Cache (Redis/Memcached).
    * The **Decorators** split: `MetricsDecorator` remains in the client SDK (side-effect free). `TTLDecorator` logic often moves to the Redis server (using native TTL features) to reduce network payload.
* **Contract Evolution:**
    * `get(key)` is no longer a memory look-up; it becomes a network RPC.

```
*   **Serialization:** The values `<V>` must now be serializable (Protobuf/Thrift/JSON) to be sent over the wire. The interface might change to return `Future<V>` or `CompletableFuture<V>` to handle network latency asynchronously.
```

* **Scaling \& Failure:**
    * **Sharding:** The client library must now use Consistent Hashing to pick which Redis node to talk to.
    * **Coalescing:** A new decorator, `RequestCoalescingDecorator`, is needed to prevent 10,000 threads from asking for the same missing key simultaneously.
* **Interview Insight:**
    * **Strong Signal:** Moving the *eviction logic* (LRU/TTL) from the Java application (Client) to the Cache Server (Infrastructure) to save CPU/Memory on the app server.

</details>

---

<details>
    <summary>Example 2: Resilient HTTP Client Wrapper</summary>

**Recap Anchor:** `CircuitBreaker` -> `Retry` -> `Auth` wrapping a basic HTTP client.

* **HLD Pressure Point:**
    * **Config Drift:** Updating a timeout or retry policy requires redeploying the entire application.
    * **Lack of Global View:** A local circuit breaker only knows if *this* instance is failing. It doesn't know if the downstream service is down globally.
    * **Polyglot Environments:** If other teams use Python/Go, they have to rewrite these decorators from scratch.
* **Boundary Evolution:**
    * The logic inside the Java Decorators is extracted out of the process entirely into a **Service Mesh Sidecar** (e.g., Envoy, Istio).
    * The Java code reverts to a simple `BaseHttpClient`. The "Decoration" happens at the network infrastructure layer (localhost proxy).
* **Contract Evolution:**
    * The application simply calls `http://inventory-service`.
    * The **Sidecar** intercepts this call and applies Retries, Circuit Breaking, and Auth headers transparently.
    * Configuration is now pushed via a Control Plane (YAML/JSON) rather than Java constructor arguments.
* **Scaling \& Failure:**
    * **Distributed Tracing:** The Auth/Header decorator must now inject Trace IDs (OpenTelemetry) so requests can be tracked across the mesh.
    * **Failover:** If the sidecar is down, the application needs a simplified "fallback" decorator to bypass the mesh or fail gracefully.
* **Interview Insight:**
    * **Strong Signal:** Acknowledging that for large-scale microservices, "Smart Pipes (Service Mesh) and Dumb Endpoints" is often preferred over heavy client-side libraries (Fat Client).

</details>

---

<details>
    <summary>Example 3: Notification Service with Channel Fallback</summary>

**Recap Anchor:** `FallbackNotifier` that tries Email, catches exception, then tries SMS.

* **HLD Pressure Point:**
    * **Blocking:** Synchronous fallbacks hold the thread. If the Email provider takes 30s to timeout, the SMS isn't sent for 30s.
    * **Data Loss:** If the server crashes during the "try/catch" block, the notification is lost forever.
    * **Rate Limiting:** Sending SMS too fast in a loop will get your API keys banned by vendors (Twilio/AWS SNS).
* **Boundary Evolution:**
    * The `FallbackNotifier` moves from a Java class to a **Workflow/Orchestration Engine** (e.g., Temporal, Cadence, or AWS Step Functions).
    * The "Decorator" becomes a "Workflow Step."
* **Contract Evolution:**
    * `send(msg)` no longer calls a provider. It pushes an event to a Message Queue (Kafka/SQS).
    * **Workers** consume the message. If Email fails, they NACK (negative acknowledgement) or push to a "Retry-SMS" topic.
    * The "fallback" is no longer an `if/else` or `try/catch` in code; it is a routing rule in the queue consumer.
* **Scaling \& Failure:**
    * **Idempotency:** Since we are now using queues, the system must handle duplicate messages (At-least-once delivery).
    * **Isolation:** Separate queues for Email and SMS so a backlog in Emails doesn't delay urgent SMS OTPs (Bulkhead Pattern).
* **Interview Insight:**
    * **Strong Signal:** Transforming synchronous procedural fallback logic into asynchronous event-driven workflows for reliability.

</details>

---

<details>
    <summary>Example 4: Pizza/Coffee Pricing (Dynamic Receipt)</summary>

**Recap Anchor:** Recursive object composition (`new Cheese(new Pizza())`) to calculate cost.

* **HLD Pressure Point:**
    * **Static Logic vs. Dynamic Biz Rules:** Marketing wants to change "10% off" to "15% off" on Friday nights without a code deployment.
    * **Database Consistency:** The base price of a Pizza lives in a DB, but the decorator hardcodes it or fetches it inefficiently.
    * **Reporting:** Analytics needs to query "How much extra cheese did we sell?" querying nested Java objects is impossible; we need structured data.
* **Boundary Evolution:**
    * The Decorator pattern is replaced or augmented by a **Rule Engine** (Drools) or a Pricing Service.
    * The "Decorator" structure persists only as a **Data Model** (JSON document structure: `items: [{ id: pizza, modifiers: [cheese, discount] }]`).
* **Contract Evolution:**
    * The Client sends a `Cart` object to the `PricingService`.
    * The Service hydrates the `Cart` with prices from a DB (Redis/SQL).
    * The "Decoration" is the act of the Rule Engine applying modifiers to the base price in memory before returning the response.
* **Scaling \& Failure:**
    * **Read Replicas:** Menu prices are read-heavy. Cache them aggressively.
    * **Versioning:** Pricing rules need versioning. An order placed at 5:59 PM must retain the 5:59 PM price even if processed at 6:01 PM.
* **Interview Insight:**
    * **Strong Signal:** Recognizing that *structural patterns* (Decorator) are great for code organization but *data-driven patterns* (Rule Engines) are better for business logic that changes frequently.

</details>

---

<details>
    <summary>Example 5: Data Stream Processing Pipeline</summary>

**Recap Anchor:** `PIIMask` -> `Compress` -> `Encrypt` decorators on an Output Stream.

* **HLD Pressure Point:**
    * **Throughput:** A single Java thread processing a stream is CPU bound (encryption is heavy).
    * **Backpressure:** If the disk (Sink) is slow, the application memory fills up with buffered logs.
    * **Durability:** If the process dies while buffering compressed chunks, logs are lost.
* **Boundary Evolution:**
    * The "Decorators" evolve into **Stream Processing Topology** (e.g., Kafka Streams, Apache Flink, or Spark Streaming).
    * `PIIMaskDecorator` becomes a "Map" operation in Flink.
    * `EncryptDecorator` becomes a dedicated secure processor or sidecar.
* **Contract Evolution:**
    * The interface changes from `write(byte[])` to `producer.send(topic, message)`.
    * The chain of decorators becomes a DAG (Directed Acyclic Graph) of independent nodes, potentially running on different servers.
* **Scaling \& Failure:**
    * **Partitioning:** Data is sharded by UserID or RequestID so multiple nodes can compress/encrypt in parallel.
    * **Checkpointing:** The stream processor saves state to disk periodically. If a node crashes, it replays from the last checkpoint (unlike the transient Java decorator).
* **Interview Insight:**
    * **Strong Signal:** Moving from "Synchronous I/O chaining" to "Distributed Stream Processing" to handle massive scale.

</details>

---

## How to Transition from LLD to HLD in an Interview (Using Existing Designs)

When the interviewer asks, *"How would this handle 100k requests per second?"*, do not redraw your classes. Pivot using these steps:

1. **Identify the State Bottleneck:**
    * "In my LLD, the `Cache` map is local. At 100k QPS, we need to move this state out of the application heap to a distributed layer like Redis."
2. **Identify the CPU Bottleneck:**
    * "The `EncryptDecorator` is CPU intensive. In HLD, I would decouple this. The app simply writes raw events to a local agent or a high-throughput queue (Kafka), and a dedicated consumer cluster handles the heavy encryption."
3. **Identify the Configuration Bottleneck:**
    * "The `ResilienceDecorator` is great for code, but operationally, we need to change these configs at runtime. I would introduce a Control Plane to push configs to these decorators dynamically."
4. **Draw the Box:**
    * Literally draw a box around your LLD Class Diagram and label it **"Service Instance"**.
    * Draw a generic "Load Balancer" in front of it.
    * Draw a "Database/Cache" behind it.
    * Say: "The logic inside the box remains the Decorator pattern for code maintainability, but the system architecture wraps this box in a scalable infrastructure."
5. **Avoid Premature Microservices:**
    * Don't say: "I'll make the `LoggingDecorator` a separate microservice." (Latency killer).
    * Do say: "I'll keep logging in-process for speed but use an async appender to avoid blocking the main thread."

**Rule of Thumb:**

* **LLD** solves Complexity of Logic (Maintainability).
* **HLD** solves Complexity of Volume (Scalability/Reliability).
* Bridging them means identifying where Logic needs to be distributed to handle Volume.