module com.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires jlayer;
    requires java.flac.encoder;
    requires java.desktop;
    requires java.google.speech.api;
    requires java.net.http;

    opens com.dictionary to javafx.fxml;
    opens com.dictionary.Controllers to javafx.fxml;
    opens com.dictionary.Controllers.Content to javafx.fxml;
    opens com.dictionary.Models to javafx.fxml;
    opens com.dictionary.Views to javafx.fxml;
    opens com.dictionary.Controllers.Content.GGTranslate to javafx.fxml;
    //opens com.dictionary.Controllers.Content.Grammarly to javafx.fxml;

    exports com.dictionary;
    exports com.dictionary.Views;
    exports com.dictionary.Controllers;
    exports com.dictionary.Controllers.Content;
    exports com.dictionary.Models;
    exports com.dictionary.Controllers.Content.GGTranslate;
    //exports com.dictionary.Controllers.Content.Grammarly;
}