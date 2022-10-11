package sept.challenge2.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class accountApplication {

    public static void main(String[] args) {
        SpringApplication.run(accountApplication.class, args);
    }

}