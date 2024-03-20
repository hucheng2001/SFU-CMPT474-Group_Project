package com.example.demo.security.service;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.security.config.securityConfig;
import com.example.demo.security.entity.dto.MyUser;
import com.example.demo.security.mapper.entity.Account;
import com.example.demo.security.utils.SnowFlake;



import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserManager implements UserDetailsService {

    static final String rolePrefix = securityConfig.rolePrefix;

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder encoder;

    @Value("${snowflake.user.workId}")
    private static long workId;

    @Value("${snowflake.datacenterId}")
    private static long datacenterId;

    private static SnowFlake usernameGenerator = new SnowFlake(workId,datacenterId);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Account account;
        try {
            account = accountService.findAccountByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return new MyUser(account);
    }

    public UserDetails loadUserByEmail(String email){
        Account account;
        try {
            account = accountService.findAccountByEmail(email);
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return new MyUser(account);
    }

    public String getNewUsername() {
        return Long.toString(usernameGenerator.nextId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void createUser(MyUser myUser){
        Account account = myUser.getAccount();
        accountService.addAccount(account);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(MyUser updateInfo) throws IllegalAccessException{ //查一遍后对比
        if(updateInfo == null) return;
        String username = updateInfo.getUsername();
        MyUser oldUser = (MyUser)loadUserByUsername(username);
        Account oldAccount = oldUser.getAccount(), 
                newAccount = updateInfo.getAccount();
        if(newAccount == null){//MyUser的newAccount不可能为null
            return;
        }
        newAccount.setPassword(oldAccount.getPassword()); //密码不能通过这种方法修改
        accountService.updateAccount(newAccount);
    }

    public void changePassword(String oldPassword, String newPassword)throws UserNotFoundException{
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            throw new IllegalArgumentException("旧密码或新密码不能为空");
        }
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if(currentAuth == null|| !currentAuth.isAuthenticated())
            throw new IllegalStateException("用户未登录，无法修改密码");
        Account account;
        try {
            account = accountService.findAccountByUsername(currentAuth.getName());
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new UserNotFoundException(e.getMessage());
        }
        if(encoder.matches(oldPassword, account.getPassword())){
            account.setPassword(encoder.encode(newPassword));
            accountService.updateAccount(account);
        }else throw new BadCredentialsException("旧密码验证失败");
    }
}
