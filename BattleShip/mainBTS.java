public class mainBTS {
    static java.util.Random generator = new java.util.Random();

    public void modocaza(int num, int x, int y, Tablero t, char valorcitox, int cont, boolean[][] matriz) {
        char valor;
            System.out.println("Modo caza activado");

            if (valorcitox == 'D') {
                if (x + 1 < 11 && matriz[x + 1][y] == false) {
                    valor = t.disparo(x + 1, y);
                    matriz[x + 1][y] = true;
                    if (valor == 'D') {
                        System.out.println("barco D destruido");
                        return;
                    }
                }
                if (x - 1 > 0 && matriz[x - 1][y] == false) {
                    valor = t.disparo(x - 1, y);
                    matriz[x - 1][y] = true;
                    if (valor == 'D') {
                        System.out.println("barco D destruido");
                        return;
                    }
                }
                if (y + 1 < 11 && matriz[x][y + 1] == false) {
                    valor = t.disparo(x, y + 1);
                    matriz[x][y + 1] = true;
                    if (valor == 'D') {
                        System.out.println("barco D destruido");
                        return;
                    }
                }
                if (y - 1 > 0 && matriz[x][y - 1] == false) {
                    valor = t.disparo(x, y - 1);
                    matriz[x][y - 1] = true;
                    if (valor == 'D') {
                        System.out.println("barco D destruido");
                        return;
                    }
                }

            }

                if (x + 1 < 11 && matriz[x + 1][y] == false) {
                    valor = t.disparo(x + 1, y);
                    matriz[x + 1][y] = true;
                    if (valor == valorcitox) {
                        cont++;
                        if (num == cont) {
                            System.out.println("barco " + valorcitox + " destruido");
                            return;
                        }
                        modocaza(num, x+1, y, t, valorcitox, cont, matriz);
                    }
                }
                if (x - 1 > 0 && matriz[x - 1][y] == false) {
                    valor = t.disparo(x - 1, y);
                    matriz[x - 1][y] = true;
                    if (valor == valorcitox) {
                        cont++;
                        if (num == cont) {
                            System.out.println("barco " + valorcitox + " destruido");
                            return;
                        }
                        modocaza(num, x-1, y, t, valorcitox, cont, matriz);
                    }
                }
                if (y + 1 < 11 && matriz[x][y + 1] == false) {
                    valor = t.disparo(x, y + 1);
                    matriz[x][y + 1] = true;
                    if (valor == valorcitox) {
                        cont++;
                        if (num == cont) {
                            System.out.println("barco " + valorcitox + " destruido");
                            return;
                        }
                        modocaza(num, x, y+1, t, valorcitox, cont, matriz);
                    }
                }
                if (y - 1 > 0 && matriz[x][y - 1] == false) {
                    valor = t.disparo(x, y - 1);
                    matriz[x][y - 1] = true;
                    if (valor == valorcitox) {
                        cont++;
                        if (num == cont) {
                            System.out.println("barco " + valorcitox + " destruido");
                            return;
                        }
                        modocaza(num, x, y-1, t, valorcitox, cont, matriz);
                    }
            }


    }

    public static void main(String[] args) {
        Tablero t = new Tablero(10);
        t.Imprimir();
        mainBTS instancia = new mainBTS();

        boolean[][] matriz = new boolean[11][11];
        int x, y;

        while (t.ganar() == 0) {
            do {
                x = 1 + generator.nextInt(10);
                y = 1 + generator.nextInt(10);
            } while (matriz[x][y] == true);
            matriz[x][y] = true;

            char valorcito;
            valorcito = t.disparo(x, y);

            System.out.println("Disparo en " + x + " " + y + " " + valorcito);

            if (valorcito == 'D') {
                instancia.modocaza(2, x, y, t, valorcito, 1, matriz);
            }
            if (valorcito == 'A') {
                instancia.modocaza(5, x, y, t, valorcito, 1, matriz);
            }
            if (valorcito == 'B') {
                instancia.modocaza(4, x, y, t, valorcito, 1, matriz);
            }
            if (valorcito == 'C') {
                instancia.modocaza(3, x, y, t, valorcito, 1, matriz);
            }
            if (valorcito == 'S') {
                instancia.modocaza(3, x, y, t, valorcito, 1, matriz);
            }
            t.Imprimir();

        }
        System.out.println("Ganaste en " + t.ganar() + " intentos");
    }
}
