package arsw.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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

    @Override
    public void run() {
        CountDownLatch latch = new CountDownLatch(1);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(() -> {
            if (paso < carril.size()) {
                carril.setPasoOn(paso++);
                carril.displayPasos(paso);

                if (paso == carril.size()) {
                    carril.finish();
                    int ubicacion = regl.registrarLlegada(this.getName());
                    logger.log(Level.INFO, "El galgo {0} llegó en la posición {1}", new Object[]{this.getName(), ubicacion});
                    latch.countDown();
                    executor.shutdown();
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "Interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
