package com.example.demo.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.entity.dto.MyUser;
import com.example.demo.security.mapper.entity.Account;
import com.example.demo.security.service.AccountService;
import com.example.demo.security.service.JwtService;
import com.example.demo.security.service.MailService;
import com.example.demo.security.service.MyUserManager;
import com.example.demo.utils.RestBean;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/user")
@Tag(name = "用户基础接口", description = "注册/jwt续签/注销")
public class UserBaseController {

    @Autowired
    MyUserManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    MailService mailService;

    @Autowired
    AccountService accountService;

    @PostMapping("/regist/simple")
    public RestBean<String> regist(@RequestParam("password") String password){
        String username = manager.getNewUsername();
        Account account = new Account()
                                .setUsername(username)
                                .setPassword(encoder.encode(password))
                                .setEnabled(true);
        MyUser user = new MyUser(account).addRole("USER");
        manager.createUser(user);
        return RestBean.success("你的用户id为"+username);
    }
}
