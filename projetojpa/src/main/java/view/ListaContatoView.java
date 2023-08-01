package view;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Contato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListaContatoView {
    private JPanel panel1;
    private JTable table1;

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("aplicativo");

    public JPanel getPanel1(){
        return panel1;
    }

    String header[] = {"IdContato", "Nome", "Email", "Telefone", "Observação"};

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel(0, 4);
        model.setColumnIdentifiers(header);
        table1 = new JTable(model);
        List<Contato> contatos = null;
        EntityManager em = emf.createEntityManager();
        try {
            contatos = em.createQuery("from Contato").getResultList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar", "Mensagem", JOptionPane.ERROR_MESSAGE);
        } finally {
            em.close();
        }
        for (Contato contato : contatos) {
            Object[] row = new Object[]{contato.getIdContato(), contato.getNome(), contato.getEmail(), contato.getTelefone(), contato.getObservacao()};
            model.addRow(row);
        }
    }
}
