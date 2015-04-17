package model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import model.collision.CollidedObject;
import model.PublishingSprite; 

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;
import com.golden.gamedev.object.collision.CollisionGroup;
import com.golden.gamedev.object.collision.CollisionShape;
import java.util.Iterator;
import model.interaction.CollisionListener;

/**
 * Менеджер столкновений, сообщающий о коллизиях между объектами
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class PublishingCollisionManager extends AdvanceCollisionGroup implements CollisionListener {

	protected HashMap<CollidedObject, ArrayList<CollidedObject>> _storage = new HashMap<>();
        private  GameModel _model;
        
        public PublishingCollisionManager (GameModel model) {
            
            _model = model;
        }
        
	
	@Override
	public void collided(Sprite arg0, Sprite arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCollide(Sprite s1, Sprite s2, CollisionShape shape1, CollisionShape shape2) {
		
		boolean retval = super.isCollide(s1, s2, shape1, shape2);
		
		// Словарь столкновений будет формироваться в процессе детекции коллизий
		if (retval) {
		
			int obj1colside = -1, obj2colside = -1;
			switch (this.collisionSide) {
			case CollisionGroup.BOTTOM_TOP_COLLISION:
				obj1colside = CollidedObject.SIDE_TOP;
				obj2colside = CollidedObject.SIDE_BOTTOM;
				break;
			case CollisionGroup.TOP_BOTTOM_COLLISION:
				obj1colside = CollidedObject.SIDE_BOTTOM;
				obj2colside = CollidedObject.SIDE_TOP;
				break;
			case CollisionGroup.RIGHT_LEFT_COLLISION:
				obj1colside = CollidedObject.SIDE_LEFT;
				obj2colside = CollidedObject.SIDE_RIGHT;
				break;
			case CollisionGroup.LEFT_RIGHT_COLLISION:
				obj1colside = CollidedObject.SIDE_RIGHT;
				obj2colside = CollidedObject.SIDE_LEFT;
				break;
			default:
				break;
			}
                        // TODO: По спрайту найти объект
			CollidedObject obj1 = null;
                        CollidedObject obj2 = null;
                        boolean isFoundFirst = false;
                        boolean isFoundSecond = false;
                        ArrayList<IngameObject> fieldObjects = _model.getField().getObjects();
                        for (int i = 0; i < fieldObjects.size() && !isFoundFirst && !isFoundSecond; i++) {
                            if (fieldObjects.get(i).isMySprite(s1)) {
                                obj1 = new CollidedObject(fieldObjects.get(i), 
                                    new Point2D.Double((float)s1.getOldX(), (float)s1.getOldY()),
                                    obj1colside, new Rectangle2D.Double(shape1.getX(), 
                                    shape1.getY(), 
                                    shape1.getWidth(), 
                                    shape1.getHeight()));
                                isFoundFirst = true;
                            }
                            if (fieldObjects.get(i).isMySprite(s2)) {
                                obj2 = new CollidedObject(fieldObjects.get(i), 
                                    new Point2D.Double((float)s2.getOldX(), (float)s2.getOldY()),
                                    obj1colside, new Rectangle2D.Double(shape2.getX(), 
                                    shape2.getY(), 
                                    shape2.getWidth(), 
                                    shape2.getHeight()));
                                isFoundSecond = true;
                            }
                                
                        }
			
			if (!_storage.keySet().contains(obj1)) {
				_storage.put(obj1, new ArrayList<CollidedObject>());
			}
			_storage.get(obj1).add(obj2);
		}
		
		return retval;
	}
	
	/**
	 * Получить словарь столкновений объектов в текущем кадре. Объекты представлены в виде 
	 * CollidedObject, содержащих дополнительную информацию о коллизиях
	 * @return Словарь столкновений.
	 */
	public HashMap<CollidedObject, ArrayList<CollidedObject>> getCollidedStorage() {
		return _storage;
	}
	
	/**
	 * Очистить словарь столкновений объектов в текущем кадре.
	 */
	public void clearCollidedStorage() {
		_storage.clear();
	}
        
        /**
     * Обработать столкновения
     * @param storage Словарь столкновений, где ключ - столкнувшийся объект, значение - 
     * список объектов, с которыми он столкнулся
     */
    public void collisionOccured(
			HashMap<CollidedObject, ArrayList<CollidedObject>> storage) {
		
    	// Вместо объектов, от которых принимается эффект (активные)
    	// передаётся их копия до начала обработки вообще всех столкновений
    	HashMap<CollidedObject, ArrayList<CollidedObject>> storage_copy = deepCopyStorage(storage);
    	
    	Iterator<CollidedObject> i, copyi, j, copyj;
    	i = storage.keySet().iterator();
    	copyi = storage_copy.keySet().iterator();
    	
    	while (i.hasNext() && copyi.hasNext()) {
    		
    		CollidedObject obj1 = i.next();
    		CollidedObject obj1copy = copyi.next();
    		j = storage.get(obj1).iterator();
    		copyj = storage_copy.get(obj1copy).iterator();
    		
    		while (j.hasNext() && copyj.hasNext()) {
    			
    			CollidedObject obj2 = j.next();
    			CollidedObject obj2copy = copyj.next();
    			obj1.object().processCollision(obj1, obj2copy);
    			obj2.object().processCollision(obj2, obj1copy);
    		}
    	}
    }
    
     /**
     * Порождает копию словаря коллизии вместе со всеми хранимыми объектами
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
        			values_copy.add((CollidedObject)obj.clone());
        		}
        		
        		deepcopy.put(key_copy, values_copy);
        	}
    	}
    	catch (CloneNotSupportedException exc) {
    		exc.printStackTrace();
    	}
    	
    	return deepcopy;
    }
}
