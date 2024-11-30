package springdingdong.pss.account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springdingdong.pss.account.domain.Account;
import springdingdong.pss.account.dto.request.*;
import springdingdong.pss.account.dto.response.LoginResponseDTO;
import springdingdong.pss.account.dto.response.MyPageResponseDTO;
import springdingdong.pss.account.repository.AccountRepository;
import springdingdong.pss.common.dto.response.ResponseDTO;
import springdingdong.pss.common.exception.NotFoundException;
import springdingdong.pss.common.service.TokenProvider;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public ResponseDTO joinAccount(JoinReqestDTO dto){
        if (checkDuplicateNickname(dto.username())){
            throw new RuntimeException("아이디가 중복입니다!");
        }
        String password = passwordEncoder.encode(dto.password());
        accountRepository.save(Account.from(dto, password));
        return new ResponseDTO("저장되었습니다",null);
    }
    public ResponseDTO loginAccount(LoginRequestDTO dto){
        Account account = findByAccount(dto.username());
        if (passwordEncoder.matches(dto.password(),account.getPassword())){
            String token = tokenProvider.createAccessToken(String.format("%s:%s", account.getUsername(), "ROLE_USER"));
            return new ResponseDTO("로그인 성공",new LoginResponseDTO(account.getUsername(), token));
        }else {
            throw new NotFoundException("회원정보 없음");
        }
    }
    public ResponseDTO mypageAccount(User dto){
        Account account = findByAccount(dto.getUsername());
        return new ResponseDTO(null, new MyPageResponseDTO(account));
    }
    @Transactional
    public ResponseDTO editAccount(User user, EditAccountRequestDTO dto){
        Account account = findByAccount(user.getUsername());
        account.edit(dto.username(), passwordEncoder.encode(dto.password()), dto.name(), dto.phone());
        return new ResponseDTO("저장되었습니다",null);
    }
    public ResponseDTO deleteAccount(User user){
        accountRepository.delete(findByAccount(user.getUsername()));

        return new ResponseDTO("삭제되었습니다",null);
    }
    public boolean checkDuplicateNickname(String username){
        return accountRepository.existsByUsername(username);
    }
    private Account findByAccount(String username){
        return accountRepository.findByUsername(username).orElseThrow(()->new NotFoundException("회원정보 없음"));
    }
}
