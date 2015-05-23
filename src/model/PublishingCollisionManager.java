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
            public void collided(Sprite firstSprite, Sprite secondSprite) {
                int firstCollidedSide = -1, secondCollidedSide = -1;

                switch (collisionSide) {
                    case CollisionGroup.BOTTOM_TOP_COLLISION:
                        firstCollidedSide = CollidedObject.SIDE_TOP;
                        secondCollidedSide = CollidedObject.SIDE_BOTTOM;
                        break;
                    case CollisionGroup.TOP_BOTTOM_COLLISION:
                        firstCollidedSide = CollidedObject.SIDE_BOTTOM;
                        secondCollidedSide = CollidedObject.SIDE_TOP;
                        break;
                    case CollisionGroup.RIGHT_LEFT_COLLISION:
                        firstCollidedSide = CollidedObject.SIDE_LEFT;
                        secondCollidedSide = CollidedObject.SIDE_RIGHT;
                        break;
                    case CollisionGroup.LEFT_RIGHT_COLLISION:
                        firstCollidedSide = CollidedObject.SIDE_RIGHT;
                        secondCollidedSide = CollidedObject.SIDE_LEFT;
                        break;
                    default:
                        break;
                }

                CollidedObject firstObject = null;
                CollidedObject secondObject = null;
                boolean isFoundFirst = false;
                boolean isFoundSecond = false;

                ArrayList<IngameObject> fieldObjects = _model.getField().getObjects();
                int i;
                for (i = 0; i < fieldObjects.size() && (!isFoundFirst || !isFoundSecond); i++) {
                    if (fieldObjects.get(i).isMySprite(new PublishingSprite(firstSprite))) {

                        firstObject = new CollidedObject(fieldObjects.get(i),
                                new Point2D.Double((float) firstSprite.getOldX(), (float) firstSprite.getOldY()),
                                new Speed2D(firstSprite.getHorizontalSpeed(), firstSprite.getVerticalSpeed()),
                                firstCollidedSide, new Rectangle2D.Double(this.getCollisionShape1(firstSprite).getX(),
                                        this.getCollisionShape1(firstSprite).getY(),
                                        this.getCollisionShape1(firstSprite).getWidth(),
                                        this.getCollisionShape1(firstSprite).getHeight()));

                        isFoundFirst = true;
                    }
                    if (fieldObjects.get(i).isMySprite(new PublishingSprite(secondSprite))) {

                        secondObject = new CollidedObject(fieldObjects.get(i),
                                new Point2D.Double((float) secondSprite.getOldX(), (float) secondSprite.getOldY()),
                                new Speed2D(secondSprite.getHorizontalSpeed(), secondSprite.getVerticalSpeed()),
                                secondCollidedSide, new Rectangle2D.Double(this.getCollisionShape1(secondSprite).getX(),
                                        this.getCollisionShape1(secondSprite).getY(),
                                        this.getCollisionShape1(secondSprite).getWidth(),
                                        this.getCollisionShape1(secondSprite).getHeight()));

                        isFoundSecond = true;
                    }

                }

                if (!_storage.keySet().contains(firstObject)) {
                    _storage.put(firstObject, new ArrayList<>());
                }

                _storage.get(firstObject).add(secondObject);
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
