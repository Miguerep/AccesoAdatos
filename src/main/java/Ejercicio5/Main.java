package Ejercicio5;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        AlumnoRepository ar = new AlumnoRepository();
        List<alumno> alumnos = new ArrayList<>();

        alumnos.add(new alumno("Jose", 21));
        alumnos.add(new alumno("mario",14));
        alumnos.add(new alumno("pepe",18));

        for (alumno a : alumnos) {
            ar.insertar(a);
        }

        List<alumno> listarAlumnos = ar.listar();

        for (alumno a : listarAlumnos) {
            System.out.println(a);
        }

        ar.cerrar();


    }
}
