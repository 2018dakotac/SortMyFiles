package SortMyFiles;

import java.io.IOException;
import java.nio.file.*;

public class FileMove {

    public void moveFile(String sourceStr,String targetStr) {


        String fromFile = "D:\\SAMPLE SORTING DIRECTORY\\source directory\\sample.txt";
        String toFile = "D:\\SAMPLE SORTING DIRECTORY\\target directory\\sample.txt";

        //Path source = Paths.get(fromFile);
       // Path target = Paths.get(toFile);
        Path source = Paths.get(sourceStr);
        Path target = Paths.get(targetStr);

        try {


            // rename or move a file to other path
            // if target exists, throws FileAlreadyExistsException
            Files.move(source, target);

            // if target exists, replace it.
            // Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

            // multiple CopyOption
            /*CopyOption[] options = { StandardCopyOption.REPLACE_EXISTING,
                                StandardCopyOption.COPY_ATTRIBUTES,
                                LinkOption.NOFOLLOW_LINKS };

            Files.move(source, target, options);*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}