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
        IngameObject toobj = to.getObject();
        IngameObject fromobj = from.getObject();

        Point2D.Double newpos = to.getOldPosition();
        if (to.getCollisionSide() == CollidedObject.SIDE_TOP) {

            newpos.y = fromobj.getPosition().y - toobj.getSize().height - 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.getOldSpeed().flipVertical());
        } else if (to.getCollisionSide() == CollidedObject.SIDE_BOTTOM) {

            newpos.y = fromobj.getPosition().y + fromobj.getSize().height + 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.getOldSpeed().flipVertical());
        } else if (to.getCollisionSide() == CollidedObject.SIDE_RIGHT) {

            newpos.x = fromobj.getPosition().x + fromobj.getSize().width + 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.getOldSpeed().flipHorizontal());
        } else if (to.getCollisionSide() == CollidedObject.SIDE_LEFT) {

            newpos.x = fromobj.getPosition().x - toobj.getSize().width - 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.getOldSpeed().flipHorizontal());
        }
    }
}
