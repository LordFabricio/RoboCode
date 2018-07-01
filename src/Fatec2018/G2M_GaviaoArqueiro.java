package Fatec2018;
import robocode.*;
import java.awt.Color;

public class G2M_GaviaoArqueiro extends AdvancedRobot
{
boolean GunD = true;
// Controla o número de tiros que não acertaram o alvo, se perderam ou bateram na parede
int erros;
// Controla quantos tiros foram levados, se levar tiros demais ele faz um movimento de fuga
int levou_tiro;

public final double PERCENT_BUFFER = .21;
	double width;
        double heigth;
        double buffer;
	double xPos;
	double yPos;

// Faz sempre:
public void run() {
	// Seta as cores da equipe:
		setBodyColor(new Color(255, 0, 0));
		setGunColor(new Color(255, 0, 0));
		setRadarColor(new Color(0, 0, 0));
	// Inicializa as variáveis com o valor 0
		erros = 0;
		levou_tiro = 0;
	
	//Armazena o tamanho do mapa neste caso a Largura
        width = this.getBattleFieldWidth();
        
        //Armazena o tamanho do mapa neste caso a Altura
        heigth = this.getBattleFieldHeight();
        
        //Calculo maluco pra ter o tamanho do mapa em graus
        buffer = PERCENT_BUFFER * Math.max(width, heigth);
		
	while(true) {
	 		//Armazena a posicao do eixo x do nosso robo
            xPos = this.getX();
            //Armazena a posicao do eixo y do nosso robo
            yPos = this.getY();
            // Começa a girar os canhões junto com o Radar
                if (GunD == true){
                    setTurnGunRight(180);
		}
		else {
                    setTurnGunLeft(180);
		}
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area de baixo
            if (yPos < buffer) {
                System.out.println("Perto de Baixo");
	
                
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 180)&&(this.getHeading() > 90)) {
                    this.setTurnLeft(90);
                    andar ();
                    
                //condição que verifica a posição do robo em graus
                } else if ((this.getHeading() < 270)&&(this.getHeading() > 180)){
                    this.setTurnRight(90);
                    andar ();
                } else {
                    andar ();
				}
                
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area de cima
            } else if (yPos > heigth - buffer) {
                System.out.println("Perto do top");
        
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 90)&&(this.getHeading() > 0)) {
                    this.setTurnRight(90);
                    andar ();
                    
                //condição que verifica a posição do robo em graus
                } else if ((this.getHeading() < 360)&&(this.getHeading() > 270)){
                    this.setTurnLeft(90);
                    andar ();
                }  else {
                    andar ();
				}
                
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area da esquerda
            } else if (xPos < buffer){
                System.out.println("Perto da esquerda");
         
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 360)&&(this.getHeading() > 270)) {
                    this.setTurnRight(90);
                    andar ();
                    
                //condição que verifica a posição do robo em graus
                } else if ((this.getHeading() < 270)&&(this.getHeading() > 180)){
                    this.setTurnLeft(90);
                    andar ();
                } else {
                    andar ();
				}
            //Condição que analiza a posição do nosso robo relativo ao mapa - toda a area da direita    
            } else if (xPos > width - buffer) {
                System.out.println("Perto da direita");
              
                //condição que verifica a posição do robo em graus
                if ((this.getHeading() < 90)&&(this.getHeading() > 0)){
                    this.setTurnLeft(90);
                    andar ();
                    
                //condição que verifica a posição do robo em graus    
                } else if ((this.getHeading() < 180)&&(this.getHeading() > 90)){
                    this.setTurnRight(90);
                    andar ();
                } else {
                    andar ();
				}
            } else {
		// Sempre gira o canhão se não estiver em uma outra função:
		if (GunD == true){
                    setTurnGunRight(180);
		}
		else {
                    setTurnGunLeft(180);
		}
		}
                    this.execute();
	}
}
// Ao escanear um robô:
public void onScannedRobot(ScannedRobotEvent e) {
	// Verifica se não é alguém do seu time, se sim, retorna:
		String name = e.getName();
		if (name.indexOf("G2M") != -1  || name.indexOf("Border") != -1) {
		return;
		}
	// Se levou o tiro mais de 2 vezes, faz esta movimentação, e zera a variável responsável
	if(levou_tiro > 2) {
	turnRight(90);
	setAhead(100);
	levou_tiro = 0;
	}
	// Se errar mais de 2 tiros, se direciona ao inimigo e segue em frente, melhorando muito sua precisão
	if (erros > 1) {
	turnRight(e.getBearing());
	// Vai em frente, pegando a distância do inimigo e diminuindo 30 pixels neste valor, para evitar colisão
	setAhead(e.getDistance() - 30);
	// Se certifica em zerar a variável de erros
	m_erros(0,100);
	}
	//  Pega o valor do quanto o canhão deve virar em relação ao seu ângulo atual, usando uma função para normalizar o ângulo e buscar o menor caminho. O cálculo dentro do parênteses pega o 
	// ângulo do inimigo em relação a tela, e adiciona o seu ângulo menos o ângulo do radar.
	double mira = normalRelativeAngle((e.getBearing() + (getHeading() - getRadarHeading())));
	// Dá o comando para o canhão virar em relação ao valor obtido pela função de normalizar ângulos e mira para o inimigo:
	turnGunRight(mira);
	// Atira com potência máxima
	if (getVelocity() > 1) {
	
	}else {
	fire(3);
	}
	
}

