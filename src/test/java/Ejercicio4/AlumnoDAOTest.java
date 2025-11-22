package Ejercicio4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoDAOTest {

    @Test
    void testInsertar() {
        AlumnoDAO a = new AlumnoDAO();

        assertDoesNotThrow(() -> a.insertar("juan", 22));
    }
}