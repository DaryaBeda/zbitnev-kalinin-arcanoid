package model.collision;

import java.awt.geom.Point2D;
import model.IngameObject;

/**
 * Поведение упрогого отскока при столкновении.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class BehaviourBrickRebound extends CollisionBehaviour {

    @Override
    public void invoke(CollidedObject from, CollidedObject to) {

        // Вектор скорости отражается по-разному в зависимости от геометрической формы
        // активного объекта и пассивного объекта
        IngameObject toobj = to.getObject();
        IngameObject fromobj = from.getObject();

        Point2D.Double newpos = to.getOldPosition();
        if (to.getCollisionSide() == CollidedObject.SIDE_TOP) {

            newpos.y = fromobj.getPosition().y - toobj.getDimension().height - 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.getOldSpeed().flipVertical());
        } else if (to.getCollisionSide() == CollidedObject.SIDE_BOTTOM) {

            newpos.y = fromobj.getPosition().y + fromobj.getDimension().height + 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.getOldSpeed().flipVertical());
        } else if (to.getCollisionSide() == CollidedObject.SIDE_RIGHT) {

            newpos.x = fromobj.getPosition().x + fromobj.getDimension().width + 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.getOldSpeed().flipHorizontal());
        } else if (to.getCollisionSide() == CollidedObject.SIDE_LEFT) {

            newpos.x = fromobj.getPosition().x - toobj.getDimension().width - 1;
            toobj.setPosition(newpos);
            toobj.setSpeed(to.getOldSpeed().flipHorizontal());
        }
    }
}
