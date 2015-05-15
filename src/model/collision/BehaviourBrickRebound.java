package model.collision;

import java.awt.geom.Point2D;
import model.IngameObject;
import model.Speed2D;
import model.ball.Ball;
import model.brick.Brick;
import model.paddle.Paddle;

/**
 * Поведение упрогого отскока при столкновении.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class BehaviourBrickRebound extends CollisionBehaviour {

    /**
     * Экзмепляр синглтона.
     */
    private static BehaviourBrickRebound _instance = null;

    /**
     * Возвращает экземпляр поведения упрогого отскока.
     *
     * @return
     */
    public static BehaviourBrickRebound getInstance() {

        if (_instance == null) {
            _instance = new BehaviourBrickRebound();
        }

        return _instance;
    }

    @Override
    public void invoke(CollidedObject from, CollidedObject to) {

        // Вектор скорости отражается по-разному в зависимости от геометрической формы
        // активного объекта и пассивного объекта
        IngameObject toobj = to.object();
        IngameObject fromobj = from.object();

        Point2D.Double newpos = to.oldPosition();
        if (to.collisionSide() == CollidedObject.SIDE_TOP) {

            newpos.y = fromobj.getPosition().y - toobj.getSize().height - 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.oldSpeed().flipVertical());
        } else if (to.collisionSide() == CollidedObject.SIDE_BOTTOM) {

            newpos.y = fromobj.getPosition().y + fromobj.getSize().height + 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.oldSpeed().flipVertical());
        } else if (to.collisionSide() == CollidedObject.SIDE_RIGHT) {

            newpos.x = fromobj.getPosition().x + fromobj.getSize().width + 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.oldSpeed().flipHorizontal());
        } else if (to.collisionSide() == CollidedObject.SIDE_LEFT) {

            newpos.x = fromobj.getPosition().x - toobj.getSize().width - 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.oldSpeed().flipHorizontal());
        }
    }
}
