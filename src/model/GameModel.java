package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.collision.CollidedObject;
import model.interaction.CollisionListener;

/**
 * Модель игры.
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class GameModel  {

    protected GameField _field = null;
    protected ArrayList<Player> _players = new ArrayList<>();
    protected PublishingCollisionManager _manager = new PublishingCollisionManager(this);
       

	/**
	 * Назначить игровое поле
	 * @param field
	 */
	public void setField(GameField field) {
		
		if (field == null) {
		    throw new NullPointerException();
		}
		_field = field;
	}
	
	/**
	 * Получить игровое поле
	 * @return Текущее поле
	 */
	public GameField getField() {
		
		return _field;
	}
	
	
	/**
	 * Обновляет модель. В реализации данного класса ничего не делает.
	 * @param arg Аргумент.
	 */
	public void update(Object arg) {
	    
	}
        
        public PublishingCollisionManager getManager () {
            return _manager;
        }
        
        
}
