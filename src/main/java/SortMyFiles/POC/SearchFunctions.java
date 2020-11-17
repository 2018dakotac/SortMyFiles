package SortMyFiles.POC;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SearchFunctions {

    public String[] listAllFiles(String path) {
        // Creates an array to store names of files and directories


        // open file
        File f = new File(path);

        // Populates the array with names of files and directories
        String[] pathnames = f.list();

       return pathnames;
    }

    public String[] listAllSubDirectories(String path){
        File file = new File(path);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return directories;
    }
    public String[] listCertainExtension(String path,String extension) {
        File file = new File(path);
        String[] matchingFiles = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return  name.endsWith(extension);
            }
        });
        return matchingFiles;
    }
}
