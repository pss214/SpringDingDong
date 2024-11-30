package springdingdong.pss.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdingdong.pss.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllBySubjectContains(String subject, PageRequest pageRequest);
}
