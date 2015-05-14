package model.brick;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import model.GameField;
import static model.GameModel.TYPE_OBJECT.UNBREAKABLE_BRICK;
import model.Speed2D;
import model.interaction.CreateViewObjectListener;

/**
 * Модель неразрушаемого кирпича.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class UnbreakableBrick extends Brick {

    public UnbreakableBrick(GameField field, Point2D.Double position, Dimension dimension, Speed2D speed) {

        super(field, position, dimension, speed);
    }

    public UnbreakableBrick(GameField field, Point2D.Double position, Dimension dimension) {

        this(field, position, dimension, new Speed2D(0, 0));
    }

    public UnbreakableBrick(GameField field) {

        this(field, new Point2D.Double(0, 0), new Dimension(0, 0));
    }

    public void createView() {
        
        for (CreateViewObjectListener l : _createViewObjectListeners) {
            l.createViewObject(_sprite, UNBREAKABLE_BRICK);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        UnbreakableBrick clone = (UnbreakableBrick) super.clone();
        return clone;
    }
}
