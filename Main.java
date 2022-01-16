

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * @author Harjit Singh
 * This is the application's main class, run it to view the Quiz. It has a
 * startGui that displays an image and has a "Start" button to start play
 * and "Help" button to quickly break down what each feature of the Quiz".
 * It also creates a rootPane which that containsRooms for each aspect of the Quiz.
 */

public class Main extends Application {

    private BorderPane rootPane = new BorderPane();
    private BorderPane startGui = new BorderPane();
    private Button start = new Button("Start");
    private Button help = new Button("Help");
    private Scene scene1 = new Scene(startGui, 1000, 500);
    private Scene scene2 = new Scene(rootPane, 1000, 500);
    private  Manager m;

    /**
     * @author Harjit Singh
     * Create the 2 button display it on borderpane
     * @param primaryStage creating a Stage object
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Quiz Game HashMap");
        showIntroScene(primaryStage);
        m = new Manager();
        rootPane.setCenter(m.MainStageQuiz(primaryStage,scene1));
        try {
            primaryStage.getIcons().add(new Image(this.getClass().getResource("backgound.jpg").toString()));
        } catch(Exception e){
            showError(new Exception("Can't find " + e.getMessage(), e), true);
        }

    }
    /**
     * @author Harjit Singh
     * Welcome scene with the Background Start button.
     * Create the scene with the TabPane.
     * @param window
     */


    private void showIntroScene(Stage window) {

        try {
            Image img = new Image("backgound.jpg");
            startGui.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT)));
        }
         catch(Exception e){
                showError(new Exception("Can't find " + e.getMessage(), e), true);
            }

        HBox buttons = new HBox(start,help);//Button on the top
        buttons.setSpacing(50);
        buttons.setPadding(new Insets(40, 10, 10, 120));
        start.setStyle("-fx-background-color: YELLOW");
        start.setTooltip(new Tooltip("Click here to play"));
        startGui.setBottom(buttons);
        buttons.setAlignment(Pos.CENTER);
        start.setOnAction(e -> window.setScene(scene2));
        help.setOnAction(e -> help());
        window.setScene(scene1);
        window.show();
    }
    /**
     * @author Harjit Singh
     * Displays Alert message to Help the user and them
     * user-friendly experience
     * @return alert
     */
    private Alert help(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WE ARE HERE TO HELP YOU");
        alert.setHeaderText(null);
        alert.setContentText("**WELCOME TO THE QUIZ GAME!" +
                "\n  -THIS GAME WILL TEST THE KNOWLEDGE OF THOSE WHO ARE BRAVE ENOUGH!" +
                "\n**To start the game, please click the START button shown on the main menu!" +
                "\n**Once the game starts, you have the following options:" +
                "\n  -You can choose to access the rooms available by clicking the respective buttons: ROOM1 and ROOM2" +
                "\n  -Once a room is chosen, the number of players assigned to each room will be displayed!" +
                "\n  -There is a text space where a player must answer the prompted question carefully," +
                "\n   BECAUSE THERE IS NO ROOM FOR MISTAKES! A player that answers incorrectly will be GONE FOREVER..." +
                "\n  -To verify an answer, once there is an answer on the text space beforehand, please click the GO button" +
                "\n  -To move to another question, please click the NEXT QUESTION button" +
                "\n  -To go back to a previous prompted question, please click the PREVIOUS button shown" +
                "\n  -The BACK button will take you back to the main menu" +
                "\n  -And for those who wish to end the game, the QUIT button will close the program"  );

        alert.show();
        return alert;
    }
        /**
         * @author Harjit Singh
         * The Exception handler
         * Displays a error message to the user
         * and if the error is bad enough closes the program
         * @param fatal true if the program should exit. false otherwise
         */
        private void showError(Exception e,boolean fatal){
            String msg = e.getMessage();
            if(fatal){
                msg = msg + " \n\nthe program will now close";
            }
            Alert alert = new Alert(Alert.AlertType.ERROR,msg);
            alert.setResizable(true);
            alert.getDialogPane().setMinWidth(420);
            alert.setTitle("Error");
            alert.setHeaderText("something went wrong");
            alert.showAndWait();
            if(fatal){
                System.exit(666);
            }
        }
    public static void main(String[] args) {
        launch(args);
    }
}


