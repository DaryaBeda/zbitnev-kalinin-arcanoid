package model;

import com.golden.gamedev.object.Sprite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Спрайт, знающий о том, частью какого игрового объекта (IngameObjectView) он является
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class PublishingSprite {
    Sprite _sprite;
        
    public PublishingSprite () {
        _sprite = new Sprite();
    }
    
    public void setImage(BufferedImage img) {
        _sprite.setImage(img);
    }
    
    public void update(long timeElapsed) {
        _sprite.update(timeElapsed);
    }
    public void render(Graphics2D g) {
    	
    	_sprite.render(g);
    }
    
    public Sprite getSprite() {
        return this._sprite;
    }
    
}
