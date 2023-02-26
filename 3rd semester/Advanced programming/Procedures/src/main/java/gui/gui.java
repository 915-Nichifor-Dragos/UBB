package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader programListLoader = new FXMLLoader();
        programListLoader.setLocation(gui.class.getResource("ChooseProgram.fxml"));
        Parent programListRoot = programListLoader.load();
        Scene programListScene = new Scene(programListRoot, 900, 400);
        ChooseProgramController programChooserController = programListLoader.getController();
        primaryStage.setTitle("Select a program");
        primaryStage.setScene(programListScene);
        primaryStage.show();

        FXMLLoader programExecutorLoader = new FXMLLoader();
        programExecutorLoader.setLocation(gui.class.getResource("ExecuteProgram.fxml"));
        Parent programExecutorRoot = programExecutorLoader.load();
        Scene programExecutorScene = new Scene(programExecutorRoot, 700, 500);
        ExecutorProgramController programExecutorController = programExecutorLoader.getController();
        programChooserController.setProgramExecutorController(programExecutorController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(programExecutorScene);
        secondaryStage.show();

    }
}
