package model.collision;

/**
 * Поведение столкнувшегося объекта; синглтон.
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public abstract class CollisionBehaviour {

    /**
     * Осуществить поведение пассивного объекта в ответ на столкновение.
     *
     * @param passiveObject Пассивный объект (не изменяется).
     * @param activeObject Активный объект (изменяет состояние в ответ на столкновение).
     */
    public abstract void invoke(CollidedObject passiveObject, CollidedObject activeObject);
}
