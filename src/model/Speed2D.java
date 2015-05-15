package model;

/**
 * Вектор скорости в двумерном пространстве
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public final class Speed2D implements Cloneable {

    private final double _x;
    private final double _y;

    /**
     * Создаёт экземпляр вектора скорости на декартовой плоскости
     *
     * @param xspeed Компонента вектора по x
     * @param yspeed Компонента вектора по y
     */
    public Speed2D(double xspeed, double yspeed) {
        _x = xspeed;
        _y = yspeed;
    }

    @Override
    public Object clone() {

        return new Speed2D(_x, _y);
    }

    @Override
    public boolean equals(Object other) {

        Speed2D oth = (Speed2D) other;
        return oth._x == this._x && oth._y == this._y;
    }

    public double x() {
        return _x;
    }

    public double y() {
        return _y;
    }

    public Speed2D flipHorizontal() {
        return new Speed2D(-_x, _y);
    }

    public Speed2D flipVertical() {
        return new Speed2D(_x, -_y);
    }
}
