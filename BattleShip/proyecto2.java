import java.util.*;

class Nodo {
    int x;
    int y;
    int costo;
    int heuristica;

    public Nodo(int x, int y, int costo, int heuristica) {
        this.x = x;
        this.y = y;
        this.costo = costo;
        this.heuristica = heuristica;
    }
}

public class proyecto2 {
    private static final Random generator = new Random();

    public void modoCaza(int num, int x, int y, Tablero t, char valorcitox, int cont, boolean[][] matriz) {
       // System.out.println("Modo caza activado");

        int[][] direcciones = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] direccion : direcciones) {
            int newX = x + direccion[0];
            int newY = y + direccion[1];

            while (newX >= 1 && newX <= 10 && newY >= 1 && newY <= 10 && !matriz[newX][newY]) {
                char valor = t.disparo(newX, newY);
                matriz[newX][newY] = true;

                if (valor == valorcitox) {
                    cont++;
                    if (num == cont) {
                       // System.out.println("Barco " + valorcitox + " destruido");
                        return;
                    }

                    x = newX;
                    y = newY;
                } else {
                    break;
                }

                newX = x + direccion[0];
                newY = y + direccion[1];
            }
        }
    }

    private int heuristicaProbabilidad(int x, int y) {
        // Simplemente utiliza una heurística simple basada en la distancia al centro
        int centroX = 5;
        int centroY = 5;
        return Math.abs(x - centroX) + Math.abs(y - centroY);
    }

    public int ejecutarJuego() {
        Tablero t = new Tablero(10);
        
        boolean[][] matriz = new boolean[11][11];
        int intentosMaximos = 100;

        // Fase de Caza
        for (int intento = 1; intento <= intentosMaximos / 2; intento++) {
            int x, y;

            do {
                // Implementamos A* para encontrar la casilla más prometedora
                Nodo mejorNodo = buscarMejorNodoAStar(matriz);
                x = mejorNodo.x;
                y = mejorNodo.y;
            } while (matriz[x][y]);

            matriz[x][y] = true;
            char valorcito = t.disparo(x, y);

           // System.out.println("Disparo en " + x + " " + y + " " + valorcito);

            if (valorcito >= 'A' && valorcito <= 'D') {
                modoCaza(valorcito - 'A' + 1, x, y, t, valorcito, 1, matriz);
            }

            

            if (t.ganar() > 0) {
                //System.out.println(t.ganar() + " " );
                return intento;
            }
        }

        // Fase de Objetivo mejorada con A*
        for (int intento = intentosMaximos / 2 + 1; intento <= intentosMaximos; intento++) {
            int x, y;

            do {
                // Implementamos A* para encontrar la casilla más prometedora
                Nodo mejorNodo = buscarMejorNodoAStar(matriz);
                x = mejorNodo.x;
                y = mejorNodo.y;
            } while (matriz[x][y]);

            matriz[x][y] = true;
            char valorcito = t.disparo(x, y);

           // System.out.println("Disparo en " + x + " " + y + " " + valorcito);

            if (valorcito >= 'A' && valorcito <= 'D') {
                modoCaza(valorcito - 'A' + 1, x, y, t, valorcito, 1, matriz);
            }

        

            if (t.ganar() > 0) {
                //System.out.println(t.ganar() + " " );
                return intento;
            }
        }

        return intentosMaximos;
    }

    private Nodo buscarMejorNodoAStar(boolean[][] matriz) {
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(nodo ->
                nodo.costo + nodo.heuristica));

        int[][] direcciones = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if (!matriz[i][j]) {
                    int heuristica = heuristicaProbabilidad(i, j);
                    colaPrioridad.offer(new Nodo(i, j, 0, heuristica));
                }
            }
        }

        return colaPrioridad.poll();
    }

    public static void main(String[] args) {
        proyecto2 instancia = new proyecto2();
        int totalIntentos = 0;


        // Ejecutamos 100.000 veces
        int numeroDeEjecuciones = 100000;

        for (int ejecucion = 1; ejecucion <= numeroDeEjecuciones; ejecucion++) {
            totalIntentos += instancia.ejecutarJuego();
        }

        double promedioIntentos = (double) totalIntentos / numeroDeEjecuciones;
        System.out.println("Promedio de intentos: " + promedioIntentos);
    }
}
