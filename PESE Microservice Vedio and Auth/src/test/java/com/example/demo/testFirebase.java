package com.example.demo;

import java.net.InetAddress;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.demo.security.mapper.entity.Account;
import com.example.demo.security.service.AccountService;
import com.example.demo.security.service.MyUserManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testFirebase {
    
    @Autowired
    AccountService accountService;

    @Autowired
    MyUserManager userManager;

    @Test
    public void pingGoogle() throws Exception{
        String host = "www.google.com"; // 要检查的主机名

        InetAddress inetAddress = InetAddress.getByName(host);

        // 调用 isReachable 方法来检查主机的连通性，超时时间为 1000 毫秒
        boolean reachable = inetAddress.isReachable(1000);

        if (reachable) {
            System.out.println("Host " + host + " is reachable.");
        } else {
            System.out.println("Host " + host + " is not reachable.");
        }
    }

    @Test
    public void testAccountAdd()throws Exception{
        String username = userManager.getNewUsername();
        Account account = new Account().setUsername(username).setEmail("123456@test.com");
        accountService.addAccount(account);
        try {
            Account account2 = accountService.findAccountByUsername(username);
            System.out.println(account2.getEmail());
        } catch (Exception e) {
            throw e;
        }
    }
}
