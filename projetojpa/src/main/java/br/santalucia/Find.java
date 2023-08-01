package br.santalucia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Contato;

import java.util.List;

public class Find {
    private static EntityManagerFactory emf;
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("aplicativo");
        Contato contato = null;
        EntityManager em = emf.createEntityManager();
        try {
            contato = em.find(Contato.class, (int)1);
            System.out.println("Consulta realizada");
            System.out.println("Dados do contato selecionado");
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