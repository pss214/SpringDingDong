package springdingdong.pss.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import springdingdong.pss.account.dto.request.EditAccountRequestDTO;
import springdingdong.pss.account.dto.request.FindUsernameRequestDTO;
import springdingdong.pss.account.dto.request.JoinReqestDTO;
import springdingdong.pss.account.dto.request.LoginRequestDTO;
import springdingdong.pss.account.service.AccountService;
import springdingdong.pss.common.dto.response.ResponseDTO;
import springdingdong.pss.common.exception.NotFoundException;

import java.net.URI;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    final private AccountService accountService;
    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> join(@RequestBody JoinReqestDTO dto){
        return ResponseEntity.created(URI.create("/account/join")).body(accountService.joinAccount(dto));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto){
        return ResponseEntity.ok().body(accountService.loginAccount(dto));
    }
    @GetMapping("/mypage")
    public ResponseEntity<ResponseDTO> findUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(accountService.mypageAccount(user));
    }
//    @GetMapping("/find")
//    public ResponseEntity<ResponseDTO> findUsername(@RequestParam(value = "username") String username){
//        return ResponseEntity.ok().body(accountService.findUsername(new FindUsernameRequestDTO(username)));
//    }
    @PostMapping("/edit")
    public ResponseEntity<ResponseDTO> editUser(@AuthenticationPrincipal User user, @RequestBody EditAccountRequestDTO dto){
        return ResponseEntity.created(URI.create("/account/edit")).body(accountService.editAccount(user, dto));
    }
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteUser(@AuthenticationPrincipal User user){
        return ResponseEntity.created(URI.create("/account")).body(accountService.deleteAccount(user));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseDTO> errorHandler(NotFoundException e){
        log.debug("AccountController 에러 메세지 : {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
    }
}
