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

        super(field, position, dimension);
    }

    public Paddle(GameField field) {

        super(field, new Point2D.Double(0, 0), new Dimension(0, 0));
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
     * Возвращает скорость мяча при запуске его с ракетки или отскока от
     * ракетки.
     *
     * @param ball Мяч.
     * @return Вектор скорости.
     */
    public Speed2D getFireSpeed(Ball ball) {

        // Найти два центра расчета вектора.
        Point2D.Double paddleLeftCenter = new Point2D.Double(getPosition().x + (this._size.width / 5) * 2, getPosition().y);
        Point2D.Double paddleRightCenter = new Point2D.Double(getPosition().x + (this._size.width / 5) * 3, getPosition().y);

        // Центр ракетки
        Point2D.Double paddleCenter = new Point2D.Double(getPosition().x + this._size.width / 2, getPosition().y);

        // Относительные координаты центра мяча в декартовой системе координат (точка B).
        // Считаем, что paddleCenter - это точка A(0, 0).
        Point2D.Double relBallCenter = new Point2D.Double(ball.getPosition().x + ball.getSize().width / 2 - paddleCenter.x,
                paddleCenter.y - ball.getPosition().y - ball.getSize().height / 2);

        // Если мяч между двумя центрами, направляем вектор вверх.
        if (relBallCenter.x <= this._size.width / 10 && relBallCenter.x >= -this._size.width / 10) {
            return new Speed2D(0, -ball.getDefaultSpeedScalar());
        }

        // В зависимости от трети ракетки, в которой располагается мяч, выбираем центр расчета вектора скорости.
        Point2D.Double paddleNewCenter = relBallCenter.x > this._size.width / 10 ? paddleRightCenter : paddleLeftCenter;

        // Рассчитываем относительное положение мяча от выбранного центра.
        relBallCenter = new Point2D.Double(relBallCenter.x + paddleCenter.x - paddleNewCenter.x, relBallCenter.y);

        // Коэффициенты уравнения точки пересечения прямой и окружности.
        double a = (Math.pow(relBallCenter.x, 2) + Math.pow(relBallCenter.y, 2)) / Math.pow(relBallCenter.x, 2);
        double b = 0;
        double c = -Math.pow(ball.getDefaultSpeedScalar(), 2);

        // Дискриминант.
        double D = Math.pow(b, 2) - 4 * a * c;

        // Точки пересечения.
        Point2D.Double p1 = new Point2D.Double((float) ((-b + Math.sqrt(D)) / (2 * a)), 0);
        Point2D.Double p2 = new Point2D.Double((float) ((-b - Math.sqrt(D)) / (2 * a)), 0);

        // Находим y{1,2} у точек.
        p1.y = p1.x * relBallCenter.y / relBallCenter.x;
        p2.y = p2.x * relBallCenter.y / relBallCenter.x;

        // Нужная точка пересечения имеет положительную y-координату.
        Point2D.Double p = p1.y > 0 ? p1 : p2;

        // Находим горизонтальную и вертикальную сооставляющие вектора скорости.
        // y отрицательный, чтобы перейти в экранную систему координат.
        return new Speed2D(p.x, -Math.abs(p.y));
    }

    /**
     * Запускает шары с ракетки.
     */
    public void fireBalls() {

        while (!_balls.isEmpty()) {
            Ball b = _balls.get(0);
            b.setSpeed(getFireSpeed(b));
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
