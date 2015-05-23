package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import model.paddle.Paddle;

/**
 * Модель игрока, управляющего ракеткой.
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class Player {

    protected ArrayList<Paddle> _paddles = new ArrayList<>();

    private GameField _field;
    /**
     * Инициализировать игрока
     *
     * @param paddle Подконтрольная игроку ракетка
     */
    public Player(Paddle paddle, GameField field) {
        this.addPaddle(paddle);
        _field = field; 
    }

    /**
     * Получить контролируемые ракетки
     *
     * @return Список контролируемых ракеток
     */
    public ArrayList<Paddle> getPaddles() {

        return (ArrayList<Paddle>) _paddles.clone();
    }

    /**
     * Добавить ракетку под контроль игрока
     *
     * @param paddle Ракетка для добавления
     */
    public void addPaddle(Paddle paddle) {

        if (paddle == null) {
            throw new NullPointerException();
        }

        _paddles.add(paddle);
    }

    /**
     * Убрать ракетку из-под контроля игрока
     *
     * @param paddle Ракетка
     */
    public void removePaddle(Paddle paddle) {

        _paddles.remove(paddle);
    }

    /**
     * Переместить все свои ракетки в указанную позицию по горизонтали
     *
     * @param x позиция по горизонтали
     */
    public void setPaddlesPositionX(int x) {

        for (Paddle p : _paddles) {
            int actualx;
            if (x > _field.getDimension().width - p.getDimension().width) {
                actualx = _field.getDimension().width - p.getDimension().width;
            } else if (x < 0) {
                actualx = 0;
            } else {
                actualx = x;
            }
            p.setPosition(new Point2D.Double(actualx, p.getPosition().y));
        }
    }

    /**
     * Переместить все свои ракетки в указанном направлении. Величину сдвига
     * жёстко задана внутри класса.
     *
     * @param dir Направление перемещения
     */
    public void movePaddles(Direction dir) {

        long delta;
        for (Paddle p : _paddles) {

            delta = Math.round(p.getDimension().width / 3.0 * 2.0);
            delta = dir.equals(Direction.west()) ? -delta : delta;
            if (p.getPosition().x + p.getDimension().width + delta > _field.getDimension().width) {
                p.setPosition(new Point2D.Double(_field.getDimension().width - p.getDimension().width, p.getPosition().y));
            } else if (p.getPosition().x + delta < 0) {
                p.setPosition(new Point2D.Double(0, p.getPosition().y));
            } else {
                p.move(new Point2D.Double(delta, 0));
            }
        }
    }

    /**
     * Заставляет подконтрольные ракетки задать скорость мячам.
     */
    public void firePaddles() {

        for (Paddle p : _paddles) {
            p.fireBalls();
        }
    }
}
