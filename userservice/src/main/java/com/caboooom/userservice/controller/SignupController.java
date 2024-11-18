package com.caboooom.userservice.controller;

import com.caboooom.userservice.service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.caboooom.userservice.exception.UserAlreadyExistsException;

@Slf4j
@Controller
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    /**
     * 알번 회원가입 요청을 처리합니다.
     */
    @PostMapping("/signup")
    public RedirectView signup(@RequestParam String username, @RequestParam String password,
                               @RequestParam String name, @RequestParam String email) {
        log.debug("signup request : username={}, password={}", username, password);
        if (signupService.findUserByUserId(username) != null) {
            throw new UserAlreadyExistsException();
        }
        signupService.signUp(username, name, email, password);
        return new RedirectView("http://localhost:8080/login?state=success");

    }

}
