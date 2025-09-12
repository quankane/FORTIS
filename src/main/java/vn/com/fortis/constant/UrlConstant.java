package vn.com.fortis.constant;

public class UrlConstant {

    private UrlConstant() {}

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        public static final String LOGIN = PRE_FIX + "/login";
//        public static final String LOGIN_WITH_GOOGLE = PRE_FIX + "/google";
        public static final String REGISTER = PRE_FIX + "/register";
        public static final String VERIFY_OTP = PRE_FIX + "/verify-otp";
        public static final String FORGOT_PASSWORD = PRE_FIX + "/forgot-password";
        public static final String VERIFY_OTP_TO_RESET_PASSWORD = PRE_FIX + "/verify-otp-to-reset-password";
        public static final String RESET_PASSWORD = PRE_FIX + "/reset-password";
        public static final String REFRESH_TOKEN = PRE_FIX + "/refresh";
        public static final String LOGOUT = PRE_FIX + "/logout";

        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/user";

//        public static final String GET_USERS = PRE_FIX;
//        public static final String GET_USER = PRE_FIX + "/{userId}";
//        public static final String GET_CURRENT_USER = PRE_FIX + "/current";
//
//        public static final String FILL_PERSONAL_INFORMATION = PRE_FIX + "/personal-information";
        public static final String UPLOAD_AVATAR = PRE_FIX + "/upload-avatar";
        public static final String DELETE_MY_ACCOUNT = PRE_FIX + "/delete-my-account";

        public static final String GET_PROFILE = PRE_FIX + "/profile";
        public static final String UPDATE_PROFILE = PRE_FIX + "/update-profile";
        public static final String UPDATE_PASSWORD = PRE_FIX + "/update-password";

        private User() {
        }
    }

    public static class Category {

        private Category() {}

        private static final String PRE_FIX = "/category";

        public static final String ADD_CATEGORY = PRE_FIX;
        public static final String UPDATE_CATEGORY = PRE_FIX + "/{categoryId}";
        public static final String DELETE_CATEGORY = PRE_FIX + "/{categoryId}";
        public static final String GET_CATEGORY_BY_ID = PRE_FIX + "/{categoryId}";
        public static final String GET_ALL_CATEGORY = PRE_FIX;
        public static final String GET_CATEGORY_BY_NAME = PRE_FIX + "/name/{categoryName}";

    }

    public static class Promotion {

        private Promotion() {}

        private static final String PRE_FIX = "/promotion";

        public static final String ADD_PROMOTION = PRE_FIX;
        public static final String UPDATE_PROMOTION = PRE_FIX + "/{promotionId}";
        public static final String DELETE_PROMOTION = PRE_FIX + "/{promotionId}";
        public static final String GET_PROMOTION_BY_ID = PRE_FIX + "/{promotionId}";
        public static final String GET_ALL_PROMOTION = PRE_FIX;
        public static final String GET_PROMOTION_BY_CODE = PRE_FIX + "/code/{promotionCode}";
    }

    public static class Email {

        private Email() {}

        private static final String PRE_FIX = "/email";

        public static final String SEND_REGISTRATION_OTP_BY_EMAIL = PRE_FIX + "/verify-registration";

        public static final String SEND_FORGOT_PASSWORD_OTP_BY_EMAIL = PRE_FIX + "/sendForgotPasswordOtpByEmail";
    }

}
