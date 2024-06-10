package com.n2o.tombile.core.user.service;

import com.n2o.tombile.core.user.dto.RQLogin;
import com.n2o.tombile.core.user.dto.RQRegister;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.model.UserData;
import com.n2o.tombile.core.user.model.VerificationStatus;
import com.n2o.tombile.core.user.repository.UserRepository;
import com.n2o.tombile.core.common.exception.DuplicateItemException;
import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String APPROVED_USER_NOT_FOUND = "couldn't find approved user";
    private static final String NON_VERIFIED_USER_NOT_FOUND = "couldn't find non verified user";
    private static final String USER_ALREADY_EXISTS_EMAIL = "a user with the given email already exists";
    private static final String USER_ALREADY_EXISTS_USERNAME = "a user with the given username already exists";
    private static final String USER_NOT_FOUND = "user not found";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(RQRegister request) {
        validateDuplicateUser(request.getUsername(), request.getEmail());

        return saveUser(request);
    }

    public User findUserForLogin(RQLogin request) {
        User user = getApprovedUserByUsername(request.getUsername());

        user.getUserData().setLastLoginDate(new Date());

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException(NON_VERIFIED_USER_NOT_FOUND));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    public User getNonVerifiedUserByEmail(String email) {
        return userRepository
                .findByEmailAndVerificationStatus(email, VerificationStatus.NOT_VERIFIED)
                .orElseThrow(() -> new ItemNotFoundException(NON_VERIFIED_USER_NOT_FOUND));
    }

    private User getApprovedUserByUsername(String username) {
        return userRepository
                .findByUsernameAndVerificationStatus(username, VerificationStatus.APPROVED)
                .orElseThrow(() -> new ItemNotFoundException(APPROVED_USER_NOT_FOUND));
    }

    private void validateDuplicateUser(String username, String email) {
        userRepository.findByUsernameOrEmail(username, email).ifPresent(user -> {
            if(user.getUsername().equals(username))
                throw new DuplicateItemException(USER_ALREADY_EXISTS_USERNAME);
            if(user.getUserData().getEmail().equals(email))
                throw new DuplicateItemException(USER_ALREADY_EXISTS_EMAIL);
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
