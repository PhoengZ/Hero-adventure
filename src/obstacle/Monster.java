package obstacle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Monster extends Pane{
	private int posX, posY, size;
	private Image img;
	private ImageView Image;
	private Boolean canshot;
	public Monster(int x, int y,int size ,String img) {
		super();
		this.setPosX(x);
		this.setPosY(y);
		this.setCanshot(true);
		try {
            String classLoaderPath = ClassLoader.getSystemResource(img).toString();
            this.img =new Image(classLoaderPath);
            setImage(new ImageView(this.img));
            this.Image.setFitHeight(size);
            this.Image.setFitWidth(size);
            this.getChildren().clear();
            this.getChildren().add(this.Image);
        } catch (Exception e) {
            e.printStackTrace();
        }
		this.setSize(size);
	}
	
	public Boolean getCanshot() {
		return canshot;
	}

	public void setCanshot(Boolean canshot) {
		this.canshot = canshot;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	
	public ImageView getImage() {
		return Image;
	}

	public void setImage(ImageView image) {
		Image = image;
	}

	public Shot shoot() {
		return new Shot(posX + size / 2 - Shot.size / 2, posY - Shot.size);
	}
	
}
