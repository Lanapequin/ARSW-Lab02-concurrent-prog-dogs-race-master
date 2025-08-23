package arsw.threads;

import lombok.Getter;
import lombok.Setter;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Un carril del canodromo
 * 
 * @author rlopez
 * @author LePeanutButter
 * @author Lanapequin
 * 
 */

@Getter
@Setter
public class Carril {
	private Color on = Color.CYAN;
    private static final Color off = Color.LIGHT_GRAY;
	private Color stop = Color.red;
	private static final Color start = Color.GREEN;
	/**
	 * Pasos del carril
	 */
	private final JButton[] paso;

	/**
	 * Bandera de llegada del carril
	 */
	private final JButton llegada;

	private final String name;

	/**
	 * Construye un carril
	 * 
	 * @param nPasos
	 *            Número de pasos del carril
	 * @param name
	 *            Nombre del carril
	 */
	public Carril(int nPasos, String name) {
		paso = new JButton[nPasos];
		JButton bTmp;
		for (int k = 0; k < nPasos; k++) {
			bTmp = new JButton();
			bTmp.setBackground(off);
			paso[k] = bTmp;
		}
		llegada = new JButton(name);
		llegada.setBackground(start);
		this.name = name;
	}

	/**
	 * Tamaño del carril en número de pasos
	 * 
	 * @return
	 */
	public int size() {
		return paso.length;
	}

	public String getName() {
		return llegada.getText();
	}

	/**
	 * Retorna el i-esimo paso del carril
	 * 
	 * @param i
	 * @return
	 */
	public JButton getPaso(int i) {
		return paso[i];
	}

	/**
	 * Indica que el paso i ha sido utilizado
	 * 
	 * @param i
	 */
	public void setPasoOn(int i) {
		paso[i].setText("o");
	}

	/**
	 * Indica que el paso i no ha sido utilizado
	 * 
	 * @param i
	 */
	public void setPasoOff(int i) {
		paso[i].setText("");
	}

	/**
	 * Indica que se ha llegado al final del carril
	 */
	public void finish() {
		llegada.setText("!");
	}

	public void displayPasos(int n) {
		llegada.setText("" + n);
	}

	/**
	 * Reinicia el carril: ningun paso se ha usado, la bandera abajo.
	 */
	public void reStart() {
		for (int k = 0; k < paso.length; k++) {
			paso[k].setBackground(off);
		}
		llegada.setBackground(start);
		llegada.setText(name);
	}
}
