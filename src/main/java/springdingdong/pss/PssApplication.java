package springdingdong.pss;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springdingdong.pss.Account.domain.Account;
import springdingdong.pss.Account.repository.AccountRepository;

@SpringBootApplication
public class PssApplication {

    public static void main(String[] args) {
		SpringApplication.run(PssApplication.class, args);
	}
}
