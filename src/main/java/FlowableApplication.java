import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({
		"com.bob.projects.flowable.*",
})
@SpringBootApplication
public class FlowableApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowableApplication.class, args);
	}
}
