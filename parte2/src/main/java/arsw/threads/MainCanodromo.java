package arsw.threads;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class MainCanodromo {
    private static final RegistroLlegada reg = new RegistroLlegada();
    static Logger logger = Logger.getLogger(MainCanodromo.class.getName());

    public static void main(String[] args) {
        Canodromo can;
        Galgo[] galgos;
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        can.setStartAction(
    e -> {
                ((JButton) e.getSource()).setEnabled(false);
                new Thread(() -> {
                    int numeroCarriles = can.getNumCarriles();
                    for (int i = 0; i < numeroCarriles; i++) {
                        galgos[i] = new Galgo(can.getCarril(i), "" + i, reg);
                        galgos[i].start();
                    }

                    for (int i = 0; i < numeroCarriles; i++) {
                        try {
                            galgos[i].join();
                        } catch (InterruptedException e1) {
                            logger.log(Level.WARNING, "Interrupted", e1);
                            Thread.currentThread().interrupt();
                        }
                    }

                    can.winnerDialog(reg.getGanador(),reg.getUltimaPosicionAlcanzada() - 1);
                    logger.log(Level.INFO, "El ganador fue: {0}", reg.getGanador());
                }).start();
            }
        );

        can.setStopAction(
    e -> logger.info("Carrera pausada!")
        );

        can.setContinueAction(
    e -> logger.info("Carrera reanudada!")
        );
    }
}
