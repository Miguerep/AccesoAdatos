package Ejercicio5;

import java.util.*;

public class MainEjercicio7 {
    public static void main(String[] args) {
        AlumnoDocumental ad = new AlumnoDocumental("prueba.json");
        List<Map< String, Object>> lista = new ArrayList();
        lista.add(Map.of("nombre", "ana", "edad", 21, "curso", "1ºDAM"));
        lista.add(Map.of("nombre", "david", "edad", 19, "curso", "1ºSMR"));
        lista.add(Map.of("nombre", "david", "edad", 19, "curso", "1ºSMR"));

        ad.guardar(lista);

        System.out.println("\nMOSTRANDO JSON.....\n" + ad.cargar());


        
    }
}
