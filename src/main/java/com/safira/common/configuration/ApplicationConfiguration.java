package com.safira.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by francisco on 25/03/15.
 */
@Configuration
@PropertySource({"classpath:/properties/configuration.properties"})
public class ApplicationConfiguration {
    private final static String HOSTNAME_PROPERTY = "hostNameUrl";

    private final static String SECURITY_AUTHORIZATION_REQUIRE_SIGNED_REQUESTS = "security.authorization.requireSignedRequests";
    private final static String SESSION_EXPIRATION_TIME = "session.token.life.span.in.hours";
    private final static String SESSION_DATE_OFFSET_IN_MINUTES = "session.date.offset.inMinutes";
    private final static String EMAIL_REGISTRATION_EXPIRATION_TIME = "email.registration.token.life.span.in.hours";
    private final static String EMAIL_VERIFICATION_EXPIRATION_TIME = "email.verification.token.life.span.in.hours";
    private final static String LOST_PASSWORD_EXPIRATION_TIME = "lost.password.token.life.span.in.hours";
    private final static String EMAIL_SERVICES_FROM_ADDRESS = "email.services.from";
    private final static String EMAIL_SERVICES_REPLYTO_ADDRESS = "email.services.reply";
    private final static String EMAIL_SERVICES_VERIFICATION_EMAIL_SUBJECT_TEXT = "email.services.emailVerificationSubjectText";
    private final static String EMAIL_SERVICES_REGISTRATION_EMAIL_SUBJECT_TEXT = "email.services.emailRegistrationSubjectText";
    private final static String EMAIL_SERVICES_LOST_PASSWORD_SUBJECT_TEXT = "email.services.lostPasswordSubjectText";


    @Autowired
    protected Environment environment;

    public String getHostNameUrl() {
        return environment.getProperty(HOSTNAME_PROPERTY);
    }

    public String getFacebookClientId() {
        return environment.getProperty("facebook.clientId");
    }

    public String getFacebookClientSecret() {
        return environment.getProperty("facebook.clientSecret");
    }

    public int getSessionExpirationTime() {
        return Integer.parseInt(environment.getProperty(SESSION_EXPIRATION_TIME));
    }

    public int getSessionDateOffsetInMinutes() {
        return Integer.parseInt(environment.getProperty(SESSION_DATE_OFFSET_IN_MINUTES));
    }

    public int getEmailRegistrationExpirationTime() {
        return Integer.parseInt(environment.getProperty(EMAIL_REGISTRATION_EXPIRATION_TIME));
    }

    public int getEmailVerificationExpirationTime() {
        return Integer.parseInt(environment.getProperty(EMAIL_VERIFICATION_EXPIRATION_TIME));
    }

    public int getLostPasswordExpirationTime() {
        return Integer.parseInt(environment.getProperty(LOST_PASSWORD_EXPIRATION_TIME));
    }

    public String getEmailVerificationSubjectText() {
        return environment.getProperty(EMAIL_SERVICES_VERIFICATION_EMAIL_SUBJECT_TEXT);
    }

    public String getEmailRegistrationSubjectText() {
        return environment.getProperty(EMAIL_SERVICES_REGISTRATION_EMAIL_SUBJECT_TEXT);
    }

    public String getLostPasswordSubjectText() {
        return environment.getProperty(EMAIL_SERVICES_LOST_PASSWORD_SUBJECT_TEXT);
    }

    public String getEmailFromAddress() {
        return environment.getProperty(EMAIL_SERVICES_FROM_ADDRESS);
    }

    public String getEmailReplyToAddress() {
        return environment.getProperty(EMAIL_SERVICES_REPLYTO_ADDRESS);
    }

    public Boolean requireSignedRequests() {
        return environment.getProperty(SECURITY_AUTHORIZATION_REQUIRE_SIGNED_REQUESTS).equalsIgnoreCase("true");
    }
}
