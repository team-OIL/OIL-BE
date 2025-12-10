package com.example.OIL.global.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageProperty {
    // loginId
    public static final String LOGIN_ID_NOT_BLANK = "이메일 또는 아이디는 필수 입력 항목입니다.";

    // email
    public static final String EMAIL_INVALID = "올바른 형식의 이메일을 입력해주세요.";
    public static final String EMAIL_NOT_BLANK = "이메일은 필수 입력 항목입니다.";

    // password
    public static final String PASSWORD_PATTERN = "비밀번호는 영어 대소문자, 숫자만 허용되며 @, #, !, %, &, * 중 하나 이상을 포함해야 합니다.";
    public static final String PASSWORD_NOT_BLANK = "비밀번호는 필수 입력 항목입니다.";
    public static final String PASSWORD_SIZE = "비밀번호는 최소 8자 이상, 30자 이하로 입력해주세요.";

    // userName
    public static final String USERNAME_PATTERN = "이름은 한글 또는 영어 대소문자만 입력 가능합니다.";
    public static final String USERNAME_SIZE = "이름은 20자 이내로 입력해주세요.";

    // token
    public static final String TOKEN_NOT_BLANK = "인증 토큰은 필수 입력 항목입니다.";
    public static final String DEVICE_TOKEN_NOT_BLANK = "디바이스 토큰은 필수 입력 항목입니다.";

    // missionTime
    public static final String MISSION_TIME_NOT_BLANK = "미션 시간은 필수 입력 항목입니다.";
    public static final String MISSION_TIME_INVALID = "올바른 미션 시간 형식을 입력해주세요.";
    public static final String MISSION_TIME_RANGE = "미션 시간은 허용된 범위 내에서 입력해야 합니다.";

    //isAlarmEnabled
    public static final String ALARM_NOT_NULL = "알림 설정 여부는 필수 항목입니다.";
}
