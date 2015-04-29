/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.border;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import model.GameField;
import static model.GameModel.TYPE_OBJECT.BASIC_BALL;
import static model.GameModel.TYPE_OBJECT.BOTTOM_BORDER;
import static model.GameModel.TYPE_OBJECT.HORIZONTAL_BORDER;
import static model.GameModel.TYPE_OBJECT.VERTICAL_BORDER;
import model.IngameObject;
import model.interaction.CreateViewObjectListener;

/**
 *
 * @author пользователь
 */
public class Border extends IngameObject {

    public enum TYPE {

        HORIZONTAL, VERTICAL, BOTTOM
    };

    private TYPE _type;

    public Border(GameField field, Point2D.Double pos,TYPE type) {
        super(field,pos,new Dimension(0, 0));
        _type = type;
    }

    public void createView() {
        for (CreateViewObjectListener l : _createViewObjectListeners) {
            if (_type == TYPE.BOTTOM) {
                l.createViewObject(_sprite, BOTTOM_BORDER);
            } else if (_type == TYPE.VERTICAL) {
                l.createViewObject(_sprite, VERTICAL_BORDER);
            } else if (_type == TYPE.HORIZONTAL) {
                l.createViewObject(_sprite, HORIZONTAL_BORDER);
            }
        }
    }
}