// Ao levar um tiro de um inimigo 
public void onHitByBullet(HitByBulletEvent e) {
// Adicionar 1 a variável levou_tiro
	levou_tiro++;
}

// Quando bater em uma parede...
public void onHitWall(HitWallEvent e) {
	// Quando bater na parede, anda para trás, vira para a esquerda e anda para frente.
	turnRight(180);
	setAhead(20);
}

// Quando um tiro seu acertar
public void onBulletHit(BulletHitEvent event) {
// Diminui 1 a variável de erros
	m_erros(0,1);
}

// Quando a bala se perde (não acerta nenhum robô)
public void onBulletMissed(BulletMissedEvent event) {
	// Adiciona 1 a variável de erros
		m_erros(1,0);
}

// Normalização dos ângulos
public double normalRelativeAngle(double angle) {
		// Se o ângulo estiver entre -180° e 180° retorna o ângulo, por não ser preciso normalizar.
		if (angle > -180 && angle <= 180) {
			return angle;
		}
		// Cria uma nova variável para dar retorno com o novo valor.
		double fixedAngle = angle;
 
		// Enquanto menos que -180° adiciona 360° para normalizar o ângulo ao sistema.
		while (fixedAngle <= -180) {
			fixedAngle += 360;
		}
		// Enquanto maior que 180° diminiui 360° para pegar o ângulo equivalente.
		while (fixedAngle > 180) {
			fixedAngle -= 360;
		}
		// Retorna o ângulo obtido.
		return fixedAngle;
	}
	
// Função para controlar a variável de erros, pode adicionar e incrementar. Se o valor estiver menor do que zero, ele iguala a 0, pois é o número mínimo possível.
public void m_erros (int a, int d) {
	if (a > 0) {
		erros += a;
	} else {
		erros -= d;
	}
	if (erros < 0) {
		erros = 0;
	}
}	

// Função para ir a frente, criei porque usei esse comando muitas vezes e para fazer os testes tinha que mudar os valores um a um... assim é mais fácil:
public void andar () {
	setAhead(100);
}

// Se o Gavião bater em um inimigo: 
	public void onHitRobot(HitRobotEvent e) {
		// Recua e vira a direita se for amigo
		String name = e.getName();
		if (name.indexOf("G2M") != -1  || name.indexOf("Border") != -1) {
		back(50);
		turnRight(90);
		}
		// Só recua, se for inimigo
		 else if (e.isMyFault()) {
			back(30);
		}
	}
}