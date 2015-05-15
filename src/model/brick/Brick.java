package model.brick;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import model.GameField;
import model.IngameObject;
import model.Speed2D;

/**
 * Модель абстрактного кирпича.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public abstract class Brick extends IngameObject {

    public Brick(GameField field, Point2D.Double position, Dimension dimension, Speed2D speed) {

        super(field, position, dimension, speed);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Brick clone = (Brick) super.clone();
        return clone;
    }
}
