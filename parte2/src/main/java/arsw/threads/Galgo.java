package arsw.threads;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * @author LePeanutButter
 * @author Lanapequin
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private final Carril carril;
	RegistroLlegada regl;
    Logger logger = Logger.getLogger(getClass().getName());

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {			
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			
			if (paso == carril.size()) {						
				carril.finish();
				int ubicacion=regl.getUltimaPosicionAlcanzada();
				regl.setUltimaPosicionAlcanzada(ubicacion+1);
                logger.log(Level.INFO, "El galgo {0} llego en la posicion {1}", new Object[]{this.getName(), ubicacion});
				if (ubicacion==1){
					regl.setGanador(this.getName());
				}
				
			}
		}
	}

	@Override
	public void run() {
		try {
			corra();
		} catch (InterruptedException e) {
            logger.log(Level.WARNING, "Interrupted", e);
            Thread.currentThread().interrupt();
		}
	}
}
