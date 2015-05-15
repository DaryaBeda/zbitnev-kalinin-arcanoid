package model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import model.collision.CollidedObject;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;
import com.golden.gamedev.object.collision.CollisionGroup;

/**
 * Менеджер столкновений, сообщающий о коллизиях между объектами
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class PublishingCollisionManager {

    protected HashMap<CollidedObject, ArrayList<CollidedObject>> _storage = new HashMap<>();
    private GameModel _model;
    private AdvanceCollisionGroup _advancedCollisionGroup;

    public PublishingCollisionManager() {
        _advancedCollisionGroup = new AdvanceCollisionGroup() {

            public boolean isPixelPerfectCollision() {
                return true;
            }

            public void setPixelPerfectCollision(boolean pixelPerfectCollision) {
                this.pixelPerfectCollision = true;
            }

            @Override
            public void collided(Sprite sprite, Sprite sprite1) {
                int obj1colside = -1, obj2colside = -1;

                switch (collisionSide) {
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

                CollidedObject obj1 = null;
                CollidedObject obj2 = null;
                boolean isFoundFirst = false;
                boolean isFoundSecond = false;
                GameField field = _model.getField();

                ArrayList<IngameObject> fieldObjects = field.getObjects();
                int i;
                for (i = 0; i < fieldObjects.size() && (!isFoundFirst || !isFoundSecond); i++) {
                    if (fieldObjects.get(i).isMySprite(sprite)) {
                        
                        obj1 = new CollidedObject(fieldObjects.get(i),
                                new Point2D.Double((float) sprite.getOldX(), (float) sprite.getOldY()),
                                new Speed2D(sprite.getHorizontalSpeed(), sprite.getVerticalSpeed()),
                                obj1colside, new Rectangle2D.Double(this.getCollisionShape1(sprite).getX(),
                                        this.getCollisionShape1(sprite).getY(),
                                        this.getCollisionShape1(sprite).getWidth(),
                                        this.getCollisionShape1(sprite).getHeight()));
                        
                        isFoundFirst = true;
                    }
                    if (fieldObjects.get(i).isMySprite(sprite1)) {
                        
                        obj2 = new CollidedObject(fieldObjects.get(i),
                                new Point2D.Double((float) sprite1.getOldX(), (float) sprite1.getOldY()),
                                new Speed2D(sprite1.getHorizontalSpeed(), sprite1.getVerticalSpeed()),
                                obj2colside, new Rectangle2D.Double(this.getCollisionShape1(sprite1).getX(),
                                        this.getCollisionShape1(sprite1).getY(),
                                        this.getCollisionShape1(sprite1).getWidth(),
                                        this.getCollisionShape1(sprite1).getHeight()));
                        
                        isFoundSecond = true;
                    }

                }

                if (!_storage.keySet().contains(obj1)) {
                    _storage.put(obj1, new ArrayList<CollidedObject>());
                }
                
                _storage.get(obj1).add(obj2);
            }
        };

    }

    public void setModel(GameModel model) {
        _model = model;
    }

    public AdvanceCollisionGroup getAdvanceCollisionGroup() {
        return _advancedCollisionGroup;
    }

    public boolean isMyCollisionGroup(AdvanceCollisionGroup group) {
        return _advancedCollisionGroup == group;
    }
    
    /**
     * Получить словарь столкновений объектов в текущем кадре. Объекты
     * представлены в виде CollidedObject, содержащих дополнительную информацию
     * о коллизиях
     *
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
}
