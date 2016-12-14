package com.epam.gomel.tests.yandexdisk;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.gomel.framework.browser.Browser;
import com.epam.gomel.framework.util.GenerateDataUtils;
import com.epam.gomel.lib.common.Account;
import com.epam.gomel.lib.common.AccountBuilder;
import com.epam.gomel.lib.drive.CustomFile;
import com.epam.gomel.lib.drive.FileBuilder;
import com.epam.gomel.lib.drive.service.DriveService;
import com.epam.gomel.lib.drive.service.LoginService;

public class DownloadFileTest {

    Account account = AccountBuilder.getDefaultAccount();
    CustomFile file = FileBuilder.getCustomFile();

    @BeforeClass
    public void loginAndUpload() {
        GenerateDataUtils.createFile(file);
        LoginService.login(account);
        DriveService.uploadFile(file);
    }

    @Test
    public void downloadFileTest() {
        DriveService.downloadFile(file);
        DriveService.waitUntilDownloadFile(file);
        assertTrue(DriveService.isFilesEquals(file));
    }

    @AfterClass
    public void exit() {
        Browser.kill();
    }
}
