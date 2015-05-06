/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.CollisionBounds;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import model.collision.CollidedObject;

/**
 *
 * @author пользователь
 */
public class BorderCollisionManager extends CollisionBounds {

    private GameModel _model;

    public BorderCollisionManager(int i, int i1, int i2, int i3) {
        super(i, i1, i2, i3);
    }

    public void setModel(GameModel model) {
        _model = model;
    }

    @Override
    public void collided(Sprite sprite) {
        CollidedObject obj = null;
        boolean isFound = false;
        ArrayList<IngameObject> fieldObjects = _model.getField().getObjects();
        for (int i = 0; i < fieldObjects.size() && !isFound; i++) {
            if (fieldObjects.get(i).isMySprite(sprite)) {
                if (this.isCollisionSide(TOP_COLLISION)) {
                    fieldObjects.get(i).setPosition(new Point2D.Double(fieldObjects.get(i).getPosition().getX(), fieldObjects.get(i).getPosition().getY() + 2));
                    fieldObjects.get(i).setSpeed(fieldObjects.get(i).getSpeed().flipVertical());
                } else if (this.isCollisionSide(BOTTOM_COLLISION)) {
                    //fieldObjects.get(i).setSpeed(fieldObjects.get(i).getSpeed().flipVertical());

                    fieldObjects.get(i).destroy();
                } else if (this.isCollisionSide(LEFT_COLLISION)) {
                    fieldObjects.get(i).setPosition(new Point2D.Double(fieldObjects.get(i).getPosition().getX() + 2, fieldObjects.get(i).getPosition().getY()));
                    fieldObjects.get(i).setSpeed(fieldObjects.get(i).getSpeed().flipHorizontal());
                } else {
                    fieldObjects.get(i).setPosition(new Point2D.Double(fieldObjects.get(i).getPosition().getX() - 2, fieldObjects.get(i).getPosition().getY()));
                    fieldObjects.get(i).setSpeed(fieldObjects.get(i).getSpeed().flipHorizontal());

                }

            }

        }

    }
}
