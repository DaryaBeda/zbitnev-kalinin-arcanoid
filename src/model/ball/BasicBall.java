package model.ball;

import java.awt.geom.Point2D;

import model.GameField;
import static model.GameModel.TYPE_OBJECT.BASIC_BALL;
import model.Speed2D;
import model.brick.BreakableBrick;
import model.brick.UnbreakableBrick;
import model.collision.BehaviourBallRebound;
import model.collision.BehaviourPaddleRebound;
import model.collision.BehaviourBrickRebound;
import model.interaction.CreateViewObjectListener;
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
        addBehaviours();
    }

    public BasicBall(GameField field, Point2D.Double position, int radius) {

        super(field, position, radius);
        addBehaviours();
    }

    public BasicBall(GameField field, Point2D.Double position, int radius, Speed2D speed) {

        super(field, position, radius, speed);
        addBehaviours();
    }

    public void createView() {
        for (CreateViewObjectListener l : _createViewObjectListeners) {
            l.createViewObject(_sprite, BASIC_BALL);
        }
    }

    private void addBehaviours() {
        _behaviours.put(BasicPaddle.class, new BehaviourPaddleRebound());
        _behaviours.put(BreakableBrick.class, new BehaviourBrickRebound());
        _behaviours.put(UnbreakableBrick.class, new BehaviourBrickRebound());
        _behaviours.put(BasicBall.class, new BehaviourBallRebound());
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
