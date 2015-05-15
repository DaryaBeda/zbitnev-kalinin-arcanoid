package model.collision;

/**
 * Поведение разрушения при столкновении.
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class BehaviourDestroy extends CollisionBehaviour {

    @Override
    public void invoke(CollidedObject from, CollidedObject to) {

        to.getObject().destroy();
    }
}
