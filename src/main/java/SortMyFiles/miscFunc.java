package SortMyFiles;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class miscFunc {

    public static String combine(String originalPath, String pathToAdd){
        File original = new File(originalPath);
        return new File(original,pathToAdd).toPath().normalize().toString();
    }

    public static Boolean isDirectoryPath(String path){
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
