package arsw.threads;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

/**
 * Interfaz de usuario y modelo para un Canodromo
 * 
 * @author rlopez
 * 
 */
public class Canodromo extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Carriles del canodromo
	 */
    private final transient Carril[] carril;

	private final JButton butStart = new JButton("Start");
	private final JButton butStop = new JButton("Stop");
	private final JButton butContinue = new JButton("Continue");
	/**
	 * Constructor
	 * 
	 * @param nCarriles
	 *            Número de carriles
	 * @param longPista
	 *            Longitud de la pista
	 */
	public Canodromo(int nCarriles, int longPista) {
		carril = new Carril[nCarriles];
		for (int i = 0; i < carril.length; i++) {
			carril[i] = new Carril(longPista, "" + i);
		}

		JPanel cont = (JPanel) getContentPane();
		cont.setLayout(new BorderLayout());

		JPanel panPistas = new JPanel();
		panPistas.setLayout(new GridLayout(nCarriles, 1));

		JPanel panCarril;
		BorderLayout bLay;
		GridLayout gridTrack = new GridLayout(1, longPista);
		for (int row = 0; row < nCarriles; row++) {
			bLay = new BorderLayout();
			panCarril = new JPanel();
			panCarril.setLayout(bLay);
			JPanel panTrack = new JPanel();
			panTrack.setLayout(gridTrack);

			for (int col = 0; col < longPista; col++) {
				panTrack.add(carril[row].getPaso(col));
			}
			panCarril.add(panTrack, BorderLayout.CENTER);
			panCarril.add(carril[row].getLlegada(), BorderLayout.EAST);
			panPistas.add(panCarril);
		}

		panPistas.setBorder(new EmptyBorder(new Insets(5, 0, 5, 0)));

		int butWidht = 8;
		int butHeight = 20;
		cont.add(panPistas, BorderLayout.NORTH);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout());
		butPanel.add(butStart);
		butPanel.add(butStop);
		butPanel.add(butContinue);
		cont.add(butPanel, BorderLayout.SOUTH);

		this.setSize(butWidht * longPista, butHeight * nCarriles + 400);

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Determine the new location of the window
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		// Move the window
		this.setLocation(x, y);
		this.setTitle("Canodromo");

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Reinicia cada uno de los carriles
	 */
	public void restart() {
        for (Carril value : carril) {
            value.reStart();
        }
	}

    /**
     * Devuelve el carril correspondiente al índice especificado.
     * Permite acceder a un carril en particular dentro del arreglo de carriles.
     * Se asume que el índice proporcionado está dentro del rango válido.
     *
     * @param i Índice del carril que se desea obtener.
     * @return El objeto {@code Carril} ubicado en la posición {@code i}.
     */
	public Carril getCarril(int i) {
		return carril[i];
	}

	public int getNumCarriles() {
		return carril.length;
	}

    /**
     * Asocia una acción al botón de inicio de la carrera.
     * Permite establecer el comportamiento que se ejecutará cuando el usuario
     * presione el botón "Start". La acción debe implementarse mediante un {@link ActionListener}.
     *
     * @param action El {@code ActionListener} que define la acción a ejecutar al hacer clic en el botón.
     */
	public void setStartAction(ActionListener action) {
		butStart.addActionListener(action);
	}

    /**
     * Asocia una acción al botón de detención de la carrera.
     * Permite definir el comportamiento que se ejecutará cuando el usuario
     * presione el botón "Stop". La acción debe estar implementada mediante un {@link ActionListener}.
     *
     * @param action El {@code ActionListener} que especifica la lógica a ejecutar al hacer clic en el botón de detener.
     */
	public void setStopAction(ActionListener action) {
		butStop.addActionListener(action);
	}

    /**
     * Asocia una acción al botón de continuar la carrera.
     * Permite definir el comportamiento que se ejecutará cuando el usuario
     * presione el botón "Continue". La acción debe estar implementada mediante un {@link ActionListener}.
     *
     * @param action El {@code ActionListener} que especifica la lógica a ejecutar al hacer clic en el botón de continuar.
     */
	public void setContinueAction(ActionListener action){
		butContinue.addActionListener(action);
	}
	
	public void winnerDialog(String winner,int total) {
        JOptionPane.showMessageDialog(null, "El ganador fue:" + winner + " de un total de " + total);
    }
}
