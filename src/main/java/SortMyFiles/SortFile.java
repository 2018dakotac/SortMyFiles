package SortMyFiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;


public class SortFile {

    private FileDatabase db;
    //public constructor to make sure database object is created and secured connection to database
    public SortFile(){
        db = new FileDatabase();
    }

    public void tagSort(String currentPath,String newPath) throws IOException{
        if(miscFunc.isDirectoryPath(currentPath)) {
            tagMoveRecursive(currentPath, newPath);
        }else{
            tagMove(currentPath, newPath);
        }
    }
    // might need to ad a folder for a tag? or just directly to destination directory
    private void tagMove(String currentPath,String newPath) throws IOException{
        Path source = Path.of(currentPath);
        Path destination = Path.of(newPath);
        Files.createDirectories(destination);
        String tag = db.getFileSingleTag(source.toAbsolutePath().normalize().toString());
        Path dest = destination.resolve(tag);
        Files.createDirectories(dest);
        Files.move(source,dest.resolve(source.getFileName()));
        db.updateFile(source.toAbsolutePath().normalize().toString(), dest.resolve(source.getFileName()).toAbsolutePath().normalize().toString());
    }

    private boolean tagMoveRecursive(String currentPath,String newPath) throws IOException{
        Path source = Path.of(currentPath);
        Path destination =  Path.of(newPath);

        Files.createDirectories(destination);
        Files.walk(source)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    String tag = db.getFileSingleTag(source.toAbsolutePath().normalize().toString());
                    Path dest = destination.resolve(tag);
                    try{
                        Files.createDirectories(dest);
                        Files.move(path,dest.resolve(path.getFileName()));
                        db.updateFile(path.toAbsolutePath().normalize().toString(), dest.resolve(source.getFileName()).toAbsolutePath().normalize().toString());
                    }catch(IOException e){
                        //e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
        return true;
    }
    public void extensionSort(String currentPath,String newPath) throws IOException{

        if(miscFunc.isDirectoryPath(currentPath)) {
            moveFileExtRecursive(currentPath, newPath);
        }else{
            moveFileExt(currentPath, newPath, FilenameUtils.getExtension(currentPath));
        }

    }

    private void moveFileExt(String currentPath,String newPath,String fileExt) throws IOException{
        Path source = Path.of(currentPath);
        Path destination = Path.of(newPath);
        Files.createDirectories(destination);
        Path dest = destination.resolve(FilenameUtils.getExtension(source.toAbsolutePath().toString()));
        Files.createDirectories(dest);
        Files.move(source,dest.resolve(source.getFileName()));
        db.updateFile(source.toAbsolutePath().normalize().toString(), dest.resolve(source.getFileName()).toAbsolutePath().normalize().toString());
    }
    /*
    this function will move all  target directory to a location directory in a folder of their type
     */
    private boolean moveFileExtRecursive(String currentPath,String newPath) throws IOException{
        Path source = Path.of(currentPath);
        Path destination =  Path.of(newPath);

        Files.createDirectories(destination);
        Files.walk(source)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    Path dest = destination.resolve(FilenameUtils.getExtension(path.toAbsolutePath().toString()));
                    try{
                        Files.createDirectories(dest);
                        Files.move(path,dest.resolve(path.getFileName()));
                        db.updateFile(path.toAbsolutePath().normalize().toString(), dest.resolve(source.getFileName()).toAbsolutePath().normalize().toString());
                    }catch(IOException e){
                        //e.printStackTrace();
                        throw new UncheckedIOException(e);
                    }

                });
        return true;
    }

}
