package Ejercicio5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

public class AlumnoDocumental {

    private String nombreFichero;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public AlumnoDocumental() {

    }

    public AlumnoDocumental(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }



    public void guardar(List<Map<String, Object>> alumno){

        try(FileWriter fw = new FileWriter(nombreFichero)){
            gson.toJson(alumno,fw);
            System.out.println("Archivo guardado exitosamente en: " + nombreFichero);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> cargar() {

        List<Map<String, Object>> alumnos = new ArrayList<>();

        try(FileReader fr = new FileReader(nombreFichero)){

            alumnos = gson.fromJson(fr, List.class);


        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + nombreFichero);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + nombreFichero + "\n" + e.getMessage());
        }

        return alumnos;
    }
}
