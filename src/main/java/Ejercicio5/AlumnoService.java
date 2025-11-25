package Ejercicio5;

import Ejercicio4.AlumnoDAO;

import java.util.List;

public class AlumnoService {

    AlumnoRepository repo = new AlumnoRepository();
    AlumnoDAO dao = new AlumnoDAO();


    public void insertarRepo(alumno a){
        repo.insertar(a);
    }

    public void insertarDAO(String nombre, int edad){
        dao.insertar(nombre, edad);
    }

    public List<alumno> listarRepo(){
        return repo.listar();
    }

    public List<String> listarDAO(){
        return dao.listar();
    }
}
