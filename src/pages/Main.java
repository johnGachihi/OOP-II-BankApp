package pages;

import Tools.StageSetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loginPageLoader = new FXMLLoader(getClass().getResource("AccountCreate.fxml"));
        Parent loginPage = loginPageLoader.load();

        StageSetter stageSetter = loginPageLoader.getController();
        stageSetter.setStage(primaryStage);

        primaryStage.setTitle("BankApp");
        primaryStage.setScene(new Scene(loginPage, 760, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
