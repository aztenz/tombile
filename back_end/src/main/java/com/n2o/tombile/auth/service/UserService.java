package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.dto.RQLogin;
import com.n2o.tombile.auth.dto.RQRegister;
import com.n2o.tombile.auth.model.entity.User;
import com.n2o.tombile.auth.model.entity.UserData;
import com.n2o.tombile.auth.model.enums.VerificationStatus;
import com.n2o.tombile.auth.repository.UserRepository;
import com.n2o.tombile.exception.DuplicateItemException;
import com.n2o.tombile.exception.ItemNotFoundException;
import com.n2o.tombile.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String APPROVED_USER_NOT_FOUND = "couldn't find approved user: ";
    private static final String NON_VERIFIED_USER_NOT_FOUND = "couldn't find non verified user: ";
    private static final String USERNAME_ALREADY_EXISTS = "username already exists: ";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(RQRegister request) {
        validateDuplicateUser(request.getUsername());

        return saveUser(request);
    }

    public User findUserForLogin(RQLogin request) {
        User user = getApprovedUserByUsername(request.getUsername());

        user.getUserData().setLastLoginDate(new Date());

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException(NON_VERIFIED_USER_NOT_FOUND + email));
    }

    public User getNonVerifiedUserByEmail(String email) {
        return userRepository
                .findByEmailAndVerificationStatus(email, VerificationStatus.NOT_VERIFIED)
                .orElseThrow(() -> new ItemNotFoundException(NON_VERIFIED_USER_NOT_FOUND + email));
    }

    private User getApprovedUserByUsername(String username) {
        return userRepository
                .findByUsernameAndVerificationStatus(username, VerificationStatus.APPROVED)
                .orElseThrow(() -> new ItemNotFoundException(APPROVED_USER_NOT_FOUND + username));
    }

    private void validateDuplicateUser(String username) {
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new DuplicateItemException(USERNAME_ALREADY_EXISTS + username);
        });
    }

    private User saveUser(RQRegister request) {
        User user = createUserObject(request);
        UserData userData = createUserDataObject(request);

        user.setUserData(userData);
        userData.setUser(user);

        return userRepository.save(user);
    }

    private User createUserObject(RQRegister request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    private UserData createUserDataObject(RQRegister request) {
        Date date = new Date();
        UserData userData = Util.cloneObject(request, UserData.class);
        userData.setLastLoginDate(date);
        userData.setRegistrationDate(date);
        userData.setVerificationStatus(VerificationStatus.NOT_VERIFIED);
        return userData;
    }
}
