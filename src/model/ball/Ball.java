package model.ball;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import model.GameField;
import model.IngameObject;
import model.Speed2D;

/**
 * Модель абстрактного шарика
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public abstract class Ball extends IngameObject {

    public Ball(GameField field, Point2D.Double position, int radius, Speed2D speed) {

        super(field, position, new Dimension(2 * radius, 2 * radius), speed);
    }

    /**
     * Возвращает радиус мяча.
     *
     * @return Радиус.
     */
    public int getRadius() {

        if (this._size.width != this._size.height) {
            throw new IllegalStateException("Height and width of Ball are not the same.");
        }

        return this._size.width;
    }

    /**
     * Задает радиус мяча.
     *
     * @param radius
     */
    public void setRadius(int radius) {

        radius = Math.abs(radius);
        this.setSize(new Dimension(2 * radius, 2 * radius));
    }

    /**
     * Внимание! Этот метод способен задать неодинаковые ширину и высоту, если
     * это потребуется, однако в этом случае вы не сможете использовать метод
     * getRadius().
     *
     * @param dim Размеры.
     */
    @Override
    public void setSize(Dimension dim) {

        super.setSize(dim);
    }

    /**
     * Здесь должен быть жестко задан скаляр скорости мяча.
     *
     * @return Скорость, с которой должен двигаться мяч.
     */
    public abstract float getDefaultSpeedScalar();

    /**
     * Задать позицию шарика, указав координаты его середины
     *
     * @param center Позиция центра шарика
     */
    public void setCenter(Point2D.Double center) {

        setPosition(new Point2D.Double(center.x - _size.width / 2, center.y - _size.height / 2));
    }

    /**
     * Получить позицию центра шарика
     *
     * @return Позиция центра шарика
     */
    public Point2D.Double getCenter() {

        return new Point2D.Double(getPosition().x + _size.width / 2,
                getPosition().y + _size.height / 2);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Ball clone = (Ball) super.clone();
        return clone;
    }
}
