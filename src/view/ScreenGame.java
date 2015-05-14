package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import model.GameField;
import model.GameModel;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.background.ImageBackground;

import controller.GameController;
import model.Player;

/**
 * Режим игры
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class ScreenGame extends GameObject {

    GameModel _model;
    GameFieldView _fieldView;
    GameController _controller;

    public ScreenGame(GameEngine arg0) {
        super(arg0);
    }

    @Override
    public void initResources() {

        // Задать фон уровня.
        BufferedImage fieldBg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = fieldBg.getGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Инициализация уровня
        GameField field = new GameField(this.bsGraphics.getSize());

        // Модель слушает сообщения о коллизиях
        _model = new GameModel();
        _model.setField(field);
        // Инициализация представления уровня
        _fieldView = new GameFieldView(_model);
        _model.setFieldView(_fieldView);
        _fieldView.setBackground(new ImageBackground(fieldBg));
        _fieldView.addCollisionListener(_model.getField());
        _fieldView.getBorderCollisionManager().setModel(_model);
        _model.startGame();
        Player player = new Player(_model.getPaddle());
        _controller = new GameController(player, bsInput);
        // Инициализация закончена. Спрятать курсор мыши перед началом игры.
        this.hideCursor();
    }

    @Override
    public void render(Graphics2D arg0) {

        _fieldView.render(arg0);
    }

    @Override
    public void update(long arg0) {

        // Апдейт всех объектов
        _fieldView.update(arg0);
        _controller.update();
    }

}
