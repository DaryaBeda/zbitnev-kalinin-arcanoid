package model;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import model.ball.BasicBall;
import model.brick.BreakableBrick;
import model.collision.CollidedObject;
import model.interaction.CollisionListener;
import model.paddle.BasicPaddle;
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
            BasicBall newball = new BasicBall(_field, new Point2D.Double(50, 50), 8, new Speed2D(0.03, 0.03));
            newball.addCreateViewObjectListener(_fieldView);
            newball.addDeleteViewObjectListener(_fieldView);
            newball.createView();
            _field.addObject(newball);
            
            BasicBall newball1 = new BasicBall(_field, new Point2D.Double(200, 200), 8, new Speed2D(-0.03, -0.03));
            newball1.addCreateViewObjectListener(_fieldView);
            newball1.addDeleteViewObjectListener(_fieldView);
            newball1.createView();
            _field.addObject(newball1);
            
            BreakableBrick brick = new BreakableBrick(_field, new Point2D.Double(60, 120),new Dimension(0, 0));
            brick.addCreateViewObjectListener(_fieldView);
            brick.addDeleteViewObjectListener(_fieldView);
            brick.createView();
            _field.addObject(brick);
            
            BasicPaddle paddle = new BasicPaddle(_field, new Point2D.Double(350, 580), new Dimension(0, 0));
            paddle.addCreateViewObjectListener(_fieldView);
            paddle.addDeleteViewObjectListener(_fieldView);
            paddle.createView();
            _field.addObject(paddle);
        }
        
        
}
