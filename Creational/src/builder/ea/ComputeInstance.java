package builder.ea;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class ComputeInstance {
    private final String imageId;
    private final String instanceType;
    private final boolean gpuEnabled;
    private final Map<String, String> tags;

    // Private constructor: Forces use of Builder
    private ComputeInstance(Builder builder) {
        this.imageId = builder.imageId;
        this.instanceType = builder.instanceType;
        this.gpuEnabled = builder.gpuEnabled;
        // Defensive copy for immutability
        this.tags = Collections.unmodifiableMap(new HashMap<>(builder.tags));
    }

    public String toString() {
        return String.format("Instance[AMI=%s, Type=%s, GPU=%b]", imageId, instanceType, gpuEnabled);
    }

    // Static Inner Builder
    public static class Builder {
        private String imageId;
        private String instanceType;
        private boolean gpuEnabled = false; // Default
        private Map<String, String> tags = new HashMap<>();

        public Builder withImageId(String imageId) {
            this.imageId = imageId;
            return this;
        }

        public Builder withInstanceType(String instanceType) {
            this.instanceType = instanceType;
            return this;
        }

        public Builder enableGpu(boolean enable) {
            this.gpuEnabled = enable;
            return this;
        }

        public Builder withTags(Map<String, String> tags) {
            this.tags = tags;
            return this;
        }

        public ComputeInstance build() {
            validate();
            return new ComputeInstance(this);
        }

        // Complex Validation Logic
        private void validate() {
            if (imageId == null || imageId.isEmpty()) {
                throw new IllegalStateException("AMI ID is required");
            }
            if (instanceType == null) {
                throw new IllegalStateException("Instance Type is required");
            }
            // Cross-field validation
            boolean isGpuType = instanceType.startsWith("g") || instanceType.startsWith("p");
            if (isGpuType && !gpuEnabled) {
                throw new IllegalStateException("GPU flag must be enabled for GPU instance types: " + instanceType);
            }
        }
    }
}