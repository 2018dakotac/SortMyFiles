package SortMyFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateDirectory extends Directory {
    //probably make this a static class or make all functions static

    /*
    this function will create a folder in the given directory
     */
    public void createDirectory(String directoryPath) {
        //create new directory does nothing if it already exists
        try {
            Path target = Paths.get(directoryPath);
            Files.createDirectories(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean createDirectory(String directoryPath,String directoryName){
    //see POC

        return true;
    }

}
