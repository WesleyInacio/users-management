package br.santalucia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Contato;

public class Insert {
    private static EntityManagerFactory emf;
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("aplicativo");
        Contato contato = new Contato();
        contato.setNome("Wesley");
        contato.setTelefone("19-9999-9999");
        contato.setEmail("wesley@hotmail.com");
        contato.setObservacao("Teste");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(contato);
            em.getTransaction().commit();
            System.out.println("Gravado com sucesso");
        } catch (Exception ex){
            em.getTransaction().rollback();
            System.out.println("Erro ao gravar: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
}