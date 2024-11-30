package springdingdong.pss.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import springdingdong.pss.board.dto.request.BoardCreateRequestDTO;
import springdingdong.pss.board.dto.request.BoardUpdateReqestDTO;
import springdingdong.pss.board.service.BoardService;
import springdingdong.pss.common.dto.response.ResponseDTO;
import springdingdong.pss.common.exception.NotFoundException;

import java.net.URI;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createBoard(@RequestBody BoardCreateRequestDTO dto,
                                                   @AuthenticationPrincipal User user){
        return ResponseEntity.created(URI.create("/board/create")).body(boardService.createBoard(user, dto));
    }
    @GetMapping("/read")
    public ResponseEntity<ResponseDTO> readAllBoard(@RequestParam(value = "page",defaultValue = "1")int page){
        return ResponseEntity.ok().body(boardService.readBoardList(page));
    }
    @GetMapping("/detail")
    public ResponseEntity<ResponseDTO> readOneBoard(@RequestParam(value = "id")long id){
        return ResponseEntity.ok().body(boardService.readBoard(id));
    }
    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateBoard(@RequestBody BoardUpdateReqestDTO dto,
                                                   @AuthenticationPrincipal User user){
        return ResponseEntity.created(URI.create("/board/update")).body(boardService.editBoard(dto, user));
    }
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteBoard(@RequestParam(value = "id")long id,
                                                   @AuthenticationPrincipal User user){
        return ResponseEntity.created(URI.create("/board")).body(boardService.deleteBoard(id, user));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseDTO> errorHandler(NotFoundException e){
        log.debug("BoardController 에러 메세지 : {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
    }
}
