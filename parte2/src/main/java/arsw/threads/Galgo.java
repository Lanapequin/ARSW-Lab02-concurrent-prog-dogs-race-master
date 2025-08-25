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
    private volatile boolean paused = false;
	RegistroLlegada regl;
    Logger logger = Logger.getLogger(getClass().getName());

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        boolean finished = false;

        while (!finished && !Thread.currentThread().isInterrupted()) {
            paused();

            if (paso < carril.size()) {
                carril.setPasoOn(paso++);
                carril.displayPasos(paso);

                if (paso == carril.size()) {
                    carril.finish();
                    int ubicacion = regl.registrarLlegada(this.getName());
                    logger.log(Level.INFO, "El galgo {0} llegó en la posición {1}", new Object[]{this.getName(), ubicacion});
                    finished = true;
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "Interrupted", e);
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
        synchronized (this) {
            notifyAll();
        }
    }

    private void paused() {
        synchronized (this) {
            while (paused) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
