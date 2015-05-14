package model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import model.ball.Ball;
import model.ball.BallPositionChangedListener;
import model.collision.CollidedObject;
import model.interaction.CollisionListener;

/**
 * Модель игрового поля.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class GameField implements BallPositionChangedListener, CollisionListener {

    private ArrayList<IngameObject> _objects;
    private Dimension _dimension;

    /**
     * Инициализирует поле заданного размера.
     *
     * @param dimension размер поля
     */
    public GameField(Dimension dimension) {

        _objects = new ArrayList<>();
        _dimension = dimension;
    }

    /**
     * Добавить объект на поле
     *
     * @param object Объект для добавления
     */
    public void addObject(IngameObject object) {

        _objects.add(object);
    }

    /**
     * Убрать объект с поля
     *
     * @param object Объект для удаления
     */
    public void removeObject(IngameObject object) {

        _objects.remove(object);
    }

    /**
     * Получить размеры игрового поля (в пикселях).
     *
     * @return Размеры поля.
     */
    public Dimension getSize() {

        return _dimension;
    }

    public ArrayList<IngameObject> getObjects() {

        return (ArrayList<IngameObject>) _objects.clone();
    }

    /**
     * Реализация этого метода отражает мяч от границ поля.
     */
    @Override
    public void ballPositionChanged(Ball ball) {

        if (ball.getPosition().y < 0) {
            
            ball.setPosition(new Point2D.Double(ball.getPosition().x, 0));
            ball.setSpeed(ball.getSpeed().flipVertical());
        }

        if (ball.getPosition().x < 0 || ball.getPosition().x + ball.getSize().width > _dimension.width) {
            
            if (ball.getPosition().x < 0) {
                ball.setPosition(new Point2D.Double(0, ball.getPosition().y));
            } else {
                ball.setPosition(new Point2D.Double(_dimension.width - ball.getSize().width, ball.getPosition().y));
            }
            ball.setSpeed(ball.getSpeed().flipHorizontal());
        }
    }

    /**
     * Обработать столкновения
     *
     * @param storage Словарь столкновений, где ключ - столкнувшийся объект,
     * значение - список объектов, с которыми он столкнулся
     */
    @Override
    public void collisionOccured(HashMap<CollidedObject, ArrayList<CollidedObject>> storage) {

        // Вместо объектов, от которых принимается эффект (активные)
        // передаётся их копия до начала обработки вообще всех столкновений
        HashMap<CollidedObject, ArrayList<CollidedObject>> storage_copy = deepCopyStorage(storage);

        Iterator<CollidedObject> i, copyi, j, copyj;
        i = storage.keySet().iterator();
        copyi = storage_copy.keySet().iterator();

        while (i.hasNext() && copyi.hasNext()) {

            CollidedObject obj1 = i.next();
            obj1.object().setPosition(obj1.object().getPosition());
            CollidedObject obj1copy = copyi.next();
            obj1copy.object().setPosition(obj1copy.object().getPosition());
            j = storage.get(obj1).iterator();
            copyj = storage_copy.get(obj1copy).iterator();

            while (j.hasNext() && copyj.hasNext()) {

                CollidedObject obj2 = j.next();
                obj2.object().setPosition(obj2.object().getPosition());
                CollidedObject obj2copy = copyj.next();
                obj2copy.object().setPosition(obj2copy.object().getPosition());
                obj1.object().processCollision(obj1, obj2copy);
                obj2.object().processCollision(obj2, obj1copy);
            }
        }
    }

    /**
     * Порождает копию словаря коллизии вместе со всеми хранимыми объектами
     *
     * @param storage Словарь коллизии
     * @return Копия словаря коллизии
     */
    private HashMap<CollidedObject, ArrayList<CollidedObject>> deepCopyStorage(
            HashMap<CollidedObject, ArrayList<CollidedObject>> storage) {

        HashMap<CollidedObject, ArrayList<CollidedObject>> deepcopy = new HashMap<>();

        try {

            for (CollidedObject key : storage.keySet()) {

                CollidedObject key_copy = (CollidedObject) key.clone();
                ArrayList<CollidedObject> values_copy = new ArrayList<>();
                
                for (CollidedObject obj : storage.get(key)) {
                    values_copy.add((CollidedObject) obj.clone());
                }

                deepcopy.put(key_copy, values_copy);
            }
        } catch (CloneNotSupportedException exc) {
        }

        return deepcopy;
    }
}
