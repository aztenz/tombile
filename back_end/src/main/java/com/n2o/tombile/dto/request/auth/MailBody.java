package com.n2o.tombile.dto.request.auth;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}