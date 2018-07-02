package fatec2018;
import robocode.*;
import java.awt.Color;

public class G2M_Mercurio extends AdvancedRobot
{
	public final double PERCENT_BUFFER = .32;
	double width;
        double heigth;
        double buffer;
	double xPos;
	double yPos;
    /**
     * run: G2M_Mercurio's default behavior
     */
    public void run() {
        // Seta as cores da equipe:
	setBodyColor(new Color(255, 0, 0));
	setGunColor(new Color(255, 0, 0));
	setRadarColor(new Color(0, 0, 0));
        //Armazena o tamanho do mapa neste caso a Largura
        width = this.getBattleFieldWidth();
        
        //Armazena o tamanho do mapa neste caso a Altura
        heigth = this.getBattleFieldHeight();
        
        //Calculo maluco pra ter o tamanho do mapa em graus
        buffer = PERCENT_BUFFER * Math.max(width, heigth);
        
        
        while (true) {
            //Armazena a posicao do eixo x do nosso robo
            xPos = this.getX();
            //Armazena a posicao do eixo y do nosso robo
            yPos = this.getY();
			
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
                if ((this.getHeading() < 360)&&(this.getHeading() > 270)) {
                    this.setTurnRight(90);
                    
                //condição que verifica a posição do robo em graus
                } else if ((this.getHeading() < 270)&&(this.getHeading() > 180)){
                    this.setTurnLeft(90);
                }
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area da direita    
            } else if (xPos > width - buffer) {
                System.out.println("Perto da direita");
              
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 90)&&(this.getHeading() > 0)){
                    this.setTurnLeft(90);
                    
                //condição que verifica a posição do robo em graus    
                } else if ((this.getHeading() < 180)&&(this.getHeading() > 90)){
                    this.setTurnRight(90);
                }
            } else {
                this.setTurnRight(0);
                this.setTurnLeft(0);
            }
			System.out.println("Graus: "+this.getHeading());
            this.setAhead(180);
            this.execute();
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
		// Verifica se não é alguém do seu time, se sim, retorna:
		String name = e.getName();
		if (name.indexOf("G2M") != -1  || name.indexOf("Border") != -1) {
                    return;
		}
		if (e.getDistance() < 200) {
			fire(3);
		} else
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
	turnRight(180);
	setAhead(20);
    }
	
    // Se o Mercurio bater em um inimigo: 
	public void onHitRobot(HitRobotEvent e) {
            // Recua e vira para a esquerda
            if (e.isMyFault()) {
		back(30);
		turnRight(90);
		}
	}
}
