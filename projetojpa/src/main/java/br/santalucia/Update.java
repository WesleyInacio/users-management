package br.santalucia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Contato;

import java.util.List;

public class Update {
    private static EntityManagerFactory emf;
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("aplicativo");
        Contato contato = null;
        EntityManager em = emf.createEntityManager();
        try {
            contato = em.find(Contato.class, (int)1);
            contato.setTelefone("(19) 0000-0000");
            em.getTransaction().begin();
            em.merge(contato);
            em.getTransaction().commit();
            System.out.println("Alteração realizada");
        } catch (Exception ex){
            System.out.println("Erro ao consultar");
        } finally {
            em.close();
        }
        if (contato != null){
            System.out.println(contato);
        }
    }
}