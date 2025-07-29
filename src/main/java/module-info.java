module io.github.samiv.sortwavefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    opens io.github.samiv.sortwavefx to javafx.fxml;
    exports io.github.samiv.sortwavefx;
}