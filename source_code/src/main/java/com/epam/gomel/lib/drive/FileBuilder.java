package com.epam.gomel.lib.drive;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class FileBuilder {
    private static final int FIVE = 5;
    private static final int TEN = 10;

    public static CustomFile getCustomFile() {
        CustomFile file = new CustomFile();
        file.setName(RandomStringUtils.randomAlphanumeric(FIVE) + ".txt");
        file.setContext(RandomStringUtils.randomAlphanumeric(TEN));
        file.setPath(FileUtils.getTempDirectoryPath());
        return file;
    }
}
