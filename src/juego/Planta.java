package juego;
//para el commit de prueba
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Planta {
	double x,y,escala,ancho,alto;
	Image imagen,imagenSeleccionada;
	Entorno e;
	boolean seleccionada;
	boolean plantada;
	public Planta(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.escala = 0.08;
		this.imagen = Herramientas.cargarImagen("plantita.png");
		this.imagenSeleccionada = Herramientas.cargarImagen("plantitaSeleccionada.png");
		this.seleccionada = false;
		this.plantada = false;
	}
	
	public void dibujar() {
		if(seleccionada) {
			e.dibujarImagen(imagenSeleccionada, x, y, 0,escala);
		} else {
			// Si NO está seleccionada, dibujá la imagen normal
			e.dibujarImagen(imagen, x, y, 0, escala);
		}
		
	}
	
	public double distancia(double x1, double y1, double x2, double y2) {
		return Math.sqrt( (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) );
	}
	
	public boolean encima(double xM, double yM) {
		return distancia(xM,yM,this.x,this.y) < 20;
	}
	
	public void arrastrar(double xM, double yM) {
		this.x = xM;
		this.y = yM;
	}
	
	//Para que no dispare si no hay zombeis a la vista
	/**
	 * Revisa el arreglo de zombies para ver si hay alguno en la misma fila.
	 * @param zombies El arreglo completo de zombies del juego.
	 * @return true si hay un zombie en la fila, false si no.
	 */
	public boolean hayZombieEnFila(Zombies[] zombies) {
		for (Zombies z : zombies) {
			// Comprueba que el zombie exista y que su 'y' sea igual al 'y' de esta planta
			if (z != null && z.y == this.y) {
				return true; // ¡Encontró uno! No necesita seguir buscando.
			}
		}
		return false; // No hay ningún zombie en esta fila.
	}
	
}
