package juego;

import entorno.Entorno;
import entorno.Herramientas;

public class WallNut extends Planta {
    int vida;

    public WallNut(double x, double y, Entorno e) {
        super(x, y, e);
        this.imagen = Herramientas.cargarImagen("wallnut.png");
        this.imagenSeleccionada = Herramientas.cargarImagen("wallnutSeleccionada.png");
        this.escala = 0.08;
        this.vida = 3; // más resistente
    }

    public void recibirGolpe() {
        vida--;
        if (vida <= 0) {
            this.plantada = false;
        }
    }
}