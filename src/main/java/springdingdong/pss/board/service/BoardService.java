package springdingdong.pss.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import springdingdong.pss.account.domain.Account;
import springdingdong.pss.account.repository.AccountRepository;
import springdingdong.pss.board.domain.Board;
import springdingdong.pss.board.dto.request.BoardCreateRequestDTO;
import springdingdong.pss.board.dto.request.BoardUpdateReqestDTO;
import springdingdong.pss.board.repository.BoardRepository;
import springdingdong.pss.comment.repository.CommentRepository;
import springdingdong.pss.common.dto.response.ResponseDTO;
import springdingdong.pss.common.exception.NotFoundException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;

    public ResponseDTO createBoard(User user, BoardCreateRequestDTO dto){
        Account account = accountRepository.findByUsername(user.getUsername()).orElseThrow(
                ()-> new NotFoundException("회원 정보 없음"));

        boardRepository.save(Board.from(dto,account));
        return new ResponseDTO("저장되었습니다",null);
    }

    public ResponseDTO readBoardList(int page){
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        return new ResponseDTO(null, boardRepository.findAll(pageRequest));
    }

    public ResponseDTO readBoard(Long boardId) {
        Board board = suchBoard(boardId);
        return new ResponseDTO(null, board);
    }
    @Transactional
    public ResponseDTO editBoard(BoardUpdateReqestDTO dto, User user) {
        Board board = suchBoard(dto.boardId());
        if (Objects.equals(user.getUsername(), board.getUsername())){
            throw new NotFoundException("게시자와 이름이 다릅니다!");
        }
        board.update(dto);
        return new ResponseDTO("수정되었습니다",null);
    }

    public ResponseDTO deleteBoard(Long boardId, User user) {
        Board board = suchBoard(boardId);
        if (Objects.equals(user.getUsername(), board.getUsername())){
            throw new NotFoundException("게시자와 이름이 다릅니다!");
        }
        boardRepository.delete(board);
        return new ResponseDTO("삭제되었습니다",null);
    }

    private Board suchBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(()->new NotFoundException("게시물이 없습니다"));
    }
}
