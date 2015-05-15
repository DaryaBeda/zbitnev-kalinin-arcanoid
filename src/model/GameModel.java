package model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import model.ball.BasicBall;
import model.brick.BreakableBrick;
import model.brick.UnbreakableBrick;
import model.collision.CollidedObject;
import model.interaction.CollisionListener;
import model.paddle.BasicPaddle;
import view.GameFieldView;

/**
 * Модель игры.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class GameModel implements CollisionListener{

    public enum TYPE_OBJECT {

        BASIC_BALL, BREAKABKE_BRICK, UNBREAKABLE_BRICK, BASIC_PADDLE
    };

    protected GameField _field = null;
    protected GameFieldView _fieldView = null;
    private BasicPaddle _paddle;

    /**
     * Назначить игровое поле
     *
     * @param field
     */
    public void setField(GameField field) {

        if (field == null) {
            throw new NullPointerException();
        }
        _field = field;
    }

    public void setFieldView(GameFieldView field) {

        if (field == null) {
            throw new NullPointerException();
        }
        _fieldView = field;
    }

    /**
     * Получить игровое поле
     *
     * @return Текущее поле
     */
    public GameField getField() {

        return _field;
    }

    public void startGame() {
        BasicBall newball = new BasicBall(_field, new Point2D.Double(46, 50), 8, new Speed2D(0, 0.03));
        createObject(newball);
        
        BasicBall ball = new BasicBall(_field, new Point2D.Double(20, 20), 8, new Speed2D(-0.03, -0.03));
        createObject(ball);
        
        _paddle = new BasicPaddle(_field, new Point2D.Double(0, 584), new Dimension(96, 16));
        createObject(_paddle);
        
        BreakableBrick newbrick = new BreakableBrick(_field, new Point2D.Double(2, 120), new Dimension(48, 24));
        createObject(newbrick);
        
        BreakableBrick newbrick2 = new BreakableBrick(_field, new Point2D.Double(50, 120), new Dimension(48, 24));
        createObject(newbrick2);
        
        UnbreakableBrick newbrick3 = new UnbreakableBrick(_field, new Point2D.Double(220, 120), new Dimension(48, 24));
        createObject(newbrick3);
    }

    private void createObject(IngameObject obj) {
        obj.addCreateViewObjectListener(_fieldView);
        obj.addDeleteViewObjectListener(_fieldView);
        obj.createView();
        _field.addObject(obj);
    }

    public BasicPaddle getPaddle() {
        return _paddle;
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
