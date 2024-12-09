package SPane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import player.Player;
import utils.GameStart;

public class UpgadeStatPane extends Pane{
	private Font Price;
	public UpgadeStatPane(Player player) {
		String pt= "";
		Media bg = null;
		try {
            String classLoaderPath = ClassLoader.getSystemResource("UpgradePane.mp3").toString();
            bg = new Media(classLoaderPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
		MediaPlayer media = new MediaPlayer(bg);
		media.setCycleCount(MediaPlayer.INDEFINITE);
		media.setVolume(0.1);
		media.play();
		try {
            String classLoaderPath = ClassLoader.getSystemResource("Pixeboy.ttf").toString();
            pt = classLoaderPath;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not fount: "+ "PixelGame.otf");
        }
		this.Price = Font.loadFont(pt, 24);
		int price[] = {15,10,20};
		ImageView Background = new ImageView(SetImage("Buy_item.png"));
		Background.setFitHeight(600);
		Background.setFitWidth(800);
		Background.setTranslateX(240);
		Background.setTranslateY(60);
		this.getChildren().add(Background);
		ImageView BuffAttack = Button(390,210,100,100,price[0],"Buy_Attack.png","cantBuy_Attack.png","15attackred.png","15attackgreen.png");
		ImageView BuffDef = Button(590,210,100,100,price[1],"Buy_Def.png","cantBuy_Def.png","10defensereed.png","10defensegreen.png");
		ImageView ResetHp = Button(790,210,100,100,price[2],"Buy_Hp.png","cantBuy_Hp.png","20_HPred.png","20_HPgreen.png");
		ImageView Continue = new ImageView(SetImage("ContinueButton.png"));
		Continue.setFitWidth(100);
		Continue.setFitHeight(100);
		Continue.setTranslateX(590);
		Continue.setTranslateY(550);
		Text score = new Text("Coin: "+Integer.toString(GameStart.getScore()));
		score.setStroke(Color.BLUE);
		score.setStrokeDashOffset(5);
		score.setFont(Price);
		score.setFill(Color.BLACK);
		score.prefHeight(60);
		score.prefWidth(60);
		score.setTranslateX(610);
		score.setTranslateY(100);
		BuffAttack.setOnMouseClicked(e->{
			if (GameStart.getScore() >= price[0]) {
				GameStart.setScore(GameStart.getScore()-price[0]);
				player.setAtk(player.getAtk()+10);
				Update(score);
			}
		});
		BuffDef.setOnMouseClicked(e->{
			if (GameStart.getScore() >= price[1]) {
				GameStart.setScore(GameStart.getScore()-price[1]);
				player.setDefense(player.getDefense()+10);
				Update(score);
			}

		});
		ResetHp.setOnMouseClicked(e->{
			if (GameStart.getScore() >= price[2]) {
				GameStart.setScore(GameStart.getScore()-price[2]);
				player.setHp(player.getHp()+100);
				Update(score);
			}
		});
		Continue.setOnMouseClicked(e->{
			media.stop();
			GameStart.getTime().start();
			GameStart.initContent(GameStart.getRound());
		});
		Continue.setOnMouseEntered(e->{
			Continue.setFitWidth(120);
			Continue.setFitHeight(120);
			Continue.setTranslateX(580);
			Continue.setTranslateY(540);
		});
		Continue.setOnMouseExited(e->{
			Continue.setFitWidth(100);
			Continue.setFitHeight(100);
			Continue.setTranslateX(590);
			Continue.setTranslateY(550);
		});
		this.getChildren().addAll(BuffAttack,BuffDef,ResetHp,score,Continue);
	}
	private Image SetImage(String path) {
		Image img = null;
	    try {
	        String classLoaderPath = ClassLoader.getSystemResource(path).toString();
	        img = new Image(classLoaderPath);
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not found gameover Background");
	    }
	    return img;
	}
	private ImageView Button(int x,int y,int w,int h,int price,String path,String path_1,String path_2,String path_3) {
		ImageView img = new ImageView(SetImage(price > GameStart.getScore() ? path_1:path));
		ImageView sc = new ImageView(SetImage(price> GameStart.getScore() ? path_2:path_3));
		sc.setFitHeight(h+40);
		sc.setFitWidth(w+40);
		sc.setTranslateX(x-20);
		sc.setTranslateY(y+h+90);
		img.setFitHeight(h);
		img.setFitWidth(w);
		img.setTranslateX(x);
		img.setTranslateY(y);
		img.setOnMouseEntered(e->{
			if (GameStart.getScore() < price) {
				img.setImage(SetImage(path_1));
				sc.setImage(SetImage(path_2));
			}else {
				img.setImage(SetImage(path));
				sc.setImage(SetImage(path_3));
			}
			img.setFitHeight(h+20);
			img.setFitWidth(w+20);
			img.setTranslateX(x-10);
			img.setTranslateY(y-10);
			sc.setFitHeight(h+60);
			sc.setFitWidth(w+60);
			sc.setTranslateX(x-30);
			sc.setTranslateY(y+h+80);
		});
		img.setOnMouseExited(e->{
			img.setFitHeight(h);
			img.setFitWidth(w);
			img.setTranslateX(x);
			img.setTranslateY(y);
			sc.setFitHeight(h+40);
			sc.setFitWidth(w+40);
			sc.setTranslateX(x-20);
			sc.setTranslateY(y+h+90);
		});
		this.getChildren().addAll(sc);
		return img;
	}
	private void Update(Text score) {
		score.setText("Coin: "+Integer.toString(GameStart.getScore()));
	}
}
