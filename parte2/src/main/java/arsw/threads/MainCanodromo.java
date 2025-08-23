package arsw.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;

public class MainCanodromo {
    private static Galgo[] galgos;
    private static Canodromo can;
    private static final RegistroLlegada reg = new RegistroLlegada();
    static Logger logger = Logger.getLogger(MainCanodromo.class.getName());

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        can.setStartAction(
                new ActionListener() {

                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        ((JButton) e.getSource()).setEnabled(false);
                        new Thread() {
                            @Override
                            public void run() {
                                int numeroCarriles = can.getNumCarriles();
                                for (int i = 0; i < numeroCarriles; i++) {
                                    galgos[i] = new Galgo(can.getCarril(i), "" + i, reg);
                                    galgos[i].start();
                                }

                                for (int i = 0; i < numeroCarriles; i++) {
                                    try {
                                        galgos[i].join();
                                    } catch (InterruptedException e) {
                                        logger.log(Level.WARNING, "Interrupted", e);
                                        Thread.currentThread().interrupt();
                                    }
                                }


				                can.winnerDialog(reg.getGanador(),reg.getUltimaPosicionAlcanzada() - 1);
                                logger.log(Level.INFO, "El ganador fue: {0}", reg.getGanador());
                            }
                        }.start();
                    }
                }
        );

        can.setStopAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        logger.info("Carrera pausada!");
                    }
                }
        );

        can.setContinueAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        logger.info("Carrera reanudada!");
                    }
                }
        );
    }
}
