package model.collision;

import java.awt.geom.Point2D;
import model.IngameObject;
import model.Speed2D;

import model.ball.Ball;

/**
 * Поведение отскока от ракетки при столкновении.
 *
 * @author Nikita Kalinin <nixorv@gmail.com>
 *
 */
public class BehaviourPaddleRebound extends CollisionBehaviour {

    @Override
    public void invoke(CollidedObject passiveObject, CollidedObject activeObject) {
        IngameObject passiveIngameObject = passiveObject.getObject();
        IngameObject activeIngameObject = activeObject.getObject();
        Speed2D newSpeed = null;
        activeObject.getObject().setPosition(new Point2D.Double(activeIngameObject.getPosition().x,
                passiveIngameObject.getPosition().y - activeIngameObject.getDimension().height));

        // Найти два центра расчета вектора.
        Point2D.Double paddleLeftCenter = new Point2D.Double(passiveIngameObject.getPosition().x + (passiveIngameObject.getDimension().width / 5) * 2, passiveIngameObject.getPosition().y);
        Point2D.Double paddleRightCenter = new Point2D.Double(passiveIngameObject.getPosition().x + (passiveIngameObject.getDimension().width / 5) * 3, passiveIngameObject.getPosition().y);

        // Центр ракетки
        Point2D.Double paddleCenter = new Point2D.Double(passiveIngameObject.getPosition().x + passiveIngameObject.getDimension().width / 2, passiveIngameObject.getPosition().y);

        // Относительные координаты центра мяча в декартовой системе координат (точка B).
        // Считаем, что paddleCenter - это точка A(0, 0).
        Point2D.Double relBallCenter = new Point2D.Double(activeIngameObject.getPosition().x + activeIngameObject.getDimension().width / 2 - paddleCenter.x,
                paddleCenter.y - activeIngameObject.getPosition().y - activeIngameObject.getDimension().height / 2);

        // Если мяч между двумя центрами, направляем вектор вверх.
        if (relBallCenter.x <= passiveIngameObject.getDimension().width / 10 && relBallCenter.x >= -passiveIngameObject.getDimension().width / 10) {
            newSpeed = new Speed2D(0, -((Ball) activeIngameObject).getDefaultSpeedScalar());
        } else {

            // В зависимости от трети ракетки, в которой располагается мяч, выбираем центр расчета вектора скорости.
            Point2D.Double paddleNewCenter = relBallCenter.x > passiveIngameObject.getDimension().width / 10 ? paddleRightCenter : paddleLeftCenter;

            // Рассчитываем относительное положение мяча от выбранного центра.
            relBallCenter = new Point2D.Double(relBallCenter.x + paddleCenter.x - paddleNewCenter.x, relBallCenter.y);

            // Коэффициенты уравнения точки пересечения прямой и окружности.
            double a = (Math.pow(relBallCenter.x, 2) + Math.pow(relBallCenter.y, 2)) / Math.pow(relBallCenter.x, 2);
            double b = 0;
            double c = -Math.pow(((Ball) activeIngameObject).getDefaultSpeedScalar(), 2);

            // Дискриминант.
            double D = Math.pow(b, 2) - 4 * a * c;

            // Точки пересечения.
            Point2D.Double p1 = new Point2D.Double((float) ((-b + Math.sqrt(D)) / (2 * a)), 0);
            Point2D.Double p2 = new Point2D.Double((float) ((-b - Math.sqrt(D)) / (2 * a)), 0);

            // Находим y{1,2} у точек.
            p1.y = p1.x * relBallCenter.y / relBallCenter.x;
            p2.y = p2.x * relBallCenter.y / relBallCenter.x;

            // Нужная точка пересечения имеет положительную y-координату.
            Point2D.Double p = p1.y > 0 ? p1 : p2;

            // Находим горизонтальную и вертикальную сооставляющие вектора скорости.
            // y отрицательный, чтобы перейти в экранную систему координат.
            newSpeed = new Speed2D(p.x, -Math.abs(p.y));
        }
        activeObject.getObject().setSpeed(newSpeed);
    }
}
