package com.n2o.tombile.core.mail;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}