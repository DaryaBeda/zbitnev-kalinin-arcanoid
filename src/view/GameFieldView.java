package view;

import java.util.ArrayList;
import java.util.HashMap;

import model.PublishingCollisionManager;
import model.collision.CollidedObject;
import model.interaction.CollisionListener;

import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.SpriteGroup;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BorderCollisionManager;
import model.GameModel;
import model.PublishingSprite;
import model.interaction.ViewObjectListener;

/**
 * Игровое поле арканоида. Содержит все обекты игры, ответственнен за
 * обновление, рендеринг и проверку стоклновений
 *
 * @author Gregory Zbitnev <zbitnev@hotmail.com>
 *
 */
public class GameFieldView extends PlayField implements ViewObjectListener {

    private ArrayList<IngameObjectView> _objectViews = new ArrayList<>();
    private ArrayList<CollisionListener> _collisionListners = new ArrayList<>();
    private ArrayList<PublishingCollisionManager> _managers = new ArrayList<>();
    private ArrayList<CollisionManager> _advanceManagers = new ArrayList<>();
    BorderCollisionManager _border_manager;

    public GameFieldView(GameModel model) {

        SpriteGroup balls = new SpriteGroup("balls");
        SpriteGroup bricks = new SpriteGroup("bricks");
        SpriteGroup paddles = new SpriteGroup("paddles");

        this.addGroup(balls);
        this.addGroup(bricks);
        this.addGroup(paddles);

        PublishingCollisionManager manager = new PublishingCollisionManager();
        manager.setModel(model);
        _managers.add(manager);
        _advanceManagers.add(manager.getAdvanceCollisionGroup());
        this.addCollisionGroup(balls, bricks, manager.getAdvanceCollisionGroup());

        manager = new PublishingCollisionManager();
        manager.setModel(model);
        _managers.add(manager);
        _advanceManagers.add(manager.getAdvanceCollisionGroup());
        this.addCollisionGroup(balls, balls, manager.getAdvanceCollisionGroup());

        manager = new PublishingCollisionManager();
        manager.setModel(model);
        _managers.add(manager);
        this.addCollisionGroup(balls, paddles, manager.getAdvanceCollisionGroup());
        _advanceManagers.add(manager.getAdvanceCollisionGroup());

        _border_manager = new BorderCollisionManager(0, 0, 800, 600);

        this.addCollisionGroup(balls, null, _border_manager);

    }

    public BorderCollisionManager getBorderCollisionManager() {
        return _border_manager;
    }

    @Override
    public void update(long timeElapsed) {

        super.update(timeElapsed);
        for (IngameObjectView ov : _objectViews) {
            ov.update(timeElapsed);
        }

        // Формируем словарь столкновений
        CollisionManager[] mgrs = this.getCollisionGroups();
        HashMap<CollidedObject, ArrayList<CollidedObject>> collisions = new HashMap<>();
        PublishingCollisionManager publishingCollisionManager = null;
        for (int i = 0; i < mgrs.length; i++) {
            if (_advanceManagers.indexOf(mgrs[i]) >= 0) {
                publishingCollisionManager = _managers.get(_advanceManagers.indexOf(mgrs[i]));

                HashMap<CollidedObject, ArrayList<CollidedObject>> map
                        = publishingCollisionManager.getCollidedStorage();

                // Если словарь столкновений не пуст, формируем один большой словарь столкновений
                if (!map.isEmpty()) {
                    attachStorage(collisions, map);
                    publishingCollisionManager.clearCollidedStorage();
                }
            }
        }

        // Если столкновения произошли -- посылаем сигнал модели
        if (!collisions.isEmpty()) {

            collisions = removeCouplingFromStorage(collisions);
            for (CollisionListener l : _collisionListners) {
                l.collisionOccured(collisions);
            }
        }
    }

    /**
     * Возвращает группу спрайтов мячей.
     *
     * @return Группа спрайтов.
     */
    private SpriteGroup getBallsGroup() {

        return this.getGroup("balls");
    }

    /**
     * Возвращает группу спрайтов кирпичей.
     *
     * @return Группа спрайтов.
     */
    private SpriteGroup getBricksGroup() {

        return this.getGroup("bricks");
    }

    /**
     * Возвращает группу спрайтов ракеток.
     *
     * @return Группа спрайтов.
     */
    private SpriteGroup getPaddlesGroup() {

        return this.getGroup("paddles");
    }

