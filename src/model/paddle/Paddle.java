package model.paddle;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import model.GameField;
import model.IngameObject;
import model.Speed2D;
import model.ball.Ball;

/**
 * Модель абстрактной ракетки.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public abstract class Paddle extends IngameObject {

    protected ArrayList<Ball> _balls = new ArrayList<>();

    public Paddle(GameField field, Point2D.Double position, Dimension dimension) {

        super(field, position, dimension, new Speed2D (0, 0));
    }

    /**
     * Поместить шар на ракетку.
     *
     * @param ball Шар.
     */
    public void addBall(Ball ball) {

        if (ball == null) {
            throw new NullPointerException();
        }

        ball.setSpeed(new Speed2D(0, 0));
        _balls.add(ball);
        this.fixBallsPosition();
    }

    /**
     * Корректирует координаты мячей. Они не должны висеть над ракеткой. Они не
     * должны по горизонтали вылазить за ракетку.
     */
    protected void fixBallsPosition() {

        for (Ball ball : _balls) {
            ball.setPosition(new Point2D.Double(ball.getPosition().x, getPosition().y - ball.getSize().height));

            if (ball.getPosition().x < this.getPosition().x) {
                ball.setPosition(new Point2D.Double(this.getPosition().x, ball.getPosition().y));
            }
            if (ball.getPosition().x > this.getPosition().x + this._size.width) {
                ball.setPosition(new Point2D.Double(this.getPosition().x + this._size.width - ball.getSize().width, ball.getPosition().y));
            }
        }
    }

    /**
     * Убрать шар с ракетки.
     *
     * @param ball Шар.
     */
    public void removeBall(Ball ball) {
        _balls.remove(ball);
    }

    /**
     * Возвращает список шаров на ракетке.
     *
     * @return Список шаров на ракетке.
     */
    public ArrayList<Ball> getBalls() {

        return (ArrayList<Ball>) _balls.clone();
    }

    

    /**
     * Запускает шары с ракетки.
     */
    public void fireBalls() {

        while (!_balls.isEmpty()) {
            Ball b = _balls.get(0);
            b.setSpeed(new Speed2D(0, -0.03));
            _balls.remove(b);
        }
    }

    @Override
    public void setPosition(Point2D.Double position) {

        if (getPosition() == null) {
            super.setPosition(position);

        } else {
            double dx = position.x - getPosition().x;
            double dy = position.y - getPosition().y;

            super.setPosition(position);
            if (_balls != null) {
                for (Ball ball : _balls) {
                    ball.setPosition(new Point2D.Double(ball.getPosition().x + dx, ball.getPosition().y + dy));
                }
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Paddle clone = (Paddle) super.clone();
        return clone;
    }
}
