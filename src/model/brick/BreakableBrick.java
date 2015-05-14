package model.brick;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import model.GameField;
import static model.GameModel.TYPE_OBJECT.BREAKABKE_BRICK;
import model.Speed2D;
import model.ball.BasicBall;
import model.collision.BehaviourDestroy;
import model.interaction.CreateViewObjectListener;

/**
 * Модель разрушаемого кирпича.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class BreakableBrick extends Brick {

    public BreakableBrick(GameField field) {

        this(field, new Point2D.Double(0, 0), new Dimension(0, 0));
    }

    public BreakableBrick(GameField field, Point2D.Double position, Dimension dimension, Speed2D speed) {

        super(field, position, dimension, speed);
        _behaviours.put(BasicBall.class, new BehaviourDestroy());
    }

    public BreakableBrick(GameField field, Point2D.Double position, Dimension dimension) {

        this(field, position, dimension, new Speed2D(0, 0));
    }

    public void createView() {
        for (CreateViewObjectListener l : _createViewObjectListeners) {
            l.createViewObject(_sprite, BREAKABKE_BRICK);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        BreakableBrick clone = (BreakableBrick) super.clone();
        return clone;
    }
}
