package Ejercicio3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AlumnoFileRpository alum = new AlumnoFileRpository();

        List<String> alumnos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            alumnos.add("Alumno " + i);
            System.out.println("Alumno " + i + ": " + alumnos.get(i));
        }
        System.out.println("GUARDANDO ALUMNOS....\n");
        alum.guardar(alumnos);


        System.out.println("CARGANDO ALUMNOS.... \n");
        alum.cargar();
        System.out.println(alumnos.toString());
    }
}