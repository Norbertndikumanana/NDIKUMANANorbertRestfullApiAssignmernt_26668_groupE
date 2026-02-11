package question5_Task_Managementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Question5TaskManagementapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Question5TaskManagementapiApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("✓ Task Management API Started Successfully!");
        System.out.println("========================================");
        System.out.println("Server URL: http://localhost:8085");
        System.out.println("API Base:   http://localhost:8085/api/tasks");
        System.out.println("========================================\n");
        System.out.println("Available Endpoints:");
        System.out.println("  GET    /api/tasks");
        System.out.println("  GET    /api/tasks/{taskId}");
        System.out.println("  GET    /api/tasks/status?completed={true/false}");
        System.out.println("  GET    /api/tasks/priority/{priority}");
        System.out.println("  POST   /api/tasks");
        System.out.println("  PUT    /api/tasks/{taskId}");
        System.out.println("  PATCH  /api/tasks/{taskId}/complete");
        System.out.println("  DELETE /api/tasks/{taskId}");
        System.out.println("========================================\n");
    }
}