package Fatec2018;
import robocode.*;

public class Robo1 extends AdvancedRobot
{
boolean inicio = true;
int erros;
// Variavel de controle para verificar se o tiro foi acertado
boolean tiro_ac = false;

public void run() {
	if (inicio == true) {
	erros = 0;
	}
	while(true) {
		turnGunRight(10);
	}
}
public void onScannedRobot(ScannedRobotEvent e) {
	if (erros > 1) {
	turnRight(e.getBearing());
	setAhead(e.getDistance() - 30);
	m_erros(0,100);
	}
	//  Pega o valor do quanto o canhão deve virar em relação ao seu ângulo atual, usando uma função para normalizar o ângulo e buscar o menor caminho. O cálculo dentro do parênteses pega o 
	// ângulo do inimigo em relação
	double mira = normalRelativeAngle((e.getBearing() + (getHeading() - getRadarHeading())));
	// Dá o comando para o canhão virar em relação ao valor obtido pela função de normalizar ângulos:
	
	turnGunRight(mira);
	// Verifica se o último tiro foi acertado e se sim, atira com potência (3) e desativa a variável booleana responsável, caso contrário, atira com potência (1).
	if (tiro_ac == true || e.getDistance() < 50) {
	fire(3);
	} else{
	fire(1);
	}
}

public void onHitByBullet(HitByBulletEvent e) {

}

public void onHitWall(HitWallEvent e) {
	// Quando bater na parede, andar para trás, vira para a esquerda e anda para frente.
	setBack(20);
	setTurnLeft(90);
	setAhead(20);
}

public void onBulletHit(BulletHitEvent event) {
	// Quando a bala acertar o inimigo ativa a variável booleana para que possa ser verificada na hora de escolher a potência do tiro.
	tiro_ac = true;
	m_erros(0,1);
}

public void onBulletMissed(BulletMissedEvent event) {
		tiro_ac = false;
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
}