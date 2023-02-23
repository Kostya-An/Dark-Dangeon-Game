package future.code.dark.dungeon.domen;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static future.code.dark.dungeon.config.Configuration.SPRITE_SIZE;


public abstract class AnimatedObject extends GameObject {
    List<Image> images;
    public AnimatedObject(int xPosition, int yPosition, List<String> images) {
        super(xPosition, yPosition);
        List<Image> imageList = new ArrayList<>();
        for (String i :
                images) {
            imageList.add(new ImageIcon(i).getImage());
        }
        this.images = imageList;
    }
    int currentImage = 0;
    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(images.get(currentImage), xPosition * SPRITE_SIZE, yPosition  * SPRITE_SIZE, null);
        currentImage++;
        if(currentImage == images.size()){
            currentImage = 0;
        }
    }
}
