package arsw.threads;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class MainCanodromo {
    private static final RegistroLlegada reg = new RegistroLlegada();
    private static final int NUMERO_CARRILES = 17;
    static Logger logger = Logger.getLogger(MainCanodromo.class.getName());

    private static final Canodromo can = new Canodromo(NUMERO_CARRILES, 100);
    private static final Galgo[] galgos = new Galgo[NUMERO_CARRILES];

    public static void main(String[] args) {
        can.setVisible(true);

        start();
        pause();
        unpause();
        restart();
    }

    private static void start() {
        can.setStartAction(e -> {
            ((JButton) e.getSource()).setEnabled(false);
            new Thread(() -> {
                for (int i = 0; i < NUMERO_CARRILES; i++) {
                    galgos[i] = new Galgo(can.getCarril(i), "" + i, reg);
                    galgos[i].start();
                }

                for (int i = 0; i < NUMERO_CARRILES; i++) {
                    try {
                        galgos[i].join();
                    } catch (InterruptedException e1) {
                        logger.log(Level.WARNING, "Interrupted!", e1);
                        Thread.currentThread().interrupt();
                    }
                }

                can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                logger.log(Level.INFO, "El ganador fue: {0}", reg.getGanador());
            }).start();
        });
    }

    private static void pause() {
        can.setStopAction(e ->
            new Thread(() -> {
                synchronized (galgos) {
                    for (Galgo g : galgos) {
                        g.pause();
                    }
                }
                logger.info("Carrera pausada!");
            }).start()
        );
    }

    private static void unpause() {
        can.setContinueAction(e ->
            new Thread(() -> {
                synchronized (galgos) {
                    for (Galgo g : galgos) {
                        g.unpause();
                    }
                }

                logger.info("Carrera reanudada!");
            }).start()
        );
    }

    private static void restart() {
        can.setRestartAction(e ->
            new Thread(() -> {
                for (int i = 0; i < NUMERO_CARRILES; i++) {
                    can.getCarril(i).reStart();
                }

                SwingUtilities.invokeLater(() -> can.getButStart().setEnabled(true));
                logger.info("Carrera reiniciada!");
            }).start()
        );
    }
}
