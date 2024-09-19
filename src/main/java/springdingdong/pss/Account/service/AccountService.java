package springdingdong.pss.Account.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springdingdong.pss.Account.domain.Account;
import springdingdong.pss.Account.dto.request.EditAccountRequestDTO;
import springdingdong.pss.Account.dto.request.FindUsernameRequestDTO;
import springdingdong.pss.Account.dto.request.JoinReqestDTO;
import springdingdong.pss.Account.dto.request.LoginRequestDTO;
import springdingdong.pss.Account.dto.response.FindUsernameResponseDTO;
import springdingdong.pss.Account.repository.AccountRepository;
import springdingdong.pss.common.dto.response.ResponseDTO;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    final private AccountRepository accountRepository;

    public ResponseDTO joinAccount(JoinReqestDTO dto){
        accountRepository.save(Account.from(dto));
        return new ResponseDTO("저장되었습니다",null);
    }
    public ResponseDTO loginAccount(LoginRequestDTO dto){
        Account account = findByAccount(dto.username());
        if (Objects.equals(account.getPassword(), dto.password())){
            return new ResponseDTO("로그인 성공",new LoginRequestDTO(account.getUsername(),null));
        }else {
            throw new RuntimeException("로그인 실패");
        }
    }
    public ResponseDTO findUsername(FindUsernameRequestDTO dto){
        Account account = findByAccount(dto.username());
        return new ResponseDTO(null, new FindUsernameResponseDTO(account));
    }
    @Transactional
    public ResponseDTO editAccount(EditAccountRequestDTO dto){
        Account account = findByAccount(dto.username());
        account.edit(dto.username(), dto.password(), dto.name(), dto.phone());
        return new ResponseDTO("저장되었습니다",null);
    }
    public ResponseDTO deleteAccount(String username){
        accountRepository.delete(findByAccount(username));
        return new ResponseDTO("삭제되었습니다",null);
    }
    public boolean checkDuplicateNickname(String username){
        return accountRepository.existsByUsername(username);
    }
    private Account findByAccount(String username){
        return accountRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("회원 정보 없음"));
    }

}
