package Robos;
import robocode.*;

public class Robo1 extends Robot {
    
    boolean tiro_ac = false;
    boolean tiro_missed = false;
    
    public void run() {

        while(true) {
            if (tiro_missed == true) {
            turnLeft(80);
            ahead(40);
            tiro_missed = false;
            }
            turnGunRight(360);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (tiro_ac == true) {
        fire(3);
        tiro_ac = false;
        } else
        fire(1);
    }

    public void onHitByBullet(HitByBulletEvent e) {
        back(40);
        turnLeft(80);
        ahead(40);
    }

    public void onHitWall(HitWallEvent e) {
        back(40);
        turnLeft(80);
        ahead(40);
    }

    public void onBulletHit(BulletHitEvent event) {
        tiro_ac = true;
    }

    public void onBulletMissed(BulletMissedEvent event) {
        tiro_missed = true;
    }
}