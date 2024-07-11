package comflower.sagongsa;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SagongsaApplication {

//	@PostConstruct
//	public void init() {
//		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
//		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
//	}

	public static void main(String[] args) {
		SpringApplication.run(SagongsaApplication.class, args);
	}
}