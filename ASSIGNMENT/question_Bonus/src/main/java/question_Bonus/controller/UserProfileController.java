package question_Bonus.controller;


import question_Bonus.UserProfile;
import question_Bonus.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    // In-memory storage for user profiles
    private List<UserProfile> users = new ArrayList<>();
    private Long nextId = 1L;

    // Constructor - initialize with sample user profiles
    public UserProfileController() {
        users.add(new UserProfile(nextId++, "john_doe", "john@example.com", "John Doe", 28, "USA", "Software developer passionate about Spring Boot", true));
        users.add(new UserProfile(nextId++, "jane_smith", "jane@example.com", "Jane Smith", 32, "UK", "Data scientist and ML enthusiast", true));
        users.add(new UserProfile(nextId++, "mike_jones", "mike@example.com", "Mike Jones", 25, "Canada", "Full-stack developer", true));
        users.add(new UserProfile(nextId++, "sarah_wilson", "sarah@example.com", "Sarah Wilson", 29, "USA", "UX/UI designer", true));
        users.add(new UserProfile(nextId++, "david_brown", "david@example.com", "David Brown", 35, "Australia", "DevOps engineer", false));
        users.add(new UserProfile(nextId++, "emily_davis", "emily@example.com", "Emily Davis", 27, "UK", "Frontend developer", true));
        users.add(new UserProfile(nextId++, "alex_martinez", "alex@example.com", "Alex Martinez", 30, "Spain", "Backend developer", true));
        users.add(new UserProfile(nextId++, "lisa_anderson", "lisa@example.com", "Lisa Anderson", 26, "USA", "Mobile app developer", true));
        users.add(new UserProfile(nextId++, "tom_taylor", "tom@example.com", "Tom Taylor", 40, "Canada", "Tech lead and architect", false));
        users.add(new UserProfile(nextId++, "maria_garcia", "maria@example.com", "Maria Garcia", 24, "Mexico", "Junior developer", true));
    }

    // GET /api/users - Get all user profiles
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUsers() {
        return ResponseEntity.ok(
            ApiResponse.success("User profiles retrieved successfully", users)
        );
    }

    // GET /api/users/{userId} - Get user profile by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserById(@PathVariable Long userId) {
        Optional<UserProfile> user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (user.isPresent()) {
            return ResponseEntity.ok(
                ApiResponse.success("User profile found", user.get())
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("User profile not found with ID: " + userId)
            );
        }
    }

    // GET /api/users/username/{username} - Search by username
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserByUsername(@PathVariable String username) {
        Optional<UserProfile> user = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();

        if (user.isPresent()) {
            return ResponseEntity.ok(
                ApiResponse.success("User found by username", user.get())
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("User not found with username: " + username)
            );
        }
    }

    // GET /api/users/country/{country} - Search by country
    @GetMapping("/country/{country}")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getUsersByCountry(@PathVariable String country) {
        List<UserProfile> foundUsers = users.stream()
                .filter(u -> u.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());

        if (foundUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("No users found from country: " + country)
            );
        }

        return ResponseEntity.ok(
            ApiResponse.success("Users found from " + country, foundUsers)
        );
    }

    // GET /api/users/age-range?min={min}&max={max} - Search by age range
    @GetMapping("/age-range")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getUsersByAgeRange(
            @RequestParam int min,
            @RequestParam int max) {
        
        List<UserProfile> foundUsers = users.stream()
                .filter(u -> u.getAge() >= min && u.getAge() <= max)
                .collect(Collectors.toList());

        if (foundUsers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("No users found in age range " + min + "-" + max)
            );
        }

        return ResponseEntity.ok(
            ApiResponse.success("Users found in age range " + min + "-" + max, foundUsers)
        );
    }

    // GET /api/users/active - Get only active users
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getActiveUsers() {
        List<UserProfile> activeUsers = users.stream()
                .filter(UserProfile::isActive)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
            ApiResponse.success("Active users retrieved successfully", activeUsers)
        );
    }

    // GET /api/users/inactive - Get only inactive users
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<UserProfile>>> getInactiveUsers() {
        List<UserProfile> inactiveUsers = users.stream()
                .filter(u -> !u.isActive())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
            ApiResponse.success("Inactive users retrieved successfully", inactiveUsers)
        );
    }

    // POST /api/users - Create new user profile
    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> createUser(@RequestBody UserProfile user) {
        // Check if username already exists
        boolean usernameExists = users.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));

        if (usernameExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.error("Username already exists: " + user.getUsername())
            );
        }

        user.setUserId(nextId++);
        user.setActive(true); // New users are active by default
        users.add(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success("User profile created successfully", user)
        );
    }

    // PUT /api/users/{userId} - Update user profile
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUser(
            @PathVariable Long userId,
            @RequestBody UserProfile updatedUser) {
        
        Optional<UserProfile> existingUser = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (existingUser.isPresent()) {
            UserProfile user = existingUser.get();
            
            // Update all fields except userId
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFullName(updatedUser.getFullName());
            user.setAge(updatedUser.getAge());
            user.setCountry(updatedUser.getCountry());
            user.setBio(updatedUser.getBio());
            user.setActive(updatedUser.isActive());

            return ResponseEntity.ok(
                ApiResponse.success("User profile updated successfully", user)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("User profile not found with ID: " + userId)
            );
        }
    }

    // PATCH /api/users/{userId}/activate - Activate user profile
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<UserProfile>> activateUser(@PathVariable Long userId) {
        Optional<UserProfile> existingUser = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (existingUser.isPresent()) {
            UserProfile user = existingUser.get();
            
            if (user.isActive()) {
                return ResponseEntity.ok(
                    ApiResponse.success("User profile is already active", user)
                );
            }
            
            user.setActive(true);
            return ResponseEntity.ok(
                ApiResponse.success("User profile activated successfully", user)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("User profile not found with ID: " + userId)
            );
        }
    }

    // PATCH /api/users/{userId}/deactivate - Deactivate user profile
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<ApiResponse<UserProfile>> deactivateUser(@PathVariable Long userId) {
        Optional<UserProfile> existingUser = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (existingUser.isPresent()) {
            UserProfile user = existingUser.get();
            
            if (!user.isActive()) {
                return ResponseEntity.ok(
                    ApiResponse.success("User profile is already inactive", user)
                );
            }
            
            user.setActive(false);
            return ResponseEntity.ok(
                ApiResponse.success("User profile deactivated successfully", user)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("User profile not found with ID: " + userId)
            );
        }
    }

    // DELETE /api/users/{userId} - Delete user profile
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        boolean removed = users.removeIf(u -> u.getUserId().equals(userId));

        if (removed) {
            return ResponseEntity.ok(
                ApiResponse.success("User profile deleted successfully", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error("User profile not found with ID: " + userId)
            );
        }
    }
}
