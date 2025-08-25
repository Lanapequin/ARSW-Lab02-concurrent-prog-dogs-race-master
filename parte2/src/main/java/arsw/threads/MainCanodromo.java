package arsw.threads;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;


/**
 *
 * @author rlopez
 * @author LePeanutButter
 * @author Lanapequin
 *
 * @version August 24, 2025
 */
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
            logger.info("Carrera iniciada!");
            can.getButStop().setEnabled(true);
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
                SwingUtilities.invokeLater(() -> {
                    can.getButRestart().setEnabled(true);
                    can.getButStop().setEnabled(false);
                });
            }).start();
        });
    }

    private static void pause() {
        can.setStopAction(e -> {
            ((JButton) e.getSource()).setEnabled(false);
            new Thread(() -> {
                synchronized (galgos) {
                    for (Galgo g : galgos) {
                        g.pause();
                    }
                }
                logger.info("Carrera pausada!");
                SwingUtilities.invokeLater(() -> {
                    can.getButRestart().setEnabled(true);
                    can.getButContinue().setEnabled(true);
                });
            }).start();
        });
    }

    private static void unpause() {
        can.setContinueAction(e -> {
            ((JButton) e.getSource()).setEnabled(false);
            can.getButRestart().setEnabled(false);
            new Thread(() -> {
                synchronized (galgos) {
                    for (Galgo g : galgos) {
                        g.unpause();
                    }
                }

                logger.info("Carrera reanudada!");
                SwingUtilities.invokeLater(() -> can.getButStop().setEnabled(true));
            }).start();
        });
    }

    private static void restart() {
        can.setRestartAction(e -> {
            ((JButton) e.getSource()).setEnabled(false);
            can.getButContinue().setEnabled(false);
            can.getButStop().setEnabled(false);

            new Thread(() -> {
                reg.reset();

                for (int i = 0; i < NUMERO_CARRILES; i++) {
                    can.getCarril(i).reStart();
                    galgos[i] = null;
                }

                logger.info("Carrera reiniciada!");
                SwingUtilities.invokeLater(() -> can.getButStart().setEnabled(true));
            }).start();
        });
    }
}
