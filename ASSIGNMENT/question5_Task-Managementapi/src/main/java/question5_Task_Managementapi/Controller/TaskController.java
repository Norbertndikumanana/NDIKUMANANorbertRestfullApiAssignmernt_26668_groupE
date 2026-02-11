package question5_Task_Managementapi.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import question5_Task_Managementapi.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // In-memory storage for tasks
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    // Constructor - initialize with sample tasks
    public TaskController() {
        tasks.add(new Task(nextId++, "Complete project documentation", 
            "Write comprehensive API documentation for the task management system", 
            false, "HIGH", "2026-02-15"));
        
        tasks.add(new Task(nextId++, "Review pull requests", 
            "Review pending code changes from team members", 
            false, "MEDIUM", "2026-02-12"));
        
        tasks.add(new Task(nextId++, "Update dependencies", 
            "Update all project dependencies to latest stable versions", 
            true, "LOW", "2026-02-10"));
        
        tasks.add(new Task(nextId++, "Fix login bug", 
            "Resolve authentication issue in login module", 
            false, "HIGH", "2026-02-11"));
        
        tasks.add(new Task(nextId++, "Prepare presentation", 
            "Create slides for quarterly review meeting", 
            false, "MEDIUM", "2026-02-18"));
        
        tasks.add(new Task(nextId++, "Database backup", 
            "Perform weekly database backup and verification", 
            true, "HIGH", "2026-02-08"));
        
        tasks.add(new Task(nextId++, "Team meeting notes", 
            "Document action items from weekly team meeting", 
            true, "LOW", "2026-02-09"));
        
        tasks.add(new Task(nextId++, "Code refactoring", 
            "Refactor legacy code in payment processing module", 
            false, "MEDIUM", "2026-02-20"));
        
        tasks.add(new Task(nextId++, "Security audit", 
            "Conduct security audit of API endpoints", 
            false, "HIGH", "2026-02-16"));
        
        tasks.add(new Task(nextId++, "Update README", 
            "Update project README with new setup instructions", 
            false, "LOW", "2026-02-14"));
        
        tasks.add(new Task(nextId++, "Performance testing", 
            "Run performance tests on new features", 
            true, "MEDIUM", "2026-02-07"));
    }

    // GET /api/tasks - Get all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // GET /api/tasks/{taskId} - Get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();

        if (task.isPresent()) {
            return new ResponseEntity<>(task.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/tasks/status?completed={true/false} - Get tasks by completion status
    @GetMapping("/status")
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam boolean completed) {
        List<Task> foundTasks = tasks.stream()
                .filter(t -> t.isCompleted() == completed)
                .collect(Collectors.toList());

        if (foundTasks.isEmpty()) {
            return new ResponseEntity<>(foundTasks, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundTasks, HttpStatus.OK);
    }

    // GET /api/tasks/priority/{priority} - Get tasks by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        List<Task> foundTasks = tasks.stream()
                .filter(t -> t.getPriority().equalsIgnoreCase(priority))
                .collect(Collectors.toList());

        if (foundTasks.isEmpty()) {
            return new ResponseEntity<>(foundTasks, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(foundTasks, HttpStatus.OK);
    }

    // POST /api/tasks - Create new task
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        task.setTaskId(nextId++);
        
        // Set defaults if not provided
        if (task.getPriority() == null || task.getPriority().isEmpty()) {
            task.setPriority("MEDIUM");
        } else {
            task.setPriority(task.getPriority().toUpperCase());
        }
        
        tasks.add(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    // PUT /api/tasks/{taskId} - Update task
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long taskId,
            @RequestBody Task updatedTask) {
        
        Optional<Task> existingTask = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();

        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            
            // Update all fields except ID
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            task.setPriority(updatedTask.getPriority() != null ? 
                updatedTask.getPriority().toUpperCase() : task.getPriority());
            task.setDueDate(updatedTask.getDueDate());

            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PATCH /api/tasks/{taskId}/complete - Mark task as completed
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long taskId) {
        Optional<Task> existingTask = tasks.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst();

        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setCompleted(true);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/tasks/{taskId} - Delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean removed = tasks.removeIf(t -> t.getTaskId().equals(taskId));

        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
