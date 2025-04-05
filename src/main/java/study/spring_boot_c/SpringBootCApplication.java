package study.spring_boot_c;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootCApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCApplication.class, args);
	}

}
