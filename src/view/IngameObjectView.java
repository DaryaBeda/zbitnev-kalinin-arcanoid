package view;

import com.golden.gamedev.object.Sprite;
import model.PublishingSprite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Представление отдельного игрового объекта
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class IngameObjectView {

    PublishingSprite _sprite;

    /**
     * Создает представление объекта на основе его модели и спрайта. Этот метод
     * автоматически согласует слушателей и связывает спрайт с объектом
     * представления, которому он принадлежит.
     *
     * @param sprite Спрайт, которым он будет отображен.
     */
    public IngameObjectView(PublishingSprite sprite) {

        if (sprite == null) {
            throw new NullPointerException();
        }
        this._sprite = sprite;
    }

    /**
     * Необходимо использовать вместо прямого обращения к спрайту.
     *
     * @param timeElapsed Прошедшее время.
     */
    public void update(long timeElapsed) {
        _sprite.update(timeElapsed);
    }

    public void render(Graphics2D g) {

        _sprite.render(g);
    }
    
    protected void setImage(BufferedImage img) {
        this._sprite.setImage(img);
    }
    
    public Sprite getSprite() {
        return this._sprite.getSprite();
    }

}
