package springdingdong.pss.account.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import springdingdong.pss.account.dto.request.JoinReqestDTO;
import springdingdong.pss.common.domain.BaseTime;

import java.net.PasswordAuthentication;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public static Account from(JoinReqestDTO dto, String password){
        return Account.builder()
                .username(dto.username())
                .password(password)
                .name(dto.name())
                .phone(dto.phone())
                .build();
    }
    @Builder
    private Account(String username, String password, String name, String phone){
        this.username=username;
        this.password=password;
        this.name=name;
        this.phone=phone;
    }
    public void edit(String username, String password, String name, String phone){
        this.password=password;
        this.name=name;
        this.phone=phone;
    }
}
