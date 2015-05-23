package model;

import com.golden.gamedev.object.Sprite;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Спрайт, знающий о том, частью какого игрового объекта (IngameObjectView) он
 * является
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class PublishingSprite {

    private Sprite _sprite = new Sprite();

    public PublishingSprite() {
    }

    public void setImage(BufferedImage image) {
        _sprite.setImage(image);
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

    public void setSpeed(Speed2D speed) {
        _sprite.setHorizontalSpeed(speed.x());
        _sprite.setVerticalSpeed(speed.y());
    }

    public void setPosition(Point2D.Double position) {
        _sprite.setX(position.getX());
        _sprite.setY(position.getY());

    }

    public Speed2D getSpeed() {
        Speed2D speed = new Speed2D(_sprite.getHorizontalSpeed(), _sprite.getVerticalSpeed());
        return (Speed2D) speed.clone();
    }

    public Point2D.Double getPosition() {
        Point2D.Double point = new Point2D.Double(_sprite.getX(), _sprite.getY());
        return (Point2D.Double) point.clone();
    }
}
