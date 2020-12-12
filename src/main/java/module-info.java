module SortMyFiles {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires org.apache.commons.io;

    opens com.SortMyFiles to javafx.fxml;
    opens com.SortMyFiles.Controllers;

    exports com.SortMyFiles;

}