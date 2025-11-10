package builder.ea;

public class User {
    // final fields to ensure immutability once the object is created
    private final String username;  // required
    private final String email;     // required
    private final String firstName; // optional
    private final String lastName;  // optional
    private final int age;          // optional

    // The constructor is private, so objects can only be created using the builder.
    // It takes the builder as a parameter and initializes its fields from it.
    private User(UserBuilder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
    }

    // Public getters for the fields. No setters, to maintain immutability.
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "creational.User: " + this.username + ", " + this.email + ", " + this.firstName + ", " + this.lastName + ", " + this.age;
    }

    // The static nested Builder class.
    public static class UserBuilder {
        // It has the same fields as the creational.User class.
        // Required fields are declared final to be enforced by the builder's constructor.
        private final String username;
        private final String email;
        // Optional fields are initialized with default values.
        private String firstName = "";
        private String lastName = "";
        private int age = 0;

        // The builder's constructor only takes the required parameters.
        public UserBuilder(String username, String email) {
            this.username = username;
            this.email = email;
        }

        // Each method for an optional parameter returns the builder instance ("this").
        // This is called "method chaining" or "fluent interface".
        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        // The build() method is the final step. It calls the private creational.User
        // constructor, passing itself (the builder) as the argument.
        public User build() {
            return new User(this);
        }
    }

    //  Main class
    public static void main(String[] args) {
        // Create a user with only the required fields.
        User firstUser = new User.UserBuilder("johndoe", "john.doe@example.com").build();
        System.out.println(firstUser);

        // Create a user with all fields using method chaining.
        User secondUser = new UserBuilder("johndoe", "john.doe@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .age(30)
                .build();
        System.out.println(secondUser);
    }
}
