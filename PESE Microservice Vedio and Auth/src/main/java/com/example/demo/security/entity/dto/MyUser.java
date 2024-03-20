package com.example.demo.security.entity.dto;

import java.util.HashSet;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.security.config.securityConfig;
import com.example.demo.security.mapper.entity.Account;

import lombok.Data;
import java.util.Collection;


@Data
public class MyUser implements UserDetails, CredentialsContainer{

    static final String rolePrefix = securityConfig.rolePrefix;

    private Account account;
    private Collection<GrantedAuthority> authorities;

    public MyUser(Account account){
        this.account = account;
        this.authorities = loadAuthorities(account);
    }

    private Collection<GrantedAuthority> loadAuthorities(Account account){//加一个role prefix后返回
        Collection<GrantedAuthority> authorities = new HashSet<>();
        for(String role:account.getRoles()){
            authorities.add(new SimpleGrantedAuthority(rolePrefix+role));
        }
        for(String permission:account.getPermissions()){
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    public MyUser addAuthority(String... authorities){
        if (this.authorities == null){
            this.authorities = new HashSet<GrantedAuthority>();
        }
        for(String authority:authorities){
            this.authorities.add(new SimpleGrantedAuthority(authority));
            this.account.getPermissions().add(authority);
        }
        return this;
    }

    public MyUser addRole(String... roles){
        if (this.authorities == null){
            this.authorities = new HashSet<GrantedAuthority>();
        }
        for(String role:roles){
            if(role.startsWith(rolePrefix))
                throw new IllegalArgumentException("角色名不能以 "+rolePrefix+" 前缀开头, 系统会自动添加");
            this.authorities.add(new SimpleGrantedAuthority(rolePrefix+role));
            this.account.getRoles().add(role);
        }
        return this;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities; 
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.isEnabled();
    }

    @Override
    public void eraseCredentials() { //清除密码，防止二次利用
        account.setPassword(null);
    }
    
}
