module br.com.styleoverflow.styleoverflow {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens br.com.styleoverflow.styleoverflow to javafx.fxml, javafx.base;
    opens br.com.styleoverflow.styleoverflow.classes to javafx.fxml, javafx.base;
    opens br.com.styleoverflow.styleoverflow.services to javafx.fxml, javafx.base;
    exports br.com.styleoverflow.styleoverflow;
    exports br.com.styleoverflow.styleoverflow.screens;
    exports br.com.styleoverflow.styleoverflow.classes;
}
