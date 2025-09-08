module io.github.samiv.sortwavefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires javafx.media;

    // Allow modules access to their relevant packages
    opens io.github.samiv.sortwavefx.ui to javafx.fxml;
    opens io.github.samiv.sortwavefx.sounds to javafx.media;

    exports io.github.samiv.sortwavefx;
}