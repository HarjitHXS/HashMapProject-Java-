import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.*;
import java.util.*;

/**
 * @author Harjit Singh, Carlos Rodriguez
 * A class that extends BorderPane
 * This class is displaying informations for Game Quiz on Borderpane
 * It use the chain class to add and remove players and the question from the quiz.
 * for the quiz answers are the key and values are the question we take keyset() which has all the keys
 * for the quiz and randomly display them if user you give  correct answer system will remove the question from the list
 * if user give wrong answer user will lose one player from the room he is playing. if user lose all the players he will lose the quiz
 * user for Room 2 will get chance to play
 * Any time user can go from room one to another room
 * user can check the score for each room live anytime user click the score button.
 * user can exit the game anytime he wants too quit button.
 */

public class Manager extends BorderPane {

    private Button quit = new Button("Quit");
    private Button score = new Button("score");
    private Button back = new Button("back");
    private Button room1 = new Button("Room1");
    private Button room2 = new Button("Room2");
    private BorderPane textAreaUp = new BorderPane();

    private Chain<String,String> forquiz = new Chain<>();
    private Chain<String,String> forPlayer = new Chain<>();
    private ArrayList<String> list;
    private ArrayList<String> list2;
    private  ArrayList<String> list3;
    private  ArrayList<String> list4;

    public Manager() {
        this.forquiz = forquiz;
        this.forPlayer = forPlayer;
    }


    /**
     * @authors Harjit Singh
     * Method is the main which display Rooms and buttons, create the list, add the questions and players to the room.
     * @return SplitPane with the BorderPane all the quiz methods.
     */

   public SplitPane MainStageQuiz(Stage window, Scene scene){
        SplitPane splitPane = new SplitPane();
        BorderPane one = new BorderPane();

       addQuestions();
       addPlayers();
       list = new ArrayList<>(forquiz.keySet());// get all the key(Answer) to display the question so we keep track for the question in the quiz for room1
       list2 = new ArrayList<>(forquiz.keySet());// get all the key(Answer) to display the question so we keep track for the question in the quiz for room2
       list3 = forPlayer.getALL("Room1");//list of all the players in room1
       list4 = forPlayer.getALL("Room2");//list of all the players in room2
       one.setBottom(roomButton());
       one.setCenter(textAreaUp);
       room1.setOnMouseClicked(e -> textAreaUp.setCenter(this.disPlayQuizRoom1()));
       room2.setOnMouseClicked(e -> textAreaUp.setCenter(this.disPlayQuizRoom2()));
       score.setOnMouseClicked(e -> textAreaUp.setCenter(this.score()));
       quit.setOnMouseClicked(e -> this.close());
       back.setOnAction(e -> window.setScene(scene));
       splitPane.getItems().addAll(one);
       return splitPane;
   }
    /**
     * @authors Harjit Singh
     * Method has the buttons for the quiz
     * @return HBox with Buttons.
     */
    public HBox roomButton(){
        HBox gameButton = new HBox(back,room1,room2,score,quit);// button on the bottom
        gameButton.setSpacing(20);
        gameButton.setPadding(new Insets(40, 10, 10, 120));
        return gameButton;
    }

    /**
     * @authors Harjit Singh, Carlos Rodriguez
     * Method to display the questions in the Room1 and the buttons
     * it check the Room1 and quiz has no more questions left for this Room.
     * @return BorderPane with the quiz questions for room1.
     */

    public GridPane check(String a) {
        GridPane g = new GridPane();
        if (list.isEmpty()) {
            JOptionPane result = new JOptionPane();
            JOptionPane.showMessageDialog(result, "You Win!, Room2 player chance.");
        } else if (list3.isEmpty()) {
            JOptionPane result = new JOptionPane();
            JOptionPane.showMessageDialog(result, "You lose!, Room2 player chance.");
        } else {

           Object key = list.get(new Random().nextInt(list.size()));// to randomly get a question from the Hash table for quiz
               Text t = new Text((String) forquiz.get((String) key));
               Text text1 = new Text(this.playerInfo(a));
               TextField textField1 = new TextField();
               textField1.setPromptText("First Letter Capital Answer");
               Button ok = new Button("Ok");
               g.add(t, 0, 0);
               g.add(textField1, 2, 0);
               g.add(ok, 3, 0);
               text1.setTranslateX(90);
               g.add(text1,5,0);
               g.setAlignment(Pos.CENTER);
               ok.setOnAction(mouseEvent -> {
                   if (textField1.getText().isEmpty()) {
                       textField1.setPromptText("Enter Answer");
                   } else {
                       String str = textField1.getText();
                       if (list.contains(str)) {//check if the answer is in the list the answer it get from the Hash Table forquiz
                           Text text = new Text("Correct Answer");//if user get correct answer
                           g.add(text, 4, 0);
                           list.remove(key);
                       } else {
                           Text text = new Text("Wrong Answer");//if user get Wrong answer
                           g.add(text, 4, 0);
                               forPlayer.remove(a);//remove the player from the HashTable
                               list3.remove(0);//remove the player from the list for the score
                           }
                       }
               });
       }

        return g;

    }