    /**
     * Добавить слушателя событий о произошедших на поле столкновениях
     *
     * @param l Добавляемый слушатель
     */
    public void addCollisionListener(CollisionListener l) {
        _collisionListners.add(l);
    }

    /**
     * Удалить слушателя событий о произошедших на поле столкновениях
     *
     * @param l Удаляемый слушатель
     */
    public void removeCollisionListener(CollisionListener l) {
        _collisionListners.remove(l);
    }

    /**
     * Копирует сообщения о столкновениях из одного словаря в другой
     *
     * @param to Словарь, который будет дополнен новыми сообщениями
     * @param from Словарь, из которого будут скопированы сообщения
     */
    private void attachStorage(HashMap<CollidedObject, ArrayList<CollidedObject>> to,
            HashMap<CollidedObject, ArrayList<CollidedObject>> from) {

        for (CollidedObject obj : from.keySet()) {

            // Если такого ключа не содержится -- просто добавляем новую запись в словарь
            // Если такой ключ есть -- копируем значения из списка
            if (!to.containsKey(obj)) {
                to.put(obj, from.get(obj));
            } else {

                for (CollidedObject listobj : from.get(obj)) {

                    if (!to.get(obj).contains(listobj)) {
                        to.get(obj).add(listobj);
                    }
                }
            }
        }
    }

    /**
     * Просеять словарь столкновений и удалить дублирующиеся ассоциации
     *
     * @param st Словарь столкновений
     */
    private HashMap<CollidedObject, ArrayList<CollidedObject>>
            removeCouplingFromStorage(HashMap<CollidedObject, ArrayList<CollidedObject>> st) {

        HashMap<CollidedObject, ArrayList<CollidedObject>> newStorage = new HashMap<>();

        for (CollidedObject key : st.keySet()) {
            for (CollidedObject val : st.get(key)) {

                // Если в словарь уже не добавлена "обратная" ассоциация
                if (!newStorage.containsKey(val) || !newStorage.get(val).contains(key)) {

                    if (!newStorage.containsKey(key)) {
                        newStorage.put(key, new ArrayList<CollidedObject>());
                    }
                    newStorage.get(key).add(val);
                }
            }
        }

        return newStorage;
    }

    @Override
    public void createViewObject(PublishingSprite sprite, GameModel.TYPE_OBJECT type) {
        switch (type) {
            case BASIC_BALL:
                try {
                    BasicBallView basicBall = new BasicBallView(sprite);
                    _objectViews.add(basicBall);
                    getBallsGroup().add(basicBall.getSprite());
                } catch (IOException ex) {
                    Logger.getLogger(GameFieldView.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case BASIC_PADDLE:
                try {
                    BasicPaddleView basicPaddle = new BasicPaddleView(sprite);
                    _objectViews.add(basicPaddle);
                    getPaddlesGroup().add(basicPaddle.getSprite());
                } catch (IOException ex) {
                    Logger.getLogger(GameFieldView.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case BREAKABKE_BRICK:
                try {
                    BreakableBrickView breakableBrick = new BreakableBrickView(sprite);
                    _objectViews.add(breakableBrick);
                    getBricksGroup().add(breakableBrick.getSprite());
                } catch (IOException ex) {
                    Logger.getLogger(GameFieldView.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case UNBREAKABLE_BRICK:
                try {
                    UnbreakableBrickView unbreakableBrick = new UnbreakableBrickView(sprite);
                    _objectViews.add(unbreakableBrick);
                    getBricksGroup().add(unbreakableBrick.getSprite());
                } catch (IOException ex) {
                    Logger.getLogger(GameFieldView.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
        }
    }

    @Override
    public void deleteViewObject(PublishingSprite sprite) {
        IngameObjectView objectView = searchView(sprite);
        _objectViews.remove(objectView);
        if (objectView instanceof BasicBallView) {
            getBallsGroup().remove(objectView.getSprite());
        } else if (objectView instanceof BrickView) {
            getBricksGroup().remove(objectView.getSprite());
        } else if (objectView instanceof BasicPaddleView) {
            getPaddlesGroup().remove(objectView.getSprite());
        }
    }

    private IngameObjectView searchView(PublishingSprite sprite) {
        for (IngameObjectView objectView : _objectViews) {
            if (objectView.getSprite() == sprite.getSprite()) {
                return objectView;
            }
        }
        return null;
    }
}
