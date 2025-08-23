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
     * Devuelve el tamaño del carril en número de pasos.
     * Representadas por el arreglo {@code paso}.
     *
     * @return El número total de pasos que conforman el carril.
     */
	public int size() {
		return paso.length;
	}

	public String getName() {
		return llegada.getText();
	}

    /**
     * Devuelve el botón correspondiente al i-ésimo paso del carril.
     * Permite acceder a un paso específico del carril, representado como un {@link JButton},
     * según el índice proporcionado.
     *
     * @param i Índice del paso que se desea obtener.
     * @return El {@code JButton} que representa el paso en la posición {@code i}.
     */
	public JButton getPaso(int i) {
		return paso[i];
	}

    /**
     * Marca el i-ésimo paso del carril como utilizado.
     * Establece el texto del botón correspondiente al paso {@code i} como "o",
     * indicando visualmente que dicho paso ha sido ocupado durante la carrera.
     *
     * @param i Índice del paso que se desea activar.
     */
	public void setPasoOn(int i) {
		paso[i].setText("o");
	}

    /**
     * Marca el i-ésimo paso del carril como no utilizado.
     * Limpia el texto del botón correspondiente al paso {@code i},
     * indicando que dicho paso está libre o no ha sido ocupado durante la carrera.
     *
     * @param i Índice del paso que se desea desactivar.
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
        for (JButton jButton : paso) {
            jButton.setBackground(off);
        }
		llegada.setBackground(start);
		llegada.setText(name);
	}
}