    /**
     * @authors Harjit Singh, Carlos Rodriguez
     * Method to display the questions in the Room2 and the buttons
     * it check the Room2 and quiz has no more questions left for this Room.
     * @return BorderPane with the quiz questions for room2.
     */
    public GridPane check2(String a) {
        GridPane g = new GridPane();
        if (list2.isEmpty()) {
            JOptionPane result = new JOptionPane();
            JOptionPane.showMessageDialog(result, "You Win!, Room2 player chance.");
        }  else if (list4.isEmpty()) {
            JOptionPane result = new JOptionPane();
            JOptionPane.showMessageDialog(result, "You lose!, Room2 player chance.");
        } else {
            Object key = list2.get(new Random().nextInt(list2.size()));// to randomly get a question from the Hash table for quiz
            Text t = new Text((String) forquiz.get((String) key));
            Text text1 = new Text(this.playerInfo(a));
            TextField textField1 = new TextField();
            textField1.setPromptText("First Letter Capital Answer");
            Button ok = new Button("Ok");
            g.add(t, 0, 0);
            g.add(textField1, 2, 0);
            g.add(ok, 3, 0);
            text1.setTranslateX(90);
            g.add(text1,5,0);
            g.setAlignment(Pos.CENTER);
            ok.setOnAction(mouseEvent -> {
                if (textField1.getText().isEmpty()) {
                    textField1.setPromptText("Enter Answer");
                } else {
                    String str = textField1.getText();
                    if (list2.contains(str)) {//check if the answer is in the list the answer it get from the Hash Table forquiz
                        Text text = new Text("Correct Answer");//if user get correct answer

                        g.add(text, 4, 0);
                        list2.remove(key);

                    } else {
                        Text text = new Text("Wrong Answer");//if user get Wrong answer
                        g.add(text, 4, 0);
                        forPlayer.remove(a);//remove the player from the HashTable
                        list4.remove(0);//remove the player from the list for the score
                    }
                }
            });
        }

        return g;

    }

    /**
     * @authors Harjit Singh
     * Method to display the questions in the Room1 and the buttons
     * call the check() to display question.
     * @return BorderPane with the quiz questions for room1.
     */

    public BorderPane disPlayQuizRoom1(){
        BorderPane borderPane = new BorderPane();
        Text text = new Text("Room1");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Button nextQ = new Button("Next Question");
        Button backQ = new Button("Last Question");
        borderPane.setTop(text);
        borderPane.setCenter(this.check("Room1"));
        HBox QuestionButton = new HBox(backQ,nextQ);// button on the bottom
        QuestionButton.setSpacing(20);
        QuestionButton.setPadding(new Insets(40, 50, 50, 120));
        QuestionButton.setAlignment(Pos.CENTER);
        borderPane.setBottom(QuestionButton);
        backQ.setOnMouseClicked(e -> borderPane.setCenter(this.check("Room1")));//get the last questions
        nextQ.setOnMouseClicked(e -> borderPane.setCenter(this.check("Room1")));//get the next questions

        return borderPane;
    }

    /**
     * @authors Harjit Singh
     * Method to display the questions in the Room2 and the buttons
     * call the check() to display question.
     * @return BorderPane with the quiz questions for room2.
     */

    public BorderPane disPlayQuizRoom2(){
        BorderPane borderPane = new BorderPane();
        Text text = new Text("Room2");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        Button nextQ = new Button("Next Question");
        Button backQ = new Button("Last Question");
        borderPane.setTop(text);
        borderPane.setCenter(this.check2("Room2"));
        HBox QuestionButton = new HBox(backQ,nextQ);// button on the bottom
        QuestionButton.setSpacing(20);
        QuestionButton.setPadding(new Insets(40, 50, 50, 120));
        QuestionButton.setAlignment(Pos.CENTER);
        borderPane.setBottom(QuestionButton);
        backQ.setOnMouseClicked(e -> borderPane.setCenter(this.check2("Room2")));//get the last questions
        nextQ.setOnMouseClicked(e -> borderPane.setCenter(this.check2("Room2")));//get the next questions

        return borderPane;
    }

    /**
     * @authors Harjit Singh
     * Method to add live score to the Quiz
     * @return BorderPane with the score for each room.
     */

    public BorderPane score(){
        BorderPane borderPane = new BorderPane();
        int scoreR1 = list3.size();
        int scoreR2 = list4.size();
        String read = "User1 score   " +"\n" +"\n"+ "    " +scoreR1;
        String read2 = "User2 score   " +"\n"+"\n"+ "    " + scoreR2;
        Text text = new Text(read);
        Text text1 = new Text(read2);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        GridPane scoreBox = new GridPane();
        scoreBox.add(text,0,0);
        scoreBox.add(text1,1,0);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setGridLinesVisible(true);
        borderPane.setCenter(scoreBox);

        return borderPane;
    }
    /**
     * @authors Harjit Singh
     * Method to exit the quiz.
     */

    private void close(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(" Quit ");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to Exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL){
            // ... user chose OK
        }
        // ... user chose CANCEL or closed the dialog
        else
            System.exit(0);
    }

    /**
     * @authors Harjit Singh
     * Void method use to put Questions and answers in the HashTable
     */
    public void addQuestions(){
        forquiz.put("Eight", "How many planets are in our solar system?");
        forquiz.put("Yellow", "What is the color of a school bus?");
        forquiz.put("Three", "How many sides does a triangle have?");
        forquiz.put("Honey", "What do bees make?");
    }

    /**
     * @authors Harjit Singh
     * Void method use to put Room and players in the HashTable
     */

    public void addPlayers(){
        forPlayer.put("Room1", "Player1");
        forPlayer.put("Room1", "Player2");
        forPlayer.put("Room1", "Player3");
        forPlayer.put("Room1", "Player4");


        forPlayer.put("Room2", "Player1");
        forPlayer.put("Room2", "Player2");
        forPlayer.put("Room2", "Player3");
        forPlayer.put("Room2", "Player4");

    }

    /**
     * @authors Carlos Rodriguez and Harjit Singh
     * method get the arraylist of the all the value(players) in room
     * @return String of all the players in room
     */


    public String playerInfo(String a){
        String str;
        StringBuffer sb = new StringBuffer();

        for (String s : forPlayer.getALL(a)) {
            sb.append(s);
            sb.append(" ");
        }
        return str = sb.toString();
    }


}
