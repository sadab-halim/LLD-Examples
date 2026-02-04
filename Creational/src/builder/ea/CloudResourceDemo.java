package builder.ea;

import java.util.Map;

public class CloudResourceDemo {
    public static void main(String[] args) {
        try {
            // Valid Instance
            ComputeInstance validInstance = new ComputeInstance.Builder()
                    .withImageId("ami-12345678")
                    .withInstanceType("g4dn.xlarge") // GPU type
                    .enableGpu(true)                 // Required for this type
                    .withTags(Map.of("Owner", "BackendTeam"))
                    .build();

            System.out.println("Provisioned: " + validInstance);

            // Invalid Instance (GPU type without enabling GPU flag)
            ComputeInstance invalidInstance = new ComputeInstance.Builder()
                    .withImageId("ami-98765432")
                    .withInstanceType("g4dn.xlarge")
                    .enableGpu(false) // This will trigger validation error
                    .build();

        } catch (IllegalStateException e) {
            System.err.println("Validation Failed: " + e.getMessage());
        }
    }
}