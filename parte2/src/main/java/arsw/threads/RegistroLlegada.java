package arsw.threads;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author rlopez
 * @author LePeanutButter
 * @author Lanapequin
 *
 * @version August 24, 2025
 */

@Getter
@Setter
public class RegistroLlegada {

	private int ultimaPosicionAlcanzada=1;

	private String ganador=null;

	public synchronized int registrarLlegada(String nombreGalgo) {
		int posicion = ultimaPosicionAlcanzada;
		ultimaPosicionAlcanzada++;
		if (posicion == 1) {
			ganador = nombreGalgo;
		}
		return posicion;
	}

    public void reset() {
        this.ultimaPosicionAlcanzada = 1;
        this.ganador = null;
    }
}
