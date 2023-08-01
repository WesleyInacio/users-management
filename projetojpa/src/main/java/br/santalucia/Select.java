package br.santalucia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Contato;

import java.util.List;

public class Select {
    private static EntityManagerFactory emf;
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("aplicativo");
        List<Contato> lista = null;
        EntityManager em = emf.createEntityManager();
        try {
            lista = em.createQuery("from Contato").getResultList();
            System.out.println("Consulta realizada");
            System.out.println("Dados de todos os contatos");
            for (Contato contato : lista){
                System.out.println(contato);
            }
        } catch (Exception ex){
            System.out.println("Erro ao consultar");
        } finally {
            em.close();
        }
    }
}