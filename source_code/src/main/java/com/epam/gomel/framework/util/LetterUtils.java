package com.epam.gomel.framework.util;

import org.apache.commons.lang3.RandomStringUtils;

public class LetterUtils {
    private static final int SEVEN = 7;
    private static final int THIRTY = 30;

    public static String getSubject() {
        return RandomStringUtils.randomAlphanumeric(SEVEN);
    }

    public static String getBody() {
        return RandomStringUtils.randomAlphanumeric(THIRTY);
    }

    public static String getAttachment() {
        return "attachment.txt";
    }
}
