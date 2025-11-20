package Ejercicio5;
import jakarta.persistence.*;

    @Entity
    @Table(name = "alumno")
public class alumno {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "edad")
        private Integer edad;

        @Column(name = "nombre")
        private String nombre;

        public alumno() {

        }

        public alumno(Integer edad, String nombre) {
            this.edad = edad;
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Integer getEdad() {
            return edad;
        }

        public void setEdad(Integer edad) {
            this.edad = edad;
        }

        @Override
        public String toString() {
            return "alumno{" +
                    "id=" + id +
                    ", edad=" + edad +
                    ", nombre='" + nombre + '\'' +
                    '}';
        }
    }

