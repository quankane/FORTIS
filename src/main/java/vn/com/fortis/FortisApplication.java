package vn.com.fortis;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.com.fortis.config.properties.AdminInfoProperties;
import vn.com.fortis.domain.entity.user.Role;
import vn.com.fortis.domain.entity.user.User;
import vn.com.fortis.repository.UserRepository;

@Log4j2
@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = "com.example.haus")
@EnableConfigurationProperties({AdminInfoProperties.class})
public class FortisApplication {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		Environment env = SpringApplication.run(FortisApplication.class, args).getEnvironment();
		String appName = env.getProperty("spring.application.name");
		if (appName != null) {
			appName = appName.toUpperCase();
		}
		String port = env.getProperty("server.port");
		log.info("-------------------------START {} Application------------------------------", appName);
		log.info("   Application         : {}", appName);
		log.info("   Url swagger-ui      : http://localhost:{}/swagger-ui.html", port);
		log.info("-------------------------START SUCCESS {} Application------------------------------", appName);
	}

	@Bean
	CommandLineRunner init(AdminInfoProperties adminInfo) {
		return args -> {
			if(userRepository.count() == 0) {
				User admin = User.builder()
						.username(adminInfo.getUsername())
						.password(passwordEncoder.encode(adminInfo.getPassword()))
						.firstName(adminInfo.getFirstName())
						.lastName(adminInfo.getLastName())
						.email(adminInfo.getEmail())
						.role(Role.ADMIN)
						.build();
				userRepository.save(admin);
				log.info("admin created successful with name: {}", admin.getUsername());
			}
		};
	}

}