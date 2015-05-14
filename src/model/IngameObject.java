package model;

import com.golden.gamedev.object.Sprite;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import model.collision.CollidedObject;
import model.collision.CollisionBehaviour;
import model.interaction.CreateViewObjectListener;
import model.interaction.DeleteViewObjectListener;

/**
 * Класс игрового объекта.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public abstract class IngameObject implements Cloneable {

    protected PublishingSprite _sprite = new PublishingSprite();
    protected Dimension _size;
    protected HashMap<Class<?>, CollisionBehaviour> _behaviours = new HashMap<>();
    protected GameField _field = null;
    protected ArrayList<DeleteViewObjectListener> _deleteViewObjectListeners = new ArrayList<>();
    protected ArrayList<CreateViewObjectListener> _createViewObjectListeners = new ArrayList<>();
    protected Point2D.Double _position;
    protected Speed2D _speed;

    /**
     * Создает игровой объект, координаты (0, 0), нулевая скорость, нулевой
     * размер.
     *
     * @param field Игровое поле.
     */
    public IngameObject(GameField field) {

        this(field, new Point2D.Double(0, 0), new Dimension(0, 0));
    }

    /**
     * Создает игровой объект с нулевой скоростью.
     *
     * @param field Игровое поле.
     * @param pos Позиция объекта.
     * @param dim Размеры объекта.
     */
    public IngameObject(GameField field, Point2D.Double pos, Dimension dim) {

        this(field, pos, dim, new Speed2D(0, 0));
    }

    public boolean isMySprite(Sprite sprite) {

        return _sprite.getSprite() == sprite;
    }

    /**
     * Создает игровой объект.
     *
     * @param field Игровое поле.
     * @param pos Позиция объекта.
     * @param dim Размеры объекта.
     * @param speed Скорость объекта.
     */
    public IngameObject(GameField field, Point2D.Double pos, Dimension dim, Speed2D speed) {

        if (field == null || pos == null || dim == null || speed == null) {
            throw new NullPointerException();
        }

        this._field = field;
        this.setSize(dim);
        this.setPosition(pos);
        this.setSpeed(speed);
    }

    public void addCreateViewObjectListener(CreateViewObjectListener l) {
        _createViewObjectListeners.add(l);
    }

    public void removeCreateViewObjectListener(CreateViewObjectListener l) {
        _createViewObjectListeners.remove(l);
    }

    /**
     * Возвращает поле, на котором находится объект.
     *
     * @return Игровое поле.
     */
    public GameField getField() {

        return this._field;
    }
    public Point2D.Double getOldPosition() {
        return _position;
    }
    
    public Speed2D getOldSpeed(){
        return _speed;
    }
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
        this._speed = speed;
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
        this._position = position;
    }

    /**
     * Задает размер объекта в пикселях.
     *
     * @param dimension
     */
    public void setSize(Dimension dimension) {

        if (dimension == null) {
            throw new NullPointerException();
        }
        _size = dimension;
    }

    /**
     * Возвращает размер объекта в пикселях.
     *
     * @return
     */
    public Dimension getSize() {

        return (Dimension) _size.clone();
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
     * @param curr Текущий объект
     * @param other Объект, столкнувшийся с данным.
     */
    public void processCollision(CollidedObject curr, CollidedObject other) {

        CollisionBehaviour cb = _behaviours.get(other.object().getClass());
        if (cb != null) {
            cb.invoke(other, curr);
        }
    }

    /**
     * Уничтожает объект. По умолчанию ничего не освобождает.
     */
    public void destroy() {

        this._field.removeObject(this);
        for (DeleteViewObjectListener l : _deleteViewObjectListeners) {
            l.deleteViewObject(_sprite);
        }
    }


    /**
     * Добавить слушателя событий жизни объекта.
     *
     * @param l Добавляемый слушатель.
     */
    public void addDeleteViewObjectListener(DeleteViewObjectListener l) {
        _deleteViewObjectListeners.add(l);
    }

    /**
     * Удалить слушателя событий жизни объекта.
     *
     * @param l Удаляемый слушатель.
     */
    public void removeDeleteViewObjectListener(DeleteViewObjectListener l) {
        _deleteViewObjectListeners.remove(l);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        IngameObject clone = (IngameObject) super.clone();
        clone._field = this._field;    // ссылка на поле просто копируется, да
        clone._position = (Point2D.Double) this._position.clone();
        clone._size = (Dimension) this._size.clone();
        clone._speed = (Speed2D) this._speed.clone();
        return clone;
    }

    public abstract void createView();

}
