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
                this.isCollisionSide(BOTTOM_COLLISION);
                if(this.isCollisionSide(TOP_COLLISION)){
                    fieldObjects.get(i).setSpeed(fieldObjects.get(i).getSpeed().flipVertical());
                }
                this.isCollisionSide(LEFT_COLLISION);
                this.isCollisionSide(RIGHT_COLLISION);
            }

        }

    }
}
