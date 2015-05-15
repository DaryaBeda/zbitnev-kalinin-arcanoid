/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.collision;

import java.awt.geom.Point2D;
import model.IngameObject;
import model.Speed2D;

/**
 *
 * @author Дарья
 */
public class BehaviourBallRebound extends CollisionBehaviour {

    /**
     * Экзмепляр синглтона.
     */
    private static BehaviourBallRebound _instance = null;

    /**
     * Возвращает экземпляр поведения упрогого отскока.
     *
     * @return
     */
    public static BehaviourBallRebound getInstance() {

        if (_instance == null) {
            _instance = new BehaviourBallRebound();
        }

        return _instance;
    }

    @Override
    public void invoke(CollidedObject from, CollidedObject to) {

        // Вектор скорости отражается по-разному в зависимости от геометрической формы
        // активного объекта и пассивного объекта
        IngameObject toobj = to.getObject();

        toobj.setPosition(new Point2D.Double(to.getOldPosition().x + 10 * from.getOldSpeed().x(), to.getOldPosition().y + 10 * from.getOldSpeed().y()));
        toobj.setSpeed(new Speed2D(from.getOldSpeed().x(), from.getOldSpeed().y()));

    }
}