package springdingdong.pss.account.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import springdingdong.pss.account.dto.request.JoinReqestDTO;
import springdingdong.pss.board.domain.Board;
import springdingdong.pss.comment.domain.Comment;
import springdingdong.pss.common.domain.BaseTime;

import java.net.PasswordAuthentication;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String username;
    private String password;
    private String name;
    private String phone;

    @OneToMany(mappedBy = "account",orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "account",orphanRemoval = true)
    private List<Board> boards;
    public static Account from(JoinReqestDTO dto, String password){
        return Account.builder()
                .username(dto.username())
                .password(password)
                .name(dto.name())
                .phone(dto.phone())
                .build();
    }
    public void edit(String username, String password, String name, String phone){
        this.password=password;
        this.name=name;
        this.phone=phone;
    }
}
