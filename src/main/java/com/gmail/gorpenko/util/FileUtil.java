package com.gmail.gorpenko.util;

import java.io.File;

/**
 * author Maksim Diland
 */
public class FileUtil {
    public static boolean fileNotExist(File file) {
        return !file.exists();
    }
}
