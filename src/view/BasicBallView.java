/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.PublishingSprite;

/**
 *
 * @author Дарья
 */
public class BasicBallView extends IngameObjectView{

    public BasicBallView(PublishingSprite sprite) throws IOException {
        super(sprite);
        setImage(ImageIO.read(new File("default\\gfx\\balls\\basic.png")));
    }
}
