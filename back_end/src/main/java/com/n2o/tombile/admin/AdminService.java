package com.n2o.tombile.admin;

import com.n2o.tombile.admin.dto.RSPRequestDetails;
import com.n2o.tombile.admin.dto.RSPRequestListItem;
import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.core.mail.EmailService;
import com.n2o.tombile.core.mail.MailBody;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.model.VerificationStatus;
import com.n2o.tombile.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.ACCOUNT_APPROVED_BODY;
import static com.n2o.tombile.core.common.util.Constants.ACCOUNT_APPROVED_SUBJECT;
import static com.n2o.tombile.core.common.util.Constants.ACCOUNT_REJECTED_BODY;
import static com.n2o.tombile.core.common.util.Constants.ACCOUNT_REJECTED_SUBJECT;
import static com.n2o.tombile.core.common.util.Constants.ERROR_USER_NOT_FOUND;
import static com.n2o.tombile.core.common.util.Constants.USER_APPROVED;
import static com.n2o.tombile.core.common.util.Constants.USER_REJECTED;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<RSPRequestListItem> getCreateAccountRequests() {
        List<User> requests = userRepository.findAllByVerificationStatus(VerificationStatus.VERIFIED);
        List<RSPRequestListItem> responses = new ArrayList<>();
        requests.forEach(user -> responses.add(prepareRequestListItem(user)));
        return responses;
    }

    public RSPRequestDetails getCreateAccountRequestById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(ERROR_USER_NOT_FOUND));
        return prepareRequestDetails(user);
    }

    public String approveAccount(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(ERROR_USER_NOT_FOUND));

        user.getUserData().setVerificationStatus(VerificationStatus.APPROVED);

        MailBody mailBody = MailBody.builder()
                .to(user.getUserData().getEmail())
                .subject(ACCOUNT_APPROVED_SUBJECT)
                .text(prepareEmailBody(ACCOUNT_APPROVED_BODY, user.getUserData().getFirstName())).build();

        emailService.sendSimpleMessage(mailBody);

        userRepository.save(user);

        return USER_APPROVED;
    }

    public String rejectAccount(int id) {
        userRepository.findById(id).ifPresent(user -> {

            MailBody mailBody = MailBody.builder()
                    .to(user.getUserData().getEmail())
                    .subject(ACCOUNT_REJECTED_SUBJECT)
                    .text(prepareEmailBody(ACCOUNT_REJECTED_BODY, user.getUserData().getFirstName())).build();

            emailService.sendSimpleMessage(mailBody);

            userRepository.delete(user);

        });

        return USER_REJECTED;
    }

    private String prepareEmailBody(String message, String firstName) {
        return MessageFormat.format(message, firstName);
    }

    private RSPRequestListItem prepareRequestListItem(User user) {
        return Util.cloneObject(user.getUserData(), RSPRequestListItem.class);
    }

    private RSPRequestDetails prepareRequestDetails(User user) {
        RSPRequestDetails requestDetails = Util.cloneObject(user.getUserData(), RSPRequestDetails.class);
        requestDetails.setUsername(user.getUsername());
        return requestDetails;
    }
}
