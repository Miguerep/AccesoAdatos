package Ejercicio5;

import jakarta.persistence.*;

import java.util.List;

public class AlumnoRepository {
    private EntityManagerFactory emf;

    public AlumnoRepository() {
        emf = Persistence.createEntityManagerFactory("AlumnoPU");
    }

    public void insertar(alumno a) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            em.persist(a);

            tx.commit();
            System.out.println(" Alumno insertado correctamente" + a);
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Error al insertar: " + e.getMessage());
            e.printStackTrace();
        }   finally {
            em.close();
        }
    }
    public List<alumno> listar() {
        EntityManager em = emf.createEntityManager();
        List<alumno> alumnos = null;

        try{
            TypedQuery<alumno> query = em.createQuery("from alumno", alumno.class);
            alumnos = query.getResultList();
        }catch(Exception e){
            System.err.println("Error al listar: " + e.getMessage());
            e.printStackTrace();
        }finally{
            em.close();
        }
        return alumnos;
    }

    public void cerrar(){
        if(emf != null && emf.isOpen()){
            emf.close();
        }
    }
}
