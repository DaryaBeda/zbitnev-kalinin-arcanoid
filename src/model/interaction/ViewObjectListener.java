/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interaction;

import model.GameModel.TYPE_OBJECT;
import model.PublishingSprite;

/**
 *
 * @author Дарья
 */
public interface ViewObjectListener {

    public void createViewObject(PublishingSprite sprite, TYPE_OBJECT type);
    public void deleteViewObject(PublishingSprite sprite);
}
