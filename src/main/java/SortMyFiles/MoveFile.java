package SortMyFiles;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;


public class MoveFile {
    private FileDatabase db;

    public MoveFile(){
        db = new FileDatabase();
    }
    //probably make this a static class or make all functions static
    private String combine(String originalPath, String pathToAdd){
        File original = new File(originalPath);
        return new File(original,pathToAdd).toPath().normalize().toString();
    }

    /*
    this function will change input path to output path (rename it)
     */
    public boolean renameFile(String currentPath,String newPath){
        Path from = Path.of(currentPath);
        Path to =  Path.of(newPath);

        try {
            Files.move(from, to, StandardCopyOption.REPLACE_EXISTING);
            //standard copy operation makes this not fail if used on own directory
            db.updateFile(from.toAbsolutePath().normalize().toString(), to.toAbsolutePath().normalize().toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean moveFile(String currentPath,String newPath){
        Path from = Path.of(currentPath);
        Path to =  Path.of(newPath);

        try {
            Files.move(from, to);
            //standard copy operation makes this not fail if used on own directory
            db.updateFile(from.toAbsolutePath().normalize().toString(), to.toAbsolutePath().normalize().toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




    //this function will move a single file from one directory to another
    public boolean copyFile(String currentPath,String newPath){
        Path from = Path.of(currentPath);
        Path to =  Path.of(newPath);

        try {
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(from, to, options);
            //standard copy operation makes this not fail if used on own directory
            db.copyFile(from.toRealPath().toString(),to.toRealPath().toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFile(String currentPath) throws IOException{
        File file = new File(currentPath);
        db.deleteFile(file.getCanonicalPath());

        return file.delete();
    }

    /*
    this function will move all functions with a certain extension from a target directory to a location directory.
    will have an option to implement recursive moving
     */
    public boolean moveFileExtRecursive(String currentPath, String fileExt, String newPath) throws IOException{
        Path source = Path.of(currentPath);
        Path destination =  Path.of(newPath);

        Files.createDirectories(destination);
        Files.walk(source)
                .filter(path -> path.toString().endsWith(fileExt))
                .forEach(path -> {
                    Path dest = destination.resolve(source.relativize(path));
                    try{
                        Files.createDirectories(dest.getParent());
                        Files.move(path,dest);
                        db.updateFile(path.toAbsolutePath().normalize().toString(), dest.toAbsolutePath().normalize().toString());
                    }catch(IOException e){
                        e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
        return true;
    }

    public void moveFileExt(String currentPath,String newPath,String fileExt) throws IOException{
        //need error checking and a lot of fixes
        File f1 = new File(currentPath);
        Path dest = Path.of(newPath);
        Files.createDirectories(dest);

        FilenameFilter filter = (dir, name) -> name.endsWith(fileExt);
        File[] files = f1.listFiles(filter);
        if(files!=null) {
            for (File f : files) {
                Files.move(f.toPath(), dest);
                db.updateFile(f.toPath().toAbsolutePath().normalize().toString(), dest.toAbsolutePath().normalize().toString());
            }
        }
    }



}
