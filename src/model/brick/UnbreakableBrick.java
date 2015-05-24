package model.brick;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import model.GameField;
import static model.GameModel.TYPE_OBJECT.UNBREAKABLE_BRICK;
import model.Speed2D;
import model.interaction.ViewObjectListener;

/**
 * Модель неразрушаемого кирпича.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class UnbreakableBrick extends Brick {

    public UnbreakableBrick(GameField field, Point2D.Double position, Dimension dimension) {

        super(field, position, dimension, new Speed2D(0, 0));
    }

    @Override
    public void createView() {

        for (ViewObjectListener l : _viewObjectListeners) {
            l.createViewObject(_sprite, UNBREAKABLE_BRICK);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        UnbreakableBrick clone = (UnbreakableBrick) super.clone();
        return clone;
    }
}
