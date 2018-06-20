package Fatec2018;

import robocode.*;
import robocode.AdvancedRobot;

/**
 * G2M_Mercurio - a robot by (your name here)
 */
public class G2M_Mercurio extends AdvancedRobot {

    /**Um buffer é uma seqüência linear e finita de elementos de um
     * tipo primitivo. Além de seu conteúdo, as propriedades essenciais de um
     * buffer são sua capacidade, limite e posição:
     */
    public final double PERCENT_BUFFER = .20;

    /**
     * run: G2M_Mercurio's default behavior
     */
    public void run() {
        
        //Armazena o tamanho do mapa neste caso a Largura
        double width = this.getBattleFieldWidth();
        
        //Armazena o tamanho do mapa neste caso a Altura
        double heigth = this.getBattleFieldHeight();
        
        //Calculo maluco pra ter o tamanho do mapa em graus
        double buffer = PERCENT_BUFFER * Math.max(width, heigth);
        
        
        while (true) {
            
            //Armazena a posicao do eixo x do nosso robo
            double xPos = this.getX();
            //Armazena a posicao do eixo y do nosso robo
            double yPos = this.getY();
            
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area de baixo
            if (yPos < buffer) {
                System.out.println("Perto de Baixo");
                
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 180)&&(this.getHeading() > 90)) {
                    this.setTurnLeft(90);
                    
                //condição que verifica a posição do robo em graus
                } else if ((this.getHeading() < 270)&&(this.getHeading() > 180)){
                    this.setTurnRight(90);
                }
                
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area de cima
            } else if (yPos > heigth - buffer) {
                System.out.println("Perto do top");
                
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 90)&&(this.getHeading() > 0)) {
                    this.setTurnRight(90);
                    
                //condição que verifica a posição do robo em graus
                } else if ((this.getHeading() < 360)&&(this.getHeading() > 270)){
                    this.setTurnLeft(90);
                }
                
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area da esquerda
            } else if (xPos < buffer){
                System.out.println("Perto da esquerda");
                
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 180)&&(this.getHeading() > 90)) {
                    this.setTurnLeft(90);
                    
                //condição que verifica a posição do robo em graus
                } else if ((this.getHeading() < 270)&&(this.getHeading() > 180)){
                    this.setTurnRight(90);
                }
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area da direita    
            } else if (xPos > width - buffer) {
                System.out.println("Perto do direita");
                
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 90)&&(this.getHeading() > 0)) {
                    this.setTurnRight(90);
                    
                //condição que verifica a posição do robo em graus    
                } else if ((this.getHeading() < 360)&&(this.getHeading() > 270)){
                    this.setTurnLeft(90);
                }
            } else {
                this.setTurnRight(0);
                this.setTurnLeft(0);
            }
            this.setAhead(10);
            this.execute();
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {

    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {

    }
}
