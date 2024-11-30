package springdingdong.pss.comment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import springdingdong.pss.account.domain.Account;
import springdingdong.pss.board.domain.Board;
import springdingdong.pss.common.domain.BaseTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String body;
    @ColumnDefault("FALSE")
    private Boolean isDelete;
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public void update(String newBody){
        this.body=newBody;
    }
    public void updateBoard(Board board){
        this.board=board;
    }
    public void updateAccount(Account account){
        this.account=account;
    }
}
