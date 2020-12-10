module SortMyFiles {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires org.apache.commons.io;

    opens SortMyFiles to javafx.fxml;
    opens SortMyFiles.Controllers;

    exports SortMyFiles;

}