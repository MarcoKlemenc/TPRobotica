public class Baldosa extends Superficie {

	private int fila, columna;
	private boolean pasar = true;
	private boolean conecta = false;

	public Baldosa(int x, int y, int largo, int alto, int fila, int columna) {
		super(x, y, largo, alto);
		this.fila = fila;
		this.columna = columna;
	}

	public boolean isPasar() {
		return pasar;
	}

	public void cambiarPasar() {
		this.pasar ^= true;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public boolean isConecta() {
		return conecta;
	}

	public void setConecta(boolean conecta) {
		this.conecta = conecta;
	}

}