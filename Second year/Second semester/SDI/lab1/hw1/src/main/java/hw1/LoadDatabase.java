package hw1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CarRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Car("Ferrari", "benzine", "manual", 3500, 320)));
            log.info("Preloading " + repository.save(new Car("Lamborghini", "benzine", "automatic", 5600, 650)));
        };
    }
}