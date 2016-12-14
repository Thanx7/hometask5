package com.epam.gomel.lib.drive.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.gomel.framework.report.Logger;
import com.epam.gomel.framework.util.GenerateDataUtils;
import com.epam.gomel.lib.drive.CustomFile;
import com.epam.gomel.lib.drive.screen.TopMenuPage;

import ru.yandex.qatools.allure.annotations.Step;

public class DriveService {
    private static final int ONE_THOUSAND = 1000;

    @Step("Upload file")
    public static void uploadFile(CustomFile file) {
        new TopMenuPage().uploadFile(file.getPath() + file.getName());
    }

    @Step("Check is file present in files")
    public static boolean isFilePresentInFiles(CustomFile file) {
        return new TopMenuPage().openFilesPage().isTrueFile(file.getName());
    }

    @Step("Remove several files")
    public static void removeSeveralFiles(List<CustomFile> files) {
        List<String> filesName = new ArrayList<String>();
        for (CustomFile file : files) {
            filesName.add(file.getName());
        }
        new TopMenuPage().openFilesPage().removeFiles(filesName);
    }

    @Step("Remove file permanently")
    public static boolean removeFilePermanently(CustomFile file) {
        new TopMenuPage().openTrashPage().markFile(file.getName()).pressRemoveButton();
        return true;
    }

    @Step("Check is removed file from files")
    public static boolean isRemovedFileFromFiles(CustomFile file) {
        return new TopMenuPage().openTrashPage().isFilePresent(file.getName());
    }

    @Step("Restore file")
    public static void restoreFile(CustomFile file) {
        new TopMenuPage().openTrashPage().markFile(file.getName()).pressRestoreButton();
    }

    @Step("Download file")
    public static void downloadFile(CustomFile file) {
        new TopMenuPage().openFilesPage().markFile(file.getName()).pressDownloadButton();
    }

    @Step("Check is successful download")
    public static void waitUntilDownloadFile(CustomFile customFile) {
        File file = new File(GenerateDataUtils.getDownloadDirectoryPath(), customFile.getName());
        try {
            while (!file.canWrite()) {
                Thread.sleep(ONE_THOUSAND);
            }
            Logger.info("Checking is file " + customFile.getPath() + customFile.getName() + "has been downloaded");
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static boolean isFilesEquals(CustomFile customFile) {
        File file = new File(GenerateDataUtils.getDownloadDirectoryPath(), customFile.getName());
        Logger.info("Checking is file " + customFile.getPath() + customFile.getName() + " and file " + file.getPath()
                        + " are equals");
        FileInputStream uploadedFileStream;
        FileInputStream downloadedFileStream;
        try {
            uploadedFileStream = new FileInputStream(customFile.getPath() + customFile.getName());
            String md5Uploaded = org.apache.commons.codec.digest.DigestUtils.md5Hex(uploadedFileStream);
            uploadedFileStream.close();
            downloadedFileStream = new FileInputStream(file.getPath());
            String md5Downloaded = org.apache.commons.codec.digest.DigestUtils.md5Hex(downloadedFileStream);
            downloadedFileStream.close();
            Logger.debug(md5Uploaded + " == " + md5Downloaded + " ?");
            return md5Uploaded.equals(md5Downloaded);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
