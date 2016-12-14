package com.epam.gomel.framework.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.epam.gomel.lib.drive.CustomFile;

public class GenerateDataUtils {

    private static final String DOWNLOAD_DIRECTORY_PATH = FileUtils.getTempDirectoryPath() + "download\\";

    public static String getDownloadDirectoryPath() {
        return DOWNLOAD_DIRECTORY_PATH;
    }

    public static void createFile(CustomFile file) {
        try {
            File folderD = new File(getDownloadDirectoryPath());
            folderD.mkdirs();

            File folder = new File(file.getPath());
            folder.mkdirs();

            File fileTXT = new File(folder, file.getName());

            FileWriter writer = new FileWriter(fileTXT, false);
            writer.write(file.getContext());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}
