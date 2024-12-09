package SPane;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HowToPlayPane extends Pane{
	private int stepIndex = 0; 
    private String[] steps = {
        "ขั้นตอนที่ 1: กดปุ่มเริ่มเกมเพื่อเริ่มต้น",
        "ขั้นตอนที่ 2: ใช้ปุ่มลูกศรเพื่อเคลื่อนที่",
        "ขั้นตอนที่ 3: เก็บคะแนนให้ได้มากที่สุด!",
    };
	public HowToPlayPane() {
		// สร้าง Text สำหรับแสดงคำอธิบาย
        Text instructionText = new Text(steps[stepIndex]);
        instructionText.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial';");

        // สร้าง ImageView สำหรับแสดงรูปภาพ
        ImageView imageView = new ImageView(new Image("file:step1.png")); // ใส่ไฟล์รูปภาพที่เหมาะสม
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        // สร้างปุ่ม Next
        Button nextButton = new Button("ถัดไป");
        nextButton.setStyle("-fx-font-size: 16px; -fx-background-color: #0078D7; -fx-text-fill: white;");
        nextButton.setOnAction(e -> {
            stepIndex++;
            if (stepIndex < steps.length) {
                instructionText.setText(steps[stepIndex]);
                imageView.setImage(new Image("file:step" + (stepIndex + 1) + ".png")); // อัพเดตรูปภาพ
            } else {
                instructionText.setText("คุณได้เรียนรู้ทุกขั้นตอนแล้ว!");
                nextButton.setDisable(true);
            }
        });

        // จัด Layout
        VBox vbox = new VBox(10, imageView, instructionText, nextButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: #F0F0F0;");
        /*
        // สร้าง Scene และแสดง Stage
        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.setTitle("แนะนำวิธีการเล่น");
        stage.show();
        */
	}
}
