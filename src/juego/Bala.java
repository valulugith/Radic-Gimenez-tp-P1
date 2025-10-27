package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Bala {
    double x, y, velocidad;
    Image imagen;
    Entorno e;
    boolean activa;

    public Bala(double x, double y, Entorno e) {
        this.x = x;
        this.y = y;
        this.e = e;
        this.velocidad = 2;//
        this.imagen = Herramientas.cargarImagen("bala.png");
        this.activa = true;
    }

    public void mover() {
        x += velocidad;
        if (x > 800) { // si sale de la pantalla
            activa = false;
        }
    }

    public void dibujar() {
        e.dibujarImagen(imagen, x, y, 0, 0.05);
    }
}