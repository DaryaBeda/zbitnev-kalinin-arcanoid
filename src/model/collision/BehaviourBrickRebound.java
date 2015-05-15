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
    public void invoke(CollidedObject passiveObject, CollidedObject activeObject) {

        // Вектор скорости отражается по-разному в зависимости от геометрической формы
        // активного объекта и пассивного объекта
        IngameObject passiveIngameObject = activeObject.getObject();
        IngameObject activeIngameObject = passiveObject.getObject();

        Point2D.Double newpos = activeObject.getOldPosition();
        if (activeObject.getCollisionSide() == CollidedObject.SIDE_TOP) {

            newpos.y = activeIngameObject.getPosition().y - passiveIngameObject.getDimension().height - 1;
            passiveIngameObject.setPosition(newpos);
            passiveIngameObject.setSpeed(activeObject.getOldSpeed().flipVertical());
        } else if (activeObject.getCollisionSide() == CollidedObject.SIDE_BOTTOM) {

            newpos.y = activeIngameObject.getPosition().y + activeIngameObject.getDimension().height + 1;
            passiveIngameObject.setPosition(newpos);
            passiveIngameObject.setSpeed(activeObject.getOldSpeed().flipVertical());
        } else if (activeObject.getCollisionSide() == CollidedObject.SIDE_RIGHT) {

            newpos.x = activeIngameObject.getPosition().x + activeIngameObject.getDimension().width + 1;
            passiveIngameObject.setPosition(newpos);
            passiveIngameObject.setSpeed(activeObject.getOldSpeed().flipHorizontal());
        } else if (activeObject.getCollisionSide() == CollidedObject.SIDE_LEFT) {

            newpos.x = activeIngameObject.getPosition().x - passiveIngameObject.getDimension().width - 1;
            passiveIngameObject.setPosition(newpos);
            passiveIngameObject.setSpeed(activeObject.getOldSpeed().flipHorizontal());
        }
    }
}
