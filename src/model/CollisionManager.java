/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import model.collision.CollidedObject;
import model.interaction.CollisionListener;

/**
 *
 * @author Дарья
 */
public final class CollisionManager implements CollisionListener{
    /**
     * Обработать столкновения
     *
     * @param storage Словарь столкновений, где ключ - столкнувшийся объект,
     * значение - список объектов, с которыми он столкнулся
     */
    @Override
    public void collisionOccured(HashMap<CollidedObject, ArrayList<CollidedObject>> storage) {

        // Вместо объектов, от которых принимается эффект (активные)
        // передаётся их копия до начала обработки вообще всех столкновений
        HashMap<CollidedObject, ArrayList<CollidedObject>> storage_copy = deepCopyStorage(storage);

        Iterator<CollidedObject> i, copyi, j, copyj;
        i = storage.keySet().iterator();
        copyi = storage_copy.keySet().iterator();

        while (i.hasNext() && copyi.hasNext()) {

            CollidedObject obj1 = i.next();
            obj1.getObject().setPosition(obj1.getObject().getPosition());
            CollidedObject obj1copy = copyi.next();
            obj1copy.getObject().setPosition(obj1copy.getObject().getPosition());
            j = storage.get(obj1).iterator();
            copyj = storage_copy.get(obj1copy).iterator();

            while (j.hasNext() && copyj.hasNext()) {

                CollidedObject obj2 = j.next();
                obj2.getObject().setPosition(obj2.getObject().getPosition());
                CollidedObject obj2copy = copyj.next();
                obj2copy.getObject().setPosition(obj2copy.getObject().getPosition());
                obj1.getObject().processCollision(obj1, obj2copy);
                obj2.getObject().processCollision(obj2, obj1copy);
            }
        }
    }

    /**
     * Порождает копию словаря коллизии вместе со всеми хранимыми объектами
     *
     * @param storage Словарь коллизии
     * @return Копия словаря коллизии
     */
    private HashMap<CollidedObject, ArrayList<CollidedObject>> deepCopyStorage(
            HashMap<CollidedObject, ArrayList<CollidedObject>> storage) {

        HashMap<CollidedObject, ArrayList<CollidedObject>> deepcopy = new HashMap<>();

        try {

            for (CollidedObject key : storage.keySet()) {

                CollidedObject key_copy = (CollidedObject) key.clone();
                ArrayList<CollidedObject> values_copy = new ArrayList<>();
                
                for (CollidedObject obj : storage.get(key)) {
                    values_copy.add((CollidedObject) obj.clone());
                }

                deepcopy.put(key_copy, values_copy);
            }
        } catch (CloneNotSupportedException exc) {
        }

        return deepcopy;
    }
}
