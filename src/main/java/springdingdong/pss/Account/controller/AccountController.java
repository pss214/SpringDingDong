package springdingdong.pss.Account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springdingdong.pss.Account.dto.request.EditAccountRequestDTO;
import springdingdong.pss.Account.dto.request.FindUsernameRequestDTO;
import springdingdong.pss.Account.dto.request.JoinReqestDTO;
import springdingdong.pss.Account.dto.request.LoginRequestDTO;
import springdingdong.pss.Account.service.AccountService;
import springdingdong.pss.common.dto.response.ResponseDTO;

import java.net.URI;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    final private AccountService accountService;
    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> join(@RequestBody JoinReqestDTO dto){
        if (accountService.checkDuplicateNickname(dto.username())){
            return ResponseEntity.badRequest().body(new ResponseDTO("아이디가 중복입니다",null));
        }
        return ResponseEntity.created(URI.create("/account/join")).body(accountService.joinAccount(dto));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto){
        try {
            return ResponseEntity.ok().body(accountService.loginAccount(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e);
        }

    }
    @PostMapping("/find")
    public ResponseEntity<ResponseDTO> findUser(@RequestBody FindUsernameRequestDTO dto){
        return ResponseEntity.ok().body(accountService.findUsername(dto));
    }
//    @GetMapping("/find")
//    public ResponseEntity<ResponseDTO> findUsername(@RequestParam(value = "username") String username){
//        return ResponseEntity.ok().body(accountService.findUsername(new FindUsernameRequestDTO(username)));
//    }
    @PostMapping("/edit")
    public ResponseEntity<ResponseDTO> editUser(@RequestBody EditAccountRequestDTO dto){
        return ResponseEntity.created(URI.create("/account/edit")).body(accountService.editAccount(dto));
    }
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteUser(@RequestParam(value = "username")String username){
        return ResponseEntity.created(URI.create("/account")).body(accountService.deleteAccount(username));
    }
}
