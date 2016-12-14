package com.epam.gomel.tests.yandexdisk;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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

public class RemoveFileTest {

    Account account = AccountBuilder.getDefaultAccount();
    CustomFile file = FileBuilder.getCustomFile();

    @BeforeClass
    public void loginAndUpload() {
        GenerateDataUtils.createFile(file);
        LoginService.login(account);
        DriveService.uploadFile(file);
    }

    @Test
    public void removeFileTest() {
        List<CustomFile> files = new ArrayList<CustomFile>();
        files.add(file);
        DriveService.removeSeveralFiles(files);
        boolean isRemovedFromFiles = DriveService.isRemovedFileFromFiles(file);
        assertTrue(isRemovedFromFiles && DriveService.removeFilePermanently(file));
    }

    @AfterClass
    public void exit() {
        Browser.kill();
    }
}
