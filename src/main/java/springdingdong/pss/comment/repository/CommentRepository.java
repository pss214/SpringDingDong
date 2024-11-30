package springdingdong.pss.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdingdong.pss.comment.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
