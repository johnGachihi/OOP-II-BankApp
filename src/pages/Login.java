package pages;

import Tools.StageSetter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login implements StageSetter{

    private Stage primaryStage;

    public void openAccountCreate(){
        try{
            FXMLLoader accountCreatePageLoader =
                    new FXMLLoader(getClass().getResource("AccountCreate.fxml"));
            Parent accountCreatePage = accountCreatePageLoader.load();

            StageSetter stageSetter = accountCreatePageLoader.getController();
            stageSetter.setStage(primaryStage);

            primaryStage.setScene(new Scene(accountCreatePage, 760, 450));
            primaryStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void setStage(Stage primaryStage) {
        System.out.println("Login setStage() called");
        this.primaryStage = primaryStage;
        if(primaryStage == null)
            System.out.println("primaryStage is null");
        else
            System.out.println("primaryStage is not null");
    }
}
