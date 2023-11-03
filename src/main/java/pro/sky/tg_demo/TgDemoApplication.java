package pro.sky.tg_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TgDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgDemoApplication.class, args);
	}

}
