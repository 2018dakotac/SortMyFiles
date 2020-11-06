package SortMyFiles;


import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.nio.file.attribute.BasicFileAttributes;

public class FileAttributes{
    public String listFileAttributes(String fileName) {
        String result = "";
        try {

            Path file = Paths.get(fileName);
            BasicFileAttributes attr =
                    Files.readAttributes(file, BasicFileAttributes.class);

            String creationTime = "creationTime: " + attr.creationTime();
           String lastAccessed = "lastAccessTime: " + attr.lastAccessTime();
            String lastModified = "lastModifiedTime: " + attr.lastModifiedTime();
            result = creationTime + "\n"+lastModified +"\n"+lastAccessed +"\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}