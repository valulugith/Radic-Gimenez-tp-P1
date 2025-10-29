package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Zombies {
	// Variables del Zombie
	double x, y;
	double velocidad;
	Image imagen;
	Entorno e;
	
	public Zombies(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.velocidad = 0.5; // se ajusta esto para que vayan más rápido o lento
		this.imagen = Herramientas.cargarImagen("zombie.png"); 
	}
	
	public void dibujar() {
		// Dibuja al zombie. 
		e.dibujarImagen(imagen, x, y, 0, 0.1); 
	}
	
	// El método: "avanzar hacia la izquierda"
	public void mover() {
		this.x -= this.velocidad; // Le resta a 'x' para que se mueva a la izquierda
	}
}