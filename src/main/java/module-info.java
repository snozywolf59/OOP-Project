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
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires javafx.media;
    requires freetts;
    requires AnimateFX;
    requires com.google.api.apicommon;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires google.cloud.core;
    requires google.cloud.core.grpc;
    requires com.google.auth;

    opens com.dictionary to javafx.fxml;
    opens com.dictionary.Controllers to javafx.fxml;
    opens com.dictionary.Controllers.Content to javafx.fxml;
    opens com.dictionary.Models to javafx.fxml;
    opens com.dictionary.Views to javafx.fxml;
    opens com.dictionary.Controllers.Content.API to javafx.fxml, com.google.gson;
    opens com.dictionary.Controllers.Content.Learn to javafx.fxml;
    opens com.dictionary.Controllers.Content.Search to javafx.fxml;
    opens com.dictionary.Controllers.Welcome to javafx.fxml;

    exports com.dictionary;
    exports com.dictionary.Views;
    exports com.dictionary.Controllers;
    exports com.dictionary.Controllers.Content;
    exports com.dictionary.Models;
    exports com.dictionary.Controllers.Content.API;
    exports com.dictionary.Controllers.Content.Learn;
    exports com.dictionary.Controllers.Content.Search;
}