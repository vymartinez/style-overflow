module br.com.styleoverflow.styleoverflow {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires com.zaxxer.hikari;

    opens br.com.styleoverflow.styleoverflow to javafx.fxml;
    exports br.com.styleoverflow.styleoverflow;
}