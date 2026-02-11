package question2_student_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Question2StudentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Question2StudentApiApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
		return "Welcome to the Student API! Go to <a href='/api/students'>/api/students</a> to see the data.";
	}

}
