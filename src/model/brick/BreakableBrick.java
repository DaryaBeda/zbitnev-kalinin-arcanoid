package model.brick;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import model.GameField;
import static model.GameModel.TYPE_OBJECT.BREAKABKE_BRICK;
import model.Speed2D;
import model.collision.BehaviourDestroy;
import model.interaction.CreateViewObjectListener;
import model.interaction.DeleteViewObjectListener;

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

    public BreakableBrick(GameField field, Point2D.Double pos, Dimension dim, Speed2D speed) {

        super(field, pos, dim, speed);
    }

    public BreakableBrick(GameField field, Point2D.Double pos, Dimension dim) {

        this(field, pos, dim, new Speed2D(0, 0));
    }
    
    public void createView () {
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
