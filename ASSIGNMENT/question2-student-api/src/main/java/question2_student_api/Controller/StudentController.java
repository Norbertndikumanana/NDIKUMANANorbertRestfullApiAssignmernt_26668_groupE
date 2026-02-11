package question2_student_api.Controller;

import question2_student_api.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    // In-memory storage for students
    private List<Student> students = new ArrayList<>();
    private Long nextId = 1L;

    // Constructor - initialize with 5 sample students
    public StudentController() {
        students.add(new Student(nextId++, "John", "Doe", "john.doe@university.edu", "Computer Science", 3.8));
        students.add(new Student(nextId++, "Jane", "Smith", "jane.smith@university.edu", "Computer Science", 3.9));
        students.add(new Student(nextId++, "Michael", "Johnson", "michael.j@university.edu", "Business", 3.2));
        students.add(new Student(nextId++, "Emily", "Williams", "emily.w@university.edu", "Engineering", 3.6));
        students.add(new Student(nextId++, "David", "Brown", "david.b@university.edu", "Computer Science", 3.4));
    }

    // GET /api/students - Get all students (200 OK)
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET /api/students/{studentId} - Get student by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();

        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // GET /api/students/major/{major} - Get all students by major (using path variable)
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        List<Student> foundStudents = new ArrayList<>();

        for (Student student : students) {
            if (student.getMajor().equalsIgnoreCase(major)) {
                foundStudents.add(student);
            }
        }

        if (foundStudents.isEmpty()) {
            return new ResponseEntity<>(foundStudents, HttpStatus.NOT_FOUND); // 404 Not Found
        }

        return new ResponseEntity<>(foundStudents, HttpStatus.OK); // 200 OK
    }

    // GET /api/students/filter?gpa={minGpa} - Filter students with GPA >= minimum
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudentsByGpa(@RequestParam Double gpa) {
        List<Student> foundStudents = new ArrayList<>();

        for (Student student : students) {
            if (student.getGpa() >= gpa) {
                foundStudents.add(student);
            }
        }

        if (foundStudents.isEmpty()) {
            return new ResponseEntity<>(foundStudents, HttpStatus.NOT_FOUND); // 404 Not Found
        }

        return new ResponseEntity<>(foundStudents, HttpStatus.OK); // 200 OK
    }

    // POST /api/students - Register a new student (201 Created)
    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        student.setStudentId(nextId++); // Auto-generate student ID
        students.add(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED); // 201 Created
    }

    // PUT /api/students/{studentId} - Update student information
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updatedStudent) {
        Optional<Student> existingStudent = students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();

        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            
            // Update all fields (keeping the same ID)
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setMajor(updatedStudent.getMajor());
            student.setGpa(updatedStudent.getGpa());

            return new ResponseEntity<>(student, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }
    // DELETE /api/students/{studentId} - Delete a student
@DeleteMapping("/{studentId}")
public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
    boolean removed = students.removeIf(student -> student.getStudentId().equals(studentId));
    
    if (removed) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
    }
}
}