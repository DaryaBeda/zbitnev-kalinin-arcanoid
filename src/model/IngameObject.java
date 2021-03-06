package model;

import com.golden.gamedev.object.Sprite;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import model.collision.CollidedObject;
import model.collision.CollisionBehaviour;
import model.interaction.ViewObjectListener;

/**
 * Класс игрового объекта.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public abstract class IngameObject implements Cloneable {

    protected PublishingSprite _sprite = new PublishingSprite();
    protected Dimension _dimension;
    protected HashMap<Class<?>, CollisionBehaviour> _behaviours = new HashMap<>();
    protected GameField _field = null;
    protected ArrayList<ViewObjectListener> _viewObjectListeners = new ArrayList<>();

    public boolean isMySprite(PublishingSprite sprite) {

        return _sprite.getSprite() == sprite.getSprite();
    }

    /**
     * Создает игровой объект.
     *
     * @param field Игровое поле.
     * @param position Позиция объекта.
     * @param dimension Размеры объекта.
     * @param speed Скорость объекта.
     */
    public IngameObject(GameField field, Point2D.Double position, Dimension dimension, Speed2D speed) {

        if (field == null || position == null || dimension == null || speed == null) {
            throw new NullPointerException();
        }

        this._field = field;
        this.setDimension(dimension);
        this.setPosition(position);
        this.setSpeed(speed);
    }

    public void addViewObjectListener(ViewObjectListener l) {
        _viewObjectListeners.add(l);
    }

    public void removeViewObjectListener(ViewObjectListener l) {
        _viewObjectListeners.remove(l);
    }

    public abstract void createView();

    /**
     * Получить скорость.
     *
     * @return Текущая скорость.
     */
    public Speed2D getSpeed() {

        return (Speed2D) _sprite.getSpeed().clone();
    }

    /**
     * Установить скорость.
     *
     * @param speed Новая скорость.
     */
    public void setSpeed(Speed2D speed) {

        this._sprite.setSpeed(speed);
    }

    /**
     * Получить позицию.
     *
     * @return Текущая позиция.
     */
    public Point2D.Double getPosition() {

        return (Point2D.Double) _sprite.getPosition().clone();
    }

    /**
     * Задать позицию.
     *
     * @param position Новая позиция.
     */
    public void setPosition(Point2D.Double position) {

        if (position == null) {
            throw new NullPointerException();
        }
        this._sprite.setPosition(position);
    }

    /**
     * Задает размер объекта в пикселях.
     *
     * @param dimension
     */
    public void setDimension(Dimension dimension) {

        if (dimension == null) {
            throw new NullPointerException();
        }
        _dimension = dimension;
    }

    /**
     * Возвращает размер объекта в пикселях.
     *
     * @return
     */
    public Dimension getDimension() {

        return (Dimension) _dimension.clone();
    }

    /**
     * Сдвинуть объект.
     *
     * @param delta Величина изменения позиции
     */
    public void move(Point2D.Double delta) {

        this.setPosition(new Point2D.Double(this.getPosition().x + delta.x, this.getPosition().y + delta.y));
    }

    /**
     * Обрабатывает столкновение с другим объектом.
     *
     * @param current Текущий объект
     * @param other Объект, столкнувшийся с данным.
     */
    public void processCollision(CollidedObject current, CollidedObject other) {

        CollisionBehaviour collisionBehaviour = _behaviours.get(other.getObject().getClass());
        if (collisionBehaviour != null) {
            collisionBehaviour.invoke(other, current);
        }
    }

    /**
     * Уничтожает объект. По умолчанию ничего не освобождает.
     */
    public void destroy() {

        this._field.removeObject(this);
        for (ViewObjectListener l : _viewObjectListeners) {
            l.deleteViewObject(_sprite);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        IngameObject clone = (IngameObject) super.clone();
        clone._field = this._field;
        clone._dimension = (Dimension) this._dimension.clone();
        return clone;
    }
}
