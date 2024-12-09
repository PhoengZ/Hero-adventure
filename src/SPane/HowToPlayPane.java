package SPane;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HowToPlayPane extends Pane{
	private int stepIndex = 0; 
	private static String FontString;
    private String[] steps = {
        "Step 1 : Choose player chractor to play.",
        "Step 2 :Move continuously through the map by using arrow keys (<-, ->) to move left and right, and press the spacebar to jump. Collect coins along the way to enhance your character's power after completing the stage.",
        "Step 3 : At the end of the stage, you can use the coins you collected to upgrade your character's attack power, armor, or health.",
        "Step 4 : Enter the turn-based screen. When it is the player's turn, click on the enemy you want to attack to perform an attack.",
        "Step 5 : Alternatively, you can choose to increase your armor or attack power for the next turn (if you choose either of these options, you will not attack during that turn)"
    };
	public HowToPlayPane() {
		FontString = "";
		 try {
	        String classLoaderPath = ClassLoader.getSystemResource("Pixeboy.ttf").toString();
	        FontString = classLoaderPath;
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not fount: "+ "Pixeboy.ttf");
	    }   
		// Background setup
	        Image Background = null;
	        try {
	            String classLoaderPath = ClassLoader.getSystemResource("Background_Mainmenu.jpg").toString();
	            Background = new Image(classLoaderPath);
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Not found turnbase Background");
	        }
	        ImageView backgroundView = new ImageView(Background);
	        backgroundView.setFitHeight(720);
	        backgroundView.setFitWidth(1280);
	        backgroundView.setOpacity(0.8);
	        this.getChildren().add(backgroundView);
	        
		Text howtoplaytext = new Text ("How To Play");
		howtoplaytext.setFont(Font.loadFont(FontString,80));
		howtoplaytext.setFill(Color.BLACK);
		howtoplaytext.setLayoutX(460);
		howtoplaytext.setLayoutY(100);
		this.getChildren().add(howtoplaytext);
        Text instructionText = new Text(steps[stepIndex]);
        //instructionText.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial';");
        instructionText.setFont(Font.loadFont(FontString,26));
        ImageView imageView = new ImageView(new Image("howtoplay/howtoplay1.JPG")); 
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        
        Button nextButton = new Button("ถัดไป");
        nextButton.setStyle("-fx-font-size: 16px; -fx-background-color: #0078D7; -fx-text-fill: white;");
        nextButton.setOnAction(e -> {
            stepIndex++;
            if (stepIndex < steps.length) {
                instructionText.setText(steps[stepIndex]);
                imageView.setImage(new Image("howtoplay/howtoplay" + (stepIndex + 1) + ".JPG"));
            } else {
            	nextButton.setDisable(true);
                instructionText.setText("Done Let's Play!!");
            }
        });
        VBox vbox = new VBox(20, imageView, instructionText, nextButton);
        vbox.setPrefWidth(800); 
        vbox.setPrefHeight(570);
        vbox.setLayoutX((1280 - 800) / 2);
        vbox.setLayoutY(140);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle(
        	    "-fx-padding: 20px; " +
        	    "-fx-background-image: url('howtoplay/howtoplaybackground.png'); " +
        	    "-fx-background-size: cover; " +
        	    "-fx-background-position: center; " +
        	    "-fx-background-repeat: no-repeat;"+
        	    "-fx-background-size: stretch;"
        	);

        instructionText.setWrappingWidth(700);
        instructionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);


        imageView.setFitWidth(580); 
        imageView.setPreserveRatio(true);

        this.getChildren().add(vbox);
	}
}
