package model.ball;

import java.awt.geom.Point2D;

import model.GameField;
import static model.GameModel.TYPE_OBJECT.BASIC_BALL;
import model.Speed2D;
import model.collision.BehaviourPaddleRebound;
import model.interaction.CreateViewObjectListener;
import model.interaction.DeleteViewObjectListener;
import model.paddle.BasicPaddle;
import model.swarm.CanBeInSwarm;

/**
 * Модель обычного шарика
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class BasicBall extends Ball implements CanBeInSwarm {

    public BasicBall(GameField field) {

        super(field);
        _behaviours.put(BasicPaddle.class, new BehaviourPaddleRebound());

    }

    public BasicBall(GameField field, Point2D.Double pos, int radius) {

        super(field, pos, radius);
        _behaviours.put(BasicPaddle.class, new BehaviourPaddleRebound());
    }

    public BasicBall(GameField field, Point2D.Double pos, int radius, Speed2D speed) {

        super(field, pos, radius, speed);
        _behaviours.put(BasicPaddle.class, new BehaviourPaddleRebound());
    }

    public void createView() {
        for (CreateViewObjectListener l : _createViewObjectListeners) {
            l.createViewObject(_sprite, BASIC_BALL);
        }
    }

    @Override
    public float getDefaultSpeedScalar() {

        return (float) 0.2;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        BasicBall clone = (BasicBall) super.clone();
        return clone;
    }
}
