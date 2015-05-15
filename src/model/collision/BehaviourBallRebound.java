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

    @Override
    public void invoke(CollidedObject passiveObject, CollidedObject activeObject) {

        // Вектор скорости отражается по-разному в зависимости от геометрической формы
        // активного объекта и пассивного объекта
        IngameObject activeIngameObject = activeObject.getObject();

        activeIngameObject.setPosition(new Point2D.Double(activeObject.getOldPosition().x + 10 * passiveObject.getOldSpeed().x(), activeObject.getOldPosition().y + 10 * passiveObject.getOldSpeed().y()));
        activeIngameObject.setSpeed(new Speed2D(passiveObject.getOldSpeed().x(), passiveObject.getOldSpeed().y()));

    }
}
