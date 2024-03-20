package com.example.demo.security.mapper.entity;

import java.util.ArrayList;

import com.google.cloud.firestore.annotation.DocumentId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Account{
    @DocumentId
    String username;
    
    String nickname;
    String email;
    String phone;
    String password;
    boolean enabled;

    ArrayList<String> roles = new ArrayList<>();
    ArrayList<String> permissions = new ArrayList<>();
}
