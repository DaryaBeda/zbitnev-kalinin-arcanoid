package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import model.ball.BasicBall;

import model.collision.CollidedObject;
import model.interaction.CollisionListener;
import view.GameFieldView;

/**
 * Модель игры.
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */

public class GameModel  {
    public enum TYPE_OBJECT {BASIC_BALL, BREAKABKE_BRICK, UNBREAKABLE_BRICK, BASIC_PADDLE};

    protected GameField _field = null;
    protected GameFieldView _fieldView = null;
    protected ArrayList<Player> _players = new ArrayList<>();
    protected PublishingCollisionManager _manager = new PublishingCollisionManager();
    
    public GameModel (){
        _manager.setModel(this);
    }
       

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
        
        public void setFieldView(GameFieldView field) {
		
		if (field == null) {
		    throw new NullPointerException();
		}
		_fieldView = field;
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
        
        public void startGame() {
            BasicBall newball = new BasicBall(_field, new Point2D.Double(40, 160), 8, new Speed2D(0.03, -0.01));
            newball.addCreateViewObjectListener(_fieldView);
            newball.addDeleteViewObjectListener(_fieldView);
            _field.addObject(newball);

        }
        
        
}
