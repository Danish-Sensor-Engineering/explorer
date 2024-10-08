package dse.demo;

import atlantafx.base.theme.PrimerLight;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class Main extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(new Locale("C"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/icon.png"))));
        stage.setTitle("Demo - Danish Sensor Engineering");
        stage.setScene(scene);
        stage.show();

        //this makes all stages close and the app exit when the main stage is closed
        stage.setOnCloseRequest(e -> Platform.exit());

        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }

}