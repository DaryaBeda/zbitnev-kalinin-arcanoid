package model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import view.IngameObjectView;
import model.ball.Ball;
import model.ball.BallPositionChangedListener;
import model.collision.CollidedObject;

/**
 * Модель игрового поля.
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class GameField implements BallPositionChangedListener {

	private ArrayList<IngameObject> _objects;
	private Dimension _dimensions;
	
    /**
     * Инициализирует поле заданного размера.
     * @param size Размер поля.
     */
    public GameField(Dimension size) {
    	
    	_objects = new ArrayList<>();
    	_dimensions = size;
    }
    
	/**
	 * Добавить объект на поле
	 * @param object Объект для добавления
	 */
	public void addObject(IngameObject object) {
		
		_objects.add(object);
	}
	
	/**
	 * Убрать объект с поля
	 * @param object Объект для удаления
	 */
	public void removeObject(IngameObject object) {
		
		_objects.remove(object);
	}
	
	/** 
	 * Получить размеры игрового поля (в пикселях).
	 * @return Размеры поля.
	 */
	public Dimension getSize() {
		
		return _dimensions;
	}
        
        public ArrayList<IngameObject> getObjects() {
            
            return (ArrayList<IngameObject>) _objects.clone();
        }

	/**
	 * Реализация этого метода отражает мяч от границ поля.
	 */
    @Override
    public void ballPositionChanged(Ball ball) {
        
        if (ball.getPosition().y < 0) {
            ball.setPosition(new Point2D.Double(ball.getPosition().x, 0));
            ball.setSpeed(ball.getSpeed().flipVertical());
        }
        
        if (ball.getPosition().x < 0 || ball.getPosition().x + ball.getSize().width > _dimensions.width) {
            if (ball.getPosition().x < 0) {
                ball.setPosition(new Point2D.Double(0, ball.getPosition().y));
            } else {
                ball.setPosition(new Point2D.Double(_dimensions.width - ball.getSize().width, ball.getPosition().y));
            }
            ball.setSpeed(ball.getSpeed().flipHorizontal());
        }
    }
    
    
    
   
}
