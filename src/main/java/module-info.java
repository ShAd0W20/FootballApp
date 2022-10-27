module com.shad0wstv.footballapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;
    requires annotations;


    opens com.shad0wstv.footballapp to javafx.fxml;
    exports com.shad0wstv.footballapp;

    opens com.shad0wstv.footballapp.models to javafx.base;

    opens com.shad0wstv.footballapp.views to javafx.fxml;
    exports com.shad0wstv.footballapp.views;
}