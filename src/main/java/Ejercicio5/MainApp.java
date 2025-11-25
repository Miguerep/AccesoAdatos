package Ejercicio5;


public class MainApp {
    public static void main(String[] args) {

        AlumnoService service = new AlumnoService();

        alumno a = new alumno("pepe", 51);
        service.insertarRepo(a);

        String nombre = a.getNombre();
        int edad = a.getEdad();

        service.insertarDAO(nombre, edad + 2);

        System.out.println(service.listarRepo().toString());

        System.out.println(service.listarDAO());
    }
}
