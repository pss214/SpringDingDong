package springdingdong.pss.common.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String mainPage(){
        return "index";
    }
    @GetMapping("/list")
    public String boardListPage(){
        return "boardlist";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }
    @GetMapping("/mypage")
    public String myPage(){
        return "mypage";
    }
    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }
}
