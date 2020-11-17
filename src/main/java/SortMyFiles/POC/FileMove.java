package SortMyFiles.POC;

import java.io.File;
import java.io.FilenameFilter;
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

    public void moveFileByExtension(String sourcePath,String targetPath){
        //need error checking and a lot of fixes
        File f1 = new File(sourcePath);
        File f2 = new File(targetPath);

        FilenameFilter filter = new FilenameFilter()
        {
            @Override public boolean accept(File dir, String name)
            {
                return name.endsWith(".zip");
            }
        };

        for (File f : f1.listFiles(filter))
        {
            // TODO move to folder2
        }

    }
}