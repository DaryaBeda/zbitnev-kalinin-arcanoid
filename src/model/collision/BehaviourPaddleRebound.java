package model.collision;

import static com.golden.gamedev.object.collision.CollisionBounds.BOTTOM_COLLISION;
import static com.golden.gamedev.object.collision.CollisionBounds.TOP_COLLISION;
import java.awt.geom.Point2D;

import model.IngameObject;
import model.ball.Ball;
import model.paddle.Paddle;

/**
 * Поведение отскока от ракетки при столкновении.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class BehaviourPaddleRebound extends CollisionBehaviour {

    /**
     * Экзмепляр синглтона.
     */
    private static BehaviourPaddleRebound _instance = null;

    public BehaviourPaddleRebound() {
    }

    /**
     * Возвращает экземпляр поведения отражения от ракетки.
     *
     * @return
     */
    public static BehaviourPaddleRebound getInstance() {

        if (_instance == null) {
            _instance = new BehaviourPaddleRebound();
        }

        return _instance;
    }

    @Override
    public void invoke(CollidedObject from, CollidedObject to) {

        if (to.object() instanceof Ball) {

            to.object().setPosition(new Point2D.Double(to.object().getPosition().x,
                    from.object().getPosition().y - to.object().getSize().height));
            if (from.object() instanceof Paddle) {
                to.object().setSpeed(((Paddle) (from.object())).getFireSpeed((Ball) to.object()));
            } else {
                if(to.collisionSide() == TOP_COLLISION || to.collisionSide() == BOTTOM_COLLISION) {
                    to.object().setSpeed(to.object().getSpeed().flipHorizontal());
                } else {
                    to.object().setSpeed(to.object().getSpeed().flipVertical());
                }
            }
        }
    }
}
