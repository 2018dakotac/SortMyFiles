package SortMyFiles.POC;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//https://mkyong.com/java/how-to-get-the-current-working-directory-in-java/
public class DirectoryFunctions {

    private static void printCurrentWorkingDirectory1() {
        String userDirectory = System.getProperty("user.dir");
        System.out.println(userDirectory);
    }
    // Path, Java 7
    private static void printCurrentWorkingDirectory2() {
        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        System.out.println(userDirectory);
    }

    // File("")
    private static void printCurrentWorkingDirectory3() {
        String userDirectory = new File("").getAbsolutePath();
        System.out.println(userDirectory);
    }

    // FileSystems
    private static void printCurrentWorkingDirectory4() {
        String userDirectory = FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString();
        System.out.println(userDirectory);
    }
    public void createDirectory(String directoryPath) {
        //create new directory does nothing if it already exists
        try {
            Path target = Paths.get(directoryPath);
            Files.createDirectories(target);
        } catch (
    IOException e) {
        e.printStackTrace();
    }
    }
}

