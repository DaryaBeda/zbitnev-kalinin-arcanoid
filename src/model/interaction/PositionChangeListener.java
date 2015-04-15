package model.interaction;

import java.awt.geom.Point2D;

/**
 * Интерфейс слушателя событий изменения позиции.
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public interface PositionChangeListener {

    /**
     * Позиция изменилась.
     *
     * @param newposition
     */
    public void positionChanged(Point2D.Double newposition);
}
