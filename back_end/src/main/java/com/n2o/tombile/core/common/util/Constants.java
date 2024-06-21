package com.n2o.tombile.core.common.util;

public abstract class Constants {

    // Length and Range Constants
    public static final int POSITIVE_NUM_MIN = 0;
    public static final int POSITIVE_NON_ZERO_NUM_MIN = 1;
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int RATING_MIN_VALUE = 1;
    public static final int RATING_MAX_VALUE = 5;
    public static final int CAR_YEAR_MIN = 1950;
    public static final int CAR_YEAR_MAX = 2024;
    public static final int OTP_MIN_VALUE = 100000;
    public static final int OTP_MAX_VALUE = 999999;
    public static final int OTP_EXPIRATION_MILLISECONDS = 600000;
    public static final int JWT_SECRET_KEY_BYTE_SIZE = 32;
    public static final int JWT_SECRET_KEY_SIZE = 64;
    public static final int JWT_TOKEN_EXPIRATION_MILLISECONDS = 86400000;

    // Regex Patterns
    public static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    // Generic Messages
    public static final String ERROR_HANDLING_REQUEST = "error handling request";
    public static final String ERROR_CREATE_INSTANCE = "unable to create an instance of ";

    // User-related Error Messages
    public static final String ERROR_BAD_CREDENTIALS = "username and password didn't match";
    public static final String ERROR_USERNAME_REQUIRED = "username can't be empty";
    public static final String ERROR_PASSWORD_REQUIRED = "password can't be empty";
    public static final String ERROR_PASSWORD_TOO_SHORT = "password cannot be less than " + PASSWORD_MIN_LENGTH + " characters";
    public static final String ERROR_FIRST_NAME_REQUIRED = "first name can't be empty";
    public static final String ERROR_LAST_NAME_REQUIRED = "last name can't be empty";
    public static final String ERROR_EMAIL_REQUIRED = "email can't be empty";
    public static final String ERROR_ROLE_REQUIRED = "role can't be empty";
    public static final String ERROR_EMAIL_INVALID = "this is not a valid email";
    public static final String ERROR_USER_EXISTS_EMAIL = "a user with the given email already exists";
    public static final String ERROR_USER_EXISTS_USERNAME = "a user with the given username already exists";
    public static final String ERROR_USER_NOT_FOUND = "user not found";
    public static final String ERROR_APPROVED_USER_NOT_FOUND = "couldn't find approved user";
    public static final String ERROR_NON_VERIFIED_USER_NOT_FOUND = "couldn't find non verified user";

    // Address-related Error Messages
    public static final String ERROR_STREET_REQUIRED = "street can't be empty";
    public static final String ERROR_CITY_REQUIRED = "city can't be empty";
    public static final String ERROR_ZIP_CODE_REQUIRED = "zip code can't be empty";
    public static final String ERROR_COUNTRY_REQUIRED = "country can't be empty";
    public static final String ERROR_ADDRESS_TYPE_REQUIRED = "address type can't be empty";
    public static final String ERROR_ADDRESS_NOT_FOUND = "couldn't find requested address for the given user";

    // Car-related Error Messages
    public static final String ERROR_MAKE_REQUIRED = "make can't be empty";
    public static final String ERROR_MODEL_REQUIRED = "model can't be empty";
    public static final String ERROR_YEAR_TOO_OLD = "year must be after "+CAR_YEAR_MIN;
    public static final String ERROR_YEAR_TOO_NEW = "year must be before "+CAR_YEAR_MAX;
    public static final String ERROR_MILEAGE_NEGATIVE = "mileage can't be negative";

    // Product and Review-related Error Messages
    public static final String ERROR_PRODUCT_NOT_FOUND = "Couldn't find a product";
    public static final String ERROR_REVIEW_NOT_FOUND = "Couldn't find a review for the given user";
    public static final String ERROR_REVIEW_TOO_LOW = "review can't be less than "+RATING_MIN_VALUE;
    public static final String ERROR_REVIEW_TOO_HIGH = "review can't exceed "+RATING_MAX_VALUE;
    public static final String ERROR_COMMENT_REQUIRED = "comment can't be empty";

    // OTP-related Error Messages
    public static final String ERROR_OTP_INVALID = "invalid otp";
    public static final String ERROR_OTP_EXPIRED = "otp is expired";
    public static final String ERROR_OTP_TYPE_MISMATCH = "otp type not match";

    // Service-related Error Messages
    public static final String ERROR_LOCATION_REQUIRED = "location can't be empty";
    public static final String ERROR_CONTACT_INFO_REQUIRED = "contact info can't be empty";
    public static final String ERROR_ITEM_NOT_FOUND = "Item not found";
    public static final String ERROR_COPYING_PROPERTIES = "error copying properties: ";
    public static final String ERROR_PRICE_NEGATIVE = "price can't be negative";
    public static final String ERROR_MANUFACTURER_REQUIRED = "manufacturer can't be empty";
    public static final String ERROR_COMPATIBILITY_REQUIRED = "compatibility can't be empty";
    public static final String ERROR_CAR_CARE_TYPE_REQUIRED = "car care type can't be empty";
    public static final String ERROR_CAR_STATE_REQUIRED = "car state can't be empty";

    // Orders Related Messages
    public static final String ERROR_CART_NOT_FOUND = "couldn't find a cart";
    public static final String ERROR_QUANTITY_NEGATIVE = "quantity can't be negative";
    public static final String ERROR_ORDER_NOT_FOUND = "couldn't find order";


    // Success Messages
    public static final String EMAIL_VERIFIED = "email verified";
    public static final String OTP_SENT = "otp sent for verification";
    public static final String PASSWORD_RESET_SUCCESS = "password reset successfully";
    public static final String CART_SUCCESSFULLY_ADDED = "cart successfully added";
    public static final String ORDER_PLACED = "order placed";
    public static final String ORDER_CONFIRMED = "order confirmed";
    public static final String ORDER_REJECTED = "order rejected";
    public static final String ORDER_COMPLETED = "order completed";
    public static final String ORDER_CANCELLED = "order cancelled";

    // Email Messages
    public static final String EMAIL_SENT_TO_ADMIN = "email sent to an admin for approval";
    public static final String VERIFY_EMAIL_SUBJECT = "your account verification code";
    public static final String RECOVER_PASSWORD_SUBJECT = "your Password Recovery OTP";
    public static final String VERIFY_EMAIL_BODY =
            """
            Dear {0},
            Thank you for registering with Tombile.
            To complete your registration, please verify your email address.
            Please use the following One-Time Password (OTP) to verify your email address: {1}
            If you did not create an account, please ignore this email.
            """;
    public static final String RECOVER_PASSWORD_BODY =
            """
            Dear {0},
            We received a request to reset your password for your Tombile account.
            If you did not request a password reset, please ignore this email.
            To reset your password, please use the following One-Time Password (OTP): {1}
            """;
}
