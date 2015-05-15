package model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import model.ball.BasicBall;
import model.brick.BreakableBrick;
import model.brick.UnbreakableBrick;
import model.paddle.BasicPaddle;
import view.GameFieldView;

/**
 * Модель игры.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class GameModel {

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
}
