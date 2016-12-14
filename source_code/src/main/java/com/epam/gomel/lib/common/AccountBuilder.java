package com.epam.gomel.lib.common;

import org.apache.commons.lang3.RandomStringUtils;

public class AccountBuilder {
    private static final int THREE = 3;

    public static Account getDefaultAccount() {
        Account account = new Account();
        account.setUserName(Constants.LOGIN);
        account.setPassword(Constants.CORRECT_PASS);
        account.setEmail(Constants.EMAIL);
        return account;
    }

    public static Account getDefaultMailRuAccount() {
        Account account = new Account();
        account.setUserName(Constants.EMAIL);
        account.setPassword(Constants.CORRECT_PASS);
        account.setEmail(Constants.EMAIL);
        return account;
    }

    public static Account getAccountWithWrongPass() {
        Account account = getDefaultAccount();
        account.setPassword(account.getPassword() + RandomStringUtils.randomAlphabetic(THREE));
        return account;
    }
}
