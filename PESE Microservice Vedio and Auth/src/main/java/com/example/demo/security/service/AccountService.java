package com.example.demo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.QueryException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.security.mapper.entity.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService {
    @Autowired
    Firestore db;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void addAccount(Account account) {
        DocumentReference docRef = db.collection("users").document(account.getUsername());
        ApiFuture<WriteResult> result = docRef.set(account);
        Timestamp timestamp;
        try {
            timestamp = result.get().getUpdateTime();
        } catch (Exception e) {
            log.error("failed to add account<{}>", account.getUsername());
            return;
        }
        log.info("successfully add account<{}> at ", account.getUsername(), timestamp);
    }

    public void updateAccount(Account account) {
        db.collection("users").document(account.getUsername()).set(account);
    }

    public Account findAccountByEmail(String email) throws UserNotFoundException, QueryException {
        CollectionReference collectionReference = db.collection("users");
        Query query = collectionReference.whereEqualTo("email", email).limit(1);
        QuerySnapshot querySnapshot;
        try {
            querySnapshot = query.get().get();
        } catch (Exception e) {
            throw new QueryException("query failed while finding account by email " + email);
        }
        if (querySnapshot.isEmpty()) {
            throw new UserNotFoundException("email<" + email + "> doesn't exist");
        }
        QueryDocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
        return documentSnapshot.toObject(Account.class);
    }

    public Account findAccountByUsername(String username) throws UserNotFoundException, QueryException {
        DocumentReference docRef = db.collection("users").document(username);
        DocumentSnapshot document;
        try {
            document = docRef.get().get();
        } catch (Exception e) {
            throw new QueryException("query failed while finding account by username " + username+", msg:"+e.getMessage());
        }
        // 获取文档
        if (!document.exists()) {
            throw new UserNotFoundException("no such user with username:" + username);
        }
        return document.toObject(Account.class);
    }

    public boolean existEmail(String email) {
        try {
            findAccountByEmail(email);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
