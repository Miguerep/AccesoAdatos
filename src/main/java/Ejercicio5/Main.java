package Ejercicio5;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        AlumnoRepository ar = new AlumnoRepository();
        List<alumno> alumnos = new ArrayList<alumno>();

//        alumnos.add(new alumno(21, "Jose"));
//        alumnos.add(new alumno(14, "mario"));
//        alumnos.add(new alumno(18, "pepe"));
//
//        for (alumno a : alumnos) {
//            ar.insertar(a);
//        }

        List<alumno> listarAlumnos = ar.listar();

        for (alumno a : listarAlumnos) {
            System.out.println(a);
        }


        System.out.println("\nMOSTRANDO JSON.....\n");
        ar.mostrarJSON();

        ar.cerrar();
    }
}
