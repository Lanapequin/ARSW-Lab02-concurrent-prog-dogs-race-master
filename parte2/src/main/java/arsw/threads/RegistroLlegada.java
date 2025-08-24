package arsw.threads;

import lombok.Getter;
import lombok.Setter;

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

}
