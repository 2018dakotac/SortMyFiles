package com.SortMyFiles;

import java.io.File;
import java.io.IOException;

public class miscFunc {//Miscellaneous functions statically defined so they can be used by all classes that need them

    public static String combine(String originalPath, String pathToAdd){
        File original = new File(originalPath);
        return new File(original,pathToAdd).toPath().normalize().toString();
    }

    public static boolean isDirectoryPath(String path){
        File file = new File(path);
        return file.isDirectory();
    }
    public static boolean isFilenameValid(String file) {
        File f = new File(file);
        try {
            f.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
