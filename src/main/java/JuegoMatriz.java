import javax.imageio.ImageTranscoder;
import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class JuegoMatriz {

    public static void main(String[] args) {
        String[][] mapa = new String[10][10];
        int[] personaje = estadisticasPesonaje(new int[4]);

        cargarMapa(mapa);
        mostrarMapa(mapa);

        boolean jugando = true;

        while (jugando) {
            System.out.println("Mover: AWSD");
            String movimiento = movimiento();
            personaje = movimientoPersonaje(mapa, personaje, movimiento);
            mostrarMapa(mapa);
            jugando = estadoJuego(personaje);
        }
    }

    public static Scanner crearScanner() {
        return new Scanner(System.in);
    }

    public static String movimiento() {
        Scanner scanner = crearScanner();
        try {
            String movimiento = scanner.nextLine();
            return movimiento;
        } catch (InputMismatchException e) {
            System.out.println("error intente nuevamente ");
        }
        return " ";
    }

    public static String[][] cargarMapa(String[][] mapa) {
        inicializarObstaculos(mapa);
        inicializarEventos(mapa);
        return mapa;
    }

    public static void inicializarObstaculos(String[][] mapa) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa.length; j++) {
                mapa[i][j] = ".";
                mapa[i][0] = "#";
                mapa[i][9] = "#";
                mapa[0][j] = "#";
                mapa[9][j] = "#";
                mapa[6][j] = "#";
                mapa[2][j] = "#";
            }
        }
        mapa[8][7] = "#";
        mapa[8][5] = "#";
        mapa[6][2] = ".";
        mapa[4][7] = "#";
        mapa[4][8] = "#";
    }

    public static void inicializarEventos(String[][] mapa) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa.length; j++) {
                mapa[1][1] = "X";
                mapa[1][2] = "E";
                mapa[8][8] = "P";
                mapa[8][6] = "C";
                mapa[2][8] = "E";
                mapa[5][7] = "E";
                mapa[5][8] = "C";
                mapa[4][4] = "C";
            }
        }
    }

    public static void mostrarMapa(String[][] mapa) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa.length; j++) {
                System.out.print(mapa[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public static int[] estadisticasPesonaje(int[] personaje) {
        personaje[0] = 100; //salud
        personaje[1] = 15;  //daño
        personaje[2] = 8;   //posicionX
        personaje[3] = 8;   //posicionY
        return personaje;
    }

    public static int[] estadisticasEnemigo(int[] enenmigo, int posx, int posy) {
        enenmigo[0] = 45; //salud
        enenmigo[1] = 10;  //daño
        enenmigo[2] = posx;   //posicionX
        enenmigo[3] = posy;   //posicionY
        return enenmigo;
    }

    public static int[] movimientoPersonaje(String[][] mapa, int[] personaje, String movimiento) {
        int nuevaX = personaje[2];
        int nuevaY = personaje[3];

        switch (movimiento.toLowerCase()) {
            case "w":
                nuevaX--;
                break;
            case "s":
                nuevaX++;
                break;
            case "a":
                nuevaY--;
                break;
            case "d":
                nuevaY++;
                break;
            default:
                System.out.println("movimiento invalido");
                return personaje;
        }
        validarMovimineto(nuevaX, nuevaY, personaje, mapa);
        return personaje;
    }

    public static int[] validarMovimineto (int nuevaX, int nuevaY, int[] personaje, String[][] mapa) {
        if (nuevaX >= 0 && nuevaX < mapa.length && nuevaY >= 0 && nuevaY < mapa[0].length) {
            if (!mapa[nuevaX][nuevaY].equals("#")) {
                if (mapa[nuevaX][nuevaY].equals("X")){
                    personaje[0] = 0;
                }
                verificarEvento(mapa, personaje, nuevaX, nuevaY);
                mapa[personaje[2]][personaje[3]] = ".";
                personaje[2] = nuevaX;
                personaje[3] = nuevaY;
                mapa[nuevaX][nuevaY] = "P";
                return personaje;
            } else {
                System.out.println("No es posible atravesar un obstaculo");
            }
        } else {
            System.out.println("Te estas moviendo fuera de el mapa");
        }
        return personaje;
    }

    public static void verificarEvento(String[][] mapa, int[] personaje, int posicionX, int posicionY) {
        if (mapa[posicionX][posicionY].equals("E")) {
            int[] enemigo = estadisticasEnemigo(new int[4], posicionX,posicionY);
            combate(personaje, enemigo);
        }
        if (mapa[posicionX][posicionY].equals("C")) {
            manejoCofres(personaje);
        }
    }

    public static void combate(int[] personaje, int[] enemigo) {
        Scanner scanner = crearScanner();
        System.out.println("¡Se a iniciado una batalla!  Atacar (a) o Huir (h):");
        String accion = scanner.nextLine().toLowerCase();
        while (enemigo[0] > 0 && personaje[0] > 0) {
            if (accion.equals("a")) {
                enemigo[0] -= personaje[1];
                System.out.println("Has atacado al enenmigo. Vida del enemigo: " + enemigo[0]);
                if (enemigo[0] > 0) {
                    personaje[0] -= enemigo[1];
                    System.out.println("El enemigo te ha atacado. Tu vida es:" + personaje[0]);
                }
            } else if (accion.equals("h")) {
                System.out.println("Has huido del combate");
                break;
            }
            if (personaje[0] <= 0) {
                System.out.println("Te han derrotado");
            }
            if (enemigo[0] <= 0) {
                System.out.println("Has derrotado al enemigo");
            }
            System.out.println("Atacar: (a) o Huir (h)");
            accion = scanner.nextLine().toLowerCase();

        }
    }

    public static void manejoCofres(int[] personaje) {
        Random random = new Random();
        System.out.println("has encontrado un cofre ¿lo quieres abrir? (S/N)");
        Scanner scanner = crearScanner();
        String respuesta = scanner.nextLine().toLowerCase();

        if (respuesta.equals("s")) {
            if (random.nextBoolean()) {
                int pocion = 20;
                personaje[0] += pocion;
                System.out.println("Has encontrado una pocion. Vida +20. Tu vida: " + personaje[0]);
            } else {
                int trampa = 20;
                personaje[0] -= trampa;
                System.out.println("Has caido en una trampa. Vida -20. Tu vida: " + personaje[0]);
            }
        }
    }

        public static boolean estadoJuego (int[] personaje){
            if (personaje[0] <= 0) {
                System.out.println("Game over");
                return false;
            }
            return true;
        }
}
