package hw1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // meta-annotation that pulls in component scanning, autoconfiguration, and property support
public class Hw1Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw1Application.class, args);
    }

}
