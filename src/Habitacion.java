import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Habitacion extends Componente {

	private static int idActual = 1;
	private int id, lado;
	private List<Baldosa> baldosas;

	public Habitacion(int x, int y, int largo, int alto, int lado) {
		super(x, y, largo, alto);
		this.lado = lado;
		id = idActual++;
	}

	public boolean contieneB(Baldosa b) {
		for (Baldosa b1 : baldosas) {
			if (b == b1) {
				return true;
			}
		}
		return false;
	}

	public Baldosa contieneB(MouseEvent e) {
		for (Baldosa b : baldosas) {
			if (b.contiene(e.getX(), e.getY())) {
				return b;
			}
		}
		return null;
	}

	public List<Point> getNoPasar() {
		List<Point> noPasar = new ArrayList<Point>();
		if (baldosas != null) {
			for (Baldosa b : baldosas) {
				if (!b.isPasar()) {
					noPasar.add(b.getCoordenadas());
				}
			}
		}
		return noPasar;
	}

	public boolean intersecta(Habitacion h) {
		if (equals(h)) {
			return false;
		} else {
			Area a = new Area(getColision());
			a.intersect(new Area(h.getColision()));
			return !a.isEmpty();
		}
	}

	public boolean intersecta(Rectangle2D r) {
		Area a = new Area(getColision());
		a.intersect(new Area(r));
		return !a.isEmpty();
	}

	Rectangle2D getColision() {
		return new Rectangle2D.Float(x, y, largo + 1, alto + 1);
	}

	public Rectangle2D bordeIzq() {
		return new Rectangle2D.Float(x, y, 1, alto);
	}

	public Rectangle2D bordeDer() {
		return new Rectangle2D.Float(x + largo, y, 1, alto);
	}

	public Rectangle2D bordeArr() {
		return new Rectangle2D.Float(x, y, largo, 1);
	}

	public Rectangle2D bordeAba() {
		return new Rectangle2D.Float(x, y + alto, largo, 1);
	}

	public void generarBaldosas() {
		List<Point> noPasar = getNoPasar();
		this.baldosas = new ArrayList<Baldosa>();
		int columna = 1;
		for (int i = x; i < x + largo; i += lado) {
			int fila = 1;
			for (int j = y; j < y + alto; j += lado) {
				Baldosa b = new Baldosa(i, j, Math.min(x + largo - i, lado), Math.min(y + alto - j, lado), fila,
						columna);
				if (noPasar.contains(b.getCoordenadas())) {
					b.cambiarPasar();
				}
				baldosas.add(b);
				fila++;
			}
			columna++;
		}
	}

	public Baldosa obtenerBaldosa(double fila, double columna) {
		for (Baldosa b : this.baldosas) {
			if (b.getFila() == fila && b.getColumna() == columna) {
				return b;
			}
		}
		return null;
	}

	public Trayectoria generarTrayectoria(int filaA, int columnaA, int filaB, int columnaB) {
		Trayectoria t = new Trayectoria();
		while (filaA != filaB || columnaA != columnaB) {
			Baldosa b = obtenerBaldosa(filaA, columnaA);
			if (b.isPasar()) {
				t.agregarBaldosa(this, filaA, columnaA);
				if (Math.abs(filaA - filaB) > Math.abs(columnaA - columnaB)) {
					filaA -= Math.signum(filaA - filaB);
				} else if (Math.abs(filaA - filaB) < Math.abs(columnaA - columnaB)) {
					columnaA -= Math.signum(columnaA - columnaB);
				} else {
					filaA -= Math.signum(filaA - filaB);
					columnaA -= Math.signum(columnaA - columnaB);
				}
			} else {
				if (obtenerBaldosa(filaA, columnaA - 1).isPasar()) {
					while (obtenerBaldosa(filaA, columnaA - 1).isPasar()
							&& !obtenerBaldosa(filaA, columnaA).isPasar()) {
						t.agregarBaldosa(this, filaA, columnaA - 1);
						filaA--;
					}
				} else if (obtenerBaldosa(filaA - 1, columnaA).isPasar()) {
					while (obtenerBaldosa(filaA - 1, columnaA).isPasar()
							&& !obtenerBaldosa(filaA, columnaA).isPasar()) {
						t.agregarBaldosa(this, filaA - 1, columnaA);
						columnaA++;
					}
				} else if (obtenerBaldosa(filaA, columnaA + 1).isPasar()) {
					while (obtenerBaldosa(filaA, columnaA + 1).isPasar()
							&& !obtenerBaldosa(filaA, columnaA).isPasar()) {
						t.agregarBaldosa(this, filaA, columnaA + 1);
						filaA++;
					}
				} else if (obtenerBaldosa(filaA + 1, columnaA).isPasar()) {
					while (obtenerBaldosa(filaA + 1, columnaA).isPasar()
							&& !obtenerBaldosa(filaA, columnaA).isPasar()) {
						t.agregarBaldosa(this, filaA + 1, columnaA);
						columnaA--;
					}
				}
			}
		}
		t.agregarBaldosa(this, filaA, columnaA);
		return t;
	}

	public List<Baldosa> getBaldosas() {
		return baldosas;
	}

	public int getLado() {
		return lado;
	}

	public void setLado(int lado) {
		this.lado = lado;
	}

	public int getId() {
		return id;
	}

	public static void setIdActual(int idActual) {
		Habitacion.idActual = idActual;
	}

	public String toString() {
		return id + "|" + x + "|" + y + "|" + largo + "|" + alto + "|" + lado + "|";
	}

}
