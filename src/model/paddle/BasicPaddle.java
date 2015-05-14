package model.paddle;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import model.GameField;
import static model.GameModel.TYPE_OBJECT.BASIC_PADDLE;
import model.interaction.CreateViewObjectListener;

/**
 * Модель обычной ракетки.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class BasicPaddle extends Paddle {

    public BasicPaddle(GameField field, Point2D.Double position, Dimension dimension) {

        super(field, position, dimension);
    }

    public BasicPaddle(GameField field) {

        super(field);
    }

    public void createView() {
        for (CreateViewObjectListener l : _createViewObjectListeners) {
            l.createViewObject(_sprite, BASIC_PADDLE);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        BasicPaddle clone = (BasicPaddle) super.clone();
        return clone;
    }

}
