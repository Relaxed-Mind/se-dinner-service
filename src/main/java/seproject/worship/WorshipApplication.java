package seproject.worship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import seproject.worship.speech.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileUploadProperties.class
})
public class WorshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorshipApplication.class, args);
	}

}
