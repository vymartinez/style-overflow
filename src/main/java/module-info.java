module br.com.styleoverflow.styleoverflow {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens br.com.styleoverflow.styleoverflow to javafx.fxml;
    exports br.com.styleoverflow.styleoverflow;
}