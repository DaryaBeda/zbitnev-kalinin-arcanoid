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
     * @param from Активный объект (не изменяется).
     * @param to Пассивный объект (изменяет состояние в ответ на столкновение).
     */
    public abstract void invoke(CollidedObject from, CollidedObject to);
}
