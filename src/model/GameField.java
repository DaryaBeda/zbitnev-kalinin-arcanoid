package model;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Модель игрового поля.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class GameField {

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
    public Dimension getDimension() {

        return _dimension;
    }

    public ArrayList<IngameObject> getObjects() {

        return (ArrayList<IngameObject>) _objects.clone();
    }

}
