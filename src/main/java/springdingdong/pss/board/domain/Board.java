package springdingdong.pss.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import springdingdong.pss.account.domain.Account;
import springdingdong.pss.board.dto.request.BoardCreateRequestDTO;
import springdingdong.pss.board.dto.request.BoardUpdateReqestDTO;
import springdingdong.pss.comment.domain.Comment;
import springdingdong.pss.common.domain.BaseTime;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subject;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> comments;
    private int readCut;

    public static Board from(BoardCreateRequestDTO dto, Account account){
        return Board.builder()
                .subject(dto.subject())
                .content(dto.content())
                .account(account)
                .build();
    }

    public void update(BoardUpdateReqestDTO dto){
        this.subject = dto.subject();
        this.content = dto.content();
    }

    public String getUsername(){
        return account.getUsername();
    }


}
