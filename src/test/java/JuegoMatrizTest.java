import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JuegoMatrizTest {

    private String[][] mapa;

    @BeforeEach
    void cargarMapa() {
        mapa = JuegoMatriz.cargarMapa(new String[10][10]);
    }

    @Test
    public void testMovimientoArriba() {
        int[] personaje = {100, 15, 8, 8};
        personaje = JuegoMatriz.movimientoPersonaje(mapa, personaje, "w");

        assertEquals(7, personaje[2]);
        assertEquals(8, personaje[3]);
    }

    @Test
    public void testMovimientoAbajo() {
        int[] personaje = {100, 15, 5, 2};
        personaje = JuegoMatriz.movimientoPersonaje(mapa, personaje, "s");

        assertEquals(6, personaje[2]);
        assertEquals(2, personaje[3]);
    }

    @Test
    public void testMovimientoIzquierda() {
        int[] personaje = {100, 15, 5, 2};
        personaje = JuegoMatriz.movimientoPersonaje(mapa, personaje, "a");

        assertEquals(5, personaje[2]);
        assertEquals(1, personaje[3]);
    }

    @Test
    public void testMovimientoDerecha() {
        int[] personaje = {100, 15, 5, 2};
        personaje = JuegoMatriz.movimientoPersonaje(mapa, personaje, "d");

        assertEquals(5, personaje[2]);
        assertEquals(3, personaje[3]);
    }

    @Test
    public void testMovimientoObstaculo() {
        int[] personaje = {100, 15, 8, 8};
        personaje = JuegoMatriz.movimientoPersonaje(mapa, personaje, "d");

        assertEquals(8, personaje[2]);
        assertEquals(8, personaje[3]);
    }

    @AfterEach
    void borrarMapa() {
        mapa = null;
    }
}

