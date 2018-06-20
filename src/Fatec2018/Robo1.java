package Robos;
import robocode.*;

public class Robo1 extends AdvancedRobot
{
// Variável para controlar o que fazer quando se iniciar a partida
boolean inicio = true;
// Controla o número de tiros que não acertaram o alvo, se perderam ou bateram na parede
int erros;
// Controla quantos tiros foram levados, se levar tiros demais ele faz um movimento de fuga
int levou_tiro;

// Faz sempre:
public void run() {
	// Controla se é o ínicio da partida e zera as variáveis (estava tendo problemas ao zerar na declaração, ela voltava para o valor declarado e não contava)
	if (inicio == true) {
	// Inicializa as variáveis com o valor 0
	erros = 0;
	levou_tiro = 0;
	}
	while(true) {
		// Sempre gira o canhão se não estiver em uma outra função:
		turnGunRight(180);
	}
}
// Ao escanear um robô:
public void onScannedRobot(ScannedRobotEvent e) {
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
	fire(3);
}

// Ao levar um tiro de um inimigo 
public void onHitByBullet(HitByBulletEvent e) {
// Adicionar 1 a variável levou_tiro
	levou_tiro++;
}

// Quando bater em uma parede...
public void onHitWall(HitWallEvent e) {
	// Quando bater na parede, anda para trás, vira para a esquerda e anda para frente.
	setBack(20);
	setTurnLeft(90);
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
}