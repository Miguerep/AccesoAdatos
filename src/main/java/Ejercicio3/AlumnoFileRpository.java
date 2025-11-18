package Ejercicio3;
import java.io.*;
import java.util.*;

public class AlumnoFileRpository {

    private final String FICHERO = "alumno.txt";

    public void guardar(List<String> alumnos) {

        try {
            FileWriter fw = new FileWriter(FICHERO);
            for (String alumno : alumnos) {
                fw.write(alumno + "\n");
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<String> cargar(){

        ArrayList lista = new ArrayList();
        File f = new File(FICHERO);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            if ((linea = br.readLine()) != null) {
                lista.add(linea);
            }else {
                br.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
