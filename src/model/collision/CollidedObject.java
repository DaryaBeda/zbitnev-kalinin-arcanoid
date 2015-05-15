package model.collision;

import java.awt.Shape;
import java.awt.geom.Point2D;

import model.IngameObject;
import model.Speed2D;

/**
 * Столкнувшийся игровой объект. Содержит ссылку на игровой объект и
 * дополнительные сведения о коллизии (тип коллизии, предыдущая позиция)
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 */
public class CollidedObject implements Cloneable {

    private IngameObject _object = null;
    private Point2D.Double _oldPosition = null;
    private Speed2D _oldSpeed = null;
    private int _colSide = -1;
    private Shape _colShape = null; // TODO Беспредел! Заменить на независимый от представления класс! ~~~ Nikita Kalinin <nixorv@gmail.com>

    /**
     * С объектом столкнулись сверху
     */
    public static final int SIDE_TOP = 0;

    /**
     * С объектом столкнулись снизу
     */
    public static final int SIDE_BOTTOM = 1;

    /**
     * С объектом столкнулись слева
     */
    public static final int SIDE_LEFT = 2;

    /**
     * С объектом столкнулись справа
     */
    public static final int SIDE_RIGHT = 3;

    /**
     * Создать информацию о столкнувшемся игровом объекте
     *
     * @param object Игровой объект
     * @param oldpos Позиция объекта за кадр до столкновения
     * @param side Сторона объекта, которой он столкнулся
     * @param shape Форма объекта
     */
    public CollidedObject(IngameObject object, Point2D.Double oldpos, Speed2D oldspeed, int side, Shape shape) {

        if (object == null || oldpos == null || shape == null) {
            throw new NullPointerException();
        }

        this._object = object;
        this._oldPosition = oldpos;
        this._oldSpeed = oldspeed;
        this._colSide = side;
        this._colShape = shape;
    }

    public IngameObject getObject() {
        return _object;
    }

    public Point2D.Double getOldPosition() {
        return _oldPosition;
    }
    
    public Speed2D getOldSpeed() {
        return _oldSpeed;
    }

    public int getCollisionSide() {
        return _colSide;
    }

    public Shape getCollisionShape() {
        return _colShape;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        CollidedObject clone = (CollidedObject) super.clone();
        clone._object = (IngameObject) this._object.clone();
        clone._colSide = this._colSide;
        clone._colShape = this._colShape;
        clone._oldPosition = (Point2D.Double) this._oldPosition.clone();
        clone._oldSpeed = (Speed2D) this._oldSpeed.clone();

        return clone;
    }

    /**
     * Метод проверки равенства двух объектов
     * @param obj объект для сравнения
     * @return признак равенства
     */
    @Override
    public boolean equals(Object obj) {

        return this._object.equals(((CollidedObject) (obj))._object);
    }

    @Override
    public int hashCode() {

        return this._object.hashCode();
    }
}
