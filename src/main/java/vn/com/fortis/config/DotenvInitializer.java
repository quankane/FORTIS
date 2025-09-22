package vn.com.fortis.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // Load .env
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()  // không lỗi nếu không có file .env
                .load();

        // Đưa tất cả biến từ .env vào system properties
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}
