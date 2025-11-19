package Ejercicio4;

import java.util.List;

public class Main {
    public static void main(String[] args) {

            AlumnoDAO dao = new AlumnoDAO();
            dao.insertar("Juan", 20);
            dao.insertar("María", 22);

            List<String> alumnos = dao.listar();

            for (String alumno : alumnos) {

                System.out.println(alumno);
            }

    }
}
