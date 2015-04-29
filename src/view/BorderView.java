/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.GameModel.TYPE_OBJECT;
import static model.GameModel.TYPE_OBJECT.HORIZONTAL_BORDER;
import model.PublishingSprite;

/**
 *
 * @author пользователь
 */
public class BorderView extends IngameObjectView {

    public BorderView(PublishingSprite sprite, TYPE_OBJECT type) throws IOException {
        super(sprite);
        switch (type) {
            case HORIZONTAL_BORDER:
                setImage(ImageIO.read(new File("default\\gfx\\borders\\hboard.png")));
                //setImage(ImageIO.read(new File("default\\gfx\\bricks\\breakable.png")));
                break;
            case VERTICAL_BORDER:
                setImage(ImageIO.read(new File("default\\gfx\\borders\\v_board.png")));
                break;
            case BOTTOM_BORDER:
                setImage(ImageIO.read(new File("default\\gfx\\borders\\b_board.png")));
                break;
        }
    }
}
