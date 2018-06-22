package Fatec2018;
import robocode.*;

public class G2M_GaviaoArqueiro extends AdvancedRobot
{
boolean GunD = true;
// Controla o número de tiros que não acertaram o alvo, se perderam ou bateram na parede
int erros;
// Controla quantos tiros foram levados, se levar tiros demais ele faz um movimento de fuga
int levou_tiro;

// Faz sempre:
public void run() {
	// Inicializa as variáveis com o valor 0
	erros = 0;
	levou_tiro = 0;
	while(true) {
		// Sempre gira o canhão se não estiver em uma outra função:
		if (GunD == true)
		turnGunRight(180);
		else 
		turnGunLeft(180);
	}
}
// Ao escanear um robô:
public void onScannedRobot(ScannedRobotEvent e) {
	// Verifica se não é alguém do seu time, se sim, retorna:
		String name = e.getName();
		if (name.indexOf("G2M") != -1) {
		return;
		}
	// Verifica se não é alguém do seu time, se sim, retorna:
	if (e.getName() == "G2M_HulkaoEsmaga*" || e.getName() == "G2M_Mercurio*") {
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