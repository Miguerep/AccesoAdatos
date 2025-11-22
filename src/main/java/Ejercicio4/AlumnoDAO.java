package Ejercicio4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {
    private final String URL = "jdbc:mysql://localhost:3306/escuela";
    private final String user = "root";
    private final String password = "root";

    public void insertar(String nombre, int edad) {
            String sql = "INSERT INTO alumno VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, user, password)){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, edad);
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> listar() {
        String sql = "SELECT * FROM escuela";
        List<String> lista = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, user, password)){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("nombre"));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
