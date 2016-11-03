public class Puerta extends Superficie {

	private Habitacion h1, h2;

	public Puerta(int x, int y, int largo, int alto, Habitacion h1, Habitacion h2) {
		super(x, y, largo, alto);
		this.h1 = h1;
		this.h2 = h2;
	}

	public Habitacion getH1() {
		return h1;
	}

	public void setH1(Habitacion h1) {
		this.h1 = h1;
	}

	public Habitacion getH2() {
		return h2;
	}

	public void setH2(Habitacion h2) {
		this.h2 = h2;
	}

}
