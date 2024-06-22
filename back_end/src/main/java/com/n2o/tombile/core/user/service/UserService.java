package com.n2o.tombile.core.user.service;

import com.n2o.tombile.address.service.AddressService;
import com.n2o.tombile.core.common.exception.DuplicateItemException;
import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.exception.PasswordNotMatchException;
import com.n2o.tombile.auth.auth.service.LogoutService;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.core.user.dto.RQCharge;
import com.n2o.tombile.core.user.dto.RQLogin;
import com.n2o.tombile.core.user.dto.RQPassword;
import com.n2o.tombile.core.user.dto.RQProfile;
import com.n2o.tombile.core.user.dto.RQRegister;
import com.n2o.tombile.core.user.dto.RSPUserProfile;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.model.UserData;
import com.n2o.tombile.core.user.model.VerificationStatus;
import com.n2o.tombile.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.n2o.tombile.core.common.util.Constants.CHARGED_SUCCESSFULLY;
import static com.n2o.tombile.core.common.util.Constants.ERROR_APPROVED_USER_NOT_FOUND;
import static com.n2o.tombile.core.common.util.Constants.ERROR_NON_VERIFIED_USER_NOT_FOUND;
import static com.n2o.tombile.core.common.util.Constants.ERROR_PASSWORD_NOT_MATCH;
import static com.n2o.tombile.core.common.util.Constants.ERROR_USER_EXISTS_EMAIL;
import static com.n2o.tombile.core.common.util.Constants.ERROR_USER_EXISTS_USERNAME;
import static com.n2o.tombile.core.common.util.Constants.ERROR_USER_NOT_FOUND;
import static com.n2o.tombile.core.common.util.Constants.PASSWORD_RESET_SUCCESS;
import static com.n2o.tombile.core.common.util.Constants.PROFILE_CHANGED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;
    private final LogoutService logoutService;

    public RSPUserProfile getUserProfile() {
        User user = Util.getCurrentUser();
        RSPUserProfile userProfile = Util.cloneObject(user.getUserData(), RSPUserProfile.class);
        userProfile.setUsername(user.getUsername());
        userProfile.setAddresses(addressService.getAllAddresses());
        return userProfile;
    }

    public String modifyProfile(RQProfile request) {
        User user = Util.getCurrentUser();
        Util.copyProperties(request, user.getUserData());
        if(request.getUsername() != null) {
            userRepository
                    .findByUsername(request.getUsername())
                    .ifPresent(u -> {throw new DuplicateItemException(ERROR_USER_EXISTS_USERNAME);});
            user.setUsername(request.getUsername());
            logoutService.logout();
        }
        userRepository.save(user);
        return PROFILE_CHANGED_SUCCESSFULLY;
    }

    public String changePassword(RQPassword request) {
        User user = Util.getCurrentUser();
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new PasswordNotMatchException(ERROR_PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return PASSWORD_RESET_SUCCESS;
    }

    public String chargeAccount(RQCharge request) {
        User user = Util.getCurrentUser();
        user.getUserData().setWalletBalance(user.getUserData().getWalletBalance() + request.getCharge());
        userRepository.save(user);
        return CHARGED_SUCCESSFULLY;
    }

    public void saveChanges(User user) {
        userRepository.save(user);
    }

    public User createUser(RQRegister request) {
        validateDuplicateUser(request.getUsername(), request.getEmail());

        return saveUser(request);
    }

    public User findUserForLogin(RQLogin request) {
        User user = getApprovedUserByUsername(request.getUsername());

        user.getUserData().setLastLoginDate(Instant.now());

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException(ERROR_USER_NOT_FOUND));
    }

    public User getNonVerifiedUserByEmail(String email) {
        return userRepository
                .findByEmailAndVerificationStatus(email, VerificationStatus.NOT_VERIFIED)
                .orElseThrow(() -> new ItemNotFoundException(ERROR_NON_VERIFIED_USER_NOT_FOUND));
    }

    private User getApprovedUserByUsername(String username) {
        return userRepository
                .findByUsernameAndVerificationStatus(username, VerificationStatus.APPROVED)
                .orElseThrow(() -> new ItemNotFoundException(ERROR_APPROVED_USER_NOT_FOUND));
    }

    private void validateDuplicateUser(String username, String email) {
        userRepository.findByUsernameOrEmail(username, email).ifPresent(user -> {
            if(user.getUsername().equals(username))
                throw new DuplicateItemException(ERROR_USER_EXISTS_USERNAME);
            if(user.getUserData().getEmail().equals(email))
                throw new DuplicateItemException(ERROR_USER_EXISTS_EMAIL);
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
        UserData userData = Util.cloneObject(request, UserData.class);
        userData.setLastLoginDate(Instant.now());
        userData.setRegistrationDate(Instant.now());
        userData.setVerificationStatus(VerificationStatus.NOT_VERIFIED);
        return userData;
    }
}
