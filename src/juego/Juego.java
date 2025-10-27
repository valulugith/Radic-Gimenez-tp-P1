package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Cuadricula cua;
	Regalo[] regalos;
	Planta[] plantas;
	// Variables y métodos propios de cada grupo
	Bala[] balas;
	RoseBlade[] roseblades;
	WallNut[] wallnuts;
   

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		cua=new Cuadricula(50,150,entorno);
		regalos=new Regalo[5];
		for(int i=0; i < 5; i++) {
			regalos[i]=new Regalo(50, 150+i*100,entorno);
		}
		plantas=new Planta[15];
		plantas[0]=new Planta(50,50,entorno);		
		// Inicializar lo que haga falta para el juego
		this.balas = new Bala[30];

		// Inicia el juego!
		this.entorno.iniciar();
		
		this.roseblades = new RoseBlade[10];
		this.wallnuts = new WallNut[10];

		// Crea una carta de cada tipo arriba del todo
		this.roseblades[0] = new RoseBlade(100, 50, entorno);
		this.wallnuts[0] = new WallNut(200, 50, entorno);
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		cua.dibujar();
		for(Regalo r:this.regalos) {
			r.dibujar();
		
			// Dibujar RoseBlades
		
			for (int i = 0; i < roseblades.length; i++) {
			    if (roseblades[i] != null) {
			        roseblades[i].dibujar();
			        if (roseblades[i].plantada && entorno.numeroDeTick() % 60 == 0) {
			            roseblades[i].disparar(balas, entorno);
			        }
			    }
			}
			
			//bara de menu
			entorno.dibujarRectangulo(400, 75, 400, 150, 0, new Color(139, 69, 19));

			// Dibujar WallNuts
		
			for (int i = 0; i < wallnuts.length; i++) {
			    if (wallnuts[i] != null) {
			        wallnuts[i].dibujar();
			    }
			}

			// Dibujar y mover balas
	
			for (int i = 0; i < balas.length; i++) {
			    if (balas[i] != null) {
			        balas[i].mover();
			        balas[i].dibujar();

			        if (balas[i].x > 800) {
			            balas[i] = null;
			        }
			    }
			}


		}

		for(int ite=0;ite < plantas.length;ite ++ ) {
			if(plantas[ite] != null) {
				plantas[ite].dibujar();
			}
		}

		if(entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
			//Para las plantas
			for(int ite=0; ite < plantas.length; ite ++) {
				if(plantas[ite] != null) {
					if(plantas[ite].encima(entorno.mouseX(),entorno.mouseY())){
						plantas[ite].seleccionada=true;
					}
					else {
						plantas[ite].seleccionada=false;
					}
				}
			}
			//para las papas
			for(int ite=0; ite < wallnuts.length; ite ++) { 
				if(wallnuts[ite] != null) {
					
					//si NO está plantada se puede
					if(wallnuts[ite].encima(entorno.mouseX(),entorno.mouseY()) && !wallnuts[ite].plantada){ 
						wallnuts[ite].seleccionada=true;
					} else {
						wallnuts[ite].seleccionada=false;
					}
				}
			}
		}


		if(entorno.estaPresionado(entorno.BOTON_IZQUIERDO)) {
			//para las plantas
			for(int ite=0; ite < plantas.length;ite++) {

				if(plantas[ite] != null && plantas[ite].seleccionada) {
					int indiceX = cua.cercano(entorno.mouseX(), entorno.mouseY()).x;
					int indiceY = cua.cercano(entorno.mouseX(), entorno.mouseY()).y;
					plantas[ite].arrastrar(entorno.mouseX(), entorno.mouseY());
					cua.ocupado[indiceX][indiceY] = false;
				}
			}
			//para las papas
			for(int ite=0; ite < wallnuts.length;ite++) {
				if(wallnuts[ite] != null && wallnuts[ite].seleccionada) {
					wallnuts[ite].arrastrar(entorno.mouseX(), entorno.mouseY());
				}
			}
		}


		if(entorno.seLevantoBoton(entorno.BOTON_IZQUIERDO)) {
			//para las plantas
			for(int ite=0; ite < this.plantas.length;ite++) {
				if (plantas[ite] != null) {
					if(plantas[ite].seleccionada) {
						plantas[ite].seleccionada = false; //si la planta no esta seleccionada no la muestra
						if(entorno.mouseY() < 70 && !plantas[ite].plantada ) {
							plantas[ite].arrastrar(50, 50);

						}else {
							int indiceX = cua.cercanoL(entorno.mouseX(), entorno.mouseY()).x;
							int indiceY = cua.cercanoL(entorno.mouseX(), entorno.mouseY()).y;
							if(cua.ocupado[indiceX][indiceY]) {
								return;
							}
							plantas[ite].arrastrar(cua.corX[indiceX],cua.corY[indiceY]);
							cua.ocupado[indiceX][indiceY] = true;
							plantas[ite].plantada = true;
						}
					}

				}
			}
			//para las papas
			for(int ite=0; ite < this.wallnuts.length;ite++) {
				if (wallnuts[ite] != null && wallnuts[ite].seleccionada) {
					wallnuts[ite].seleccionada = false; 
					
					// Copia la misma lógica de plantar que usaste para 'plantas'
					// pero ajustando la posición original de la papa
					if(entorno.mouseY() < 70 && !wallnuts[ite].plantada ) {
						wallnuts[ite].arrastrar(200, 50); // Posición original de la papa

					}else {
						int indiceX = cua.cercanoL(entorno.mouseX(), entorno.mouseY()).x;
						int indiceY = cua.cercanoL(entorno.mouseX(), entorno.mouseY()).y;
				
						
						// 2. Verificamos si está ocupado
						boolean estaOcupado = cua.ocupado[indiceX][indiceY];

						if (!estaOcupado) {
							// ¡Se puede plantar!
							wallnuts[ite].arrastrar(cua.corX[indiceX],cua.corY[indiceY]);
							cua.ocupado[indiceX][indiceY] = true;
							wallnuts[ite].plantada = true;
							
						} else {
							// No se puede plantar (es verde O está ocupado)
							// La devolvemos a la barra
							wallnuts[ite].arrastrar(200, 50);
						}
						// ***************************************
					}
				}
			}
		}


		for(int ite=0; ite < plantas.length;ite++) {
			if(plantas[ite] != null) {
				if(entorno.sePresiono(entorno.TECLA_ARRIBA)){

					if(this.plantas[ite].seleccionada && this.plantas[ite].plantada) {
						int indiceX = cua.cercano(plantas[ite].x, plantas[ite].y).x;
						int indiceY = cua.cercano(plantas[ite].x, plantas[ite].y).y;
						if(indiceY >= 1 && !this.cua.ocupado[indiceX][indiceY-1]) {

							this.cua.ocupado[indiceX][indiceY] = false;
							this.cua.ocupado[indiceX][indiceY-1] = true;
							this.plantas[ite].y -= 100 ;
						}
					}
				}

				if(entorno.sePresiono(entorno.TECLA_ABAJO)){
					if(this.plantas[ite].seleccionada && this.plantas[ite].plantada ) {
						int indiceX = cua.cercano(plantas[ite].x, plantas[ite].y).x;
						int indiceY = cua.cercano(plantas[ite].x, plantas[ite].y).y;
						if(indiceY <= 3 && !this.cua.ocupado[indiceX][indiceY+1]) {

							this.cua.ocupado[indiceX][indiceY] = false;
							this.cua.ocupado[indiceX][indiceY+1] = true;
							this.plantas[ite].y += 100 ;
						}
					}	
				}

				if(entorno.sePresiono(entorno.TECLA_DERECHA)) {
					if(this.plantas[ite].seleccionada && this.plantas[ite].plantada) {
						int indiceX = cua.cercano(plantas[ite].x, plantas[ite].y).x;
						int indiceY = cua.cercano(plantas[ite].x, plantas[ite].y).y;
						if(indiceX <= 6 && !this.cua.ocupado[indiceX+1][indiceY]) {

							this.cua.ocupado[indiceX][indiceY] = false;
							this.cua.ocupado[indiceX+1][indiceY] = true;
							this.plantas[ite].x += 100 ;
						}

					}
				}

				if(entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
					if(this.plantas[ite].seleccionada && this.plantas[ite].plantada) {
						int indiceX = cua.cercano(plantas[ite].x, plantas[ite].y).x; 
						int indiceY = cua.cercano(plantas[ite].x, plantas[ite].y).y; 
						if(indiceX > 1 && !this.cua.ocupado[indiceX-1][indiceY]) { 

							this.cua.ocupado[indiceX][indiceY] = false;
							this.cua.ocupado[indiceX-1][indiceY] = true;
							this.plantas[ite].x -= 100 ;
						}
					}	
				}
			}
		}
		// Cada 60 ticks (≈1 segundo), cada planta plantada dispara una bala
		if (entorno.numeroDeTick() % 60 == 0) {
		    for (int i = 0; i < plantas.length; i++) {
		        if (plantas[i] != null && plantas[i].plantada) {
		            for (int j = 0; j < balas.length; j++) {
		                if (balas[j] == null) { // hay lugar libre en el arreglo
		                    balas[j] = new Bala(plantas[i].x + 30, plantas[i].y, entorno);
		                    break;
		                }
		            }
		        }
		    }
		}

		// MOVER Y DIBUJAR BALAS

		for (int i = 0; i < balas.length; i++) {
		    if (balas[i] != null) {
		        balas[i].mover();
		        balas[i].dibujar();

		        //  Si sale del mapa, eliminarla
		        if (balas[i].x > 800) {
		            balas[i] = null;
		        }
		    }
		}
		//para que cree las plantas y papas
		//llamo los metodos de plantas
		if(!plantasNoPlantadas(this.plantas)) {
			crearPlanta(this.plantas);
		}
		//llamo los metodos de las papas
		if(!wallnutsNoPlantados(this.wallnuts)) {
			crearWallNut(this.wallnuts);
		}
	}
//plantas
	public boolean plantasNoPlantadas(Planta[] pl) {
		for(Planta p:pl) {
			if(p != null && !p.plantada) {
				return true;
			}
		}
		return false;
	}

	private void crearPlanta(Planta[] pl) {

		for(int x=0; x < pl.length;x++) {
			if(pl[x] == null) {
				pl[x] = new Planta(50,50,entorno);
				return;
			}
		}
	}
//papas
	public boolean wallnutsNoPlantados(WallNut[] wn) {
		for(WallNut w : wn) {
			if(w != null && !w.plantada) {
				return true;
			}
		}
		return false;
	}

	// Copia de 'crearPlanta' pero para WallNut
	private void crearWallNut(WallNut[] wn) {
		for(int x=0; x < wn.length;x++) {
			if(wn[x] == null) {
				// Asegurate de poner la posición original de la papa (200, 50)
				wn[x] = new WallNut(200, 50, entorno); 
				return;
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
