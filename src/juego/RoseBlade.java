package juego;

import entorno.Entorno;
import entorno.Herramientas;

public class RoseBlade extends Planta {

    public RoseBlade(double x, double y, Entorno e) {
        super(x, y, e);
        this.imagen = Herramientas.cargarImagen("roseblade.png");
        this.imagenSeleccionada = Herramientas.cargarImagen("roseblade.png");
        this.escala = 0.09;
    }

    public void disparar(Bala[] balas, Entorno e) {
        for (int j = 0; j < balas.length; j++) {
            if (balas[j] == null) {
                balas[j] = new Bala(this.x + 30, this.y, e);
                break;
            }
        }
    }
}