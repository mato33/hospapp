package site.mato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HospappApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospappApplication.class, args);
	}

}
