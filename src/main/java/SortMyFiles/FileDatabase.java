package SortMyFiles;

public class FileDatabase {
    /*
    will need a contructor to initialize the embedded data base
    and also check to see if the in memory storage has been
    created in the file system
    may need to be passing a data base object as a parameter idk
    */

    /*
    this function will copy a file to a new location and update the data base
     */

    public boolean copyFile(String currentPath, String fileName, String newPath){

        return true;
    }

    /*
    this function will add a tag to the file within the data base
     */
    public boolean addTag(String filePath, String tag){

        return true;
    }

    /*
    this function will return a list of files with the searched tag
    not sure on return type
     */
    public String[] returnTag(String tag){
        String [] empty = {};
        return empty;
    }

    /*
    this function will return a log recent sorts?
     */
    public String[] returnSortLog(){
        String [] empty = {};
        return empty;
    }


}
