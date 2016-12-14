package com.epam.gomel.lib.mail;

import com.epam.gomel.framework.util.LetterUtils;
import com.epam.gomel.lib.common.Constants;

public class LetterBuilder {

    public static Letter getEmptyLetter() {
        Letter letter = new Letter();
        letter.setRecipient(Constants.EMAIL);
        return letter;
    }

    public static Letter getFullLetter() {
        Letter letter = getEmptyLetter();
        letter.setSubject(LetterUtils.getSubject());
        letter.setBody(LetterUtils.getBody());
        letter.setAttachment(LetterUtils.getAttachment());
        return letter;
    }
}
