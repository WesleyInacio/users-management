package view;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Contato;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContatoView {
    private JPanel panelContato;
    private JPanel panelInterno;
    private JLabel lblIdContato;
    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnListar;
    private JTextField txtIdContato;
    private JLabel lblNome;
    private JTextField txtNome;
    private JLabel lblEmail;
    private JTextField txtEmail;
    private JLabel lblTelefone;
    private JTextField txtTelefone;
    private JLabel lblObservacao;
    private JTextArea txtObservacao;
    private JButton btnProcurar;

    private static boolean newRecord = false;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("aplicativo");

    public ContatoView() {
        btnProcurar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EntityManager em = emf.createEntityManager();
                Contato contato = em.find(Contato.class, Integer.parseInt(txtIdContato.getText()));
                if (contato != null) {
                    txtNome.setText(contato.getNome());
                    txtEmail.setText(contato.getEmail());
                    txtTelefone.setText(contato.getTelefone());
                    txtObservacao.setText(contato.getObservacao());
                    newRecord = false;
                } else {
                    txtIdContato.setText("<Automático>");
                    limpar();
                    txtNome.requestFocus();
                    newRecord = true;
                }
            }
        });

        btnSalvar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EntityManager em = emf.createEntityManager();
                try {
                    Contato contato = new Contato();
                    contato.setNome(txtNome.getText().toUpperCase());
                    contato.setEmail(txtEmail.getText().toUpperCase());
                    contato.setTelefone(txtTelefone.getText().toUpperCase());
                    contato.setObservacao(txtObservacao.getText().toUpperCase());
                    if (newRecord) {
                        em.getTransaction().begin();
                        em.persist(contato);
                        em.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Registro novo inserido", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        contato.setIdContato(Integer.parseInt(txtIdContato.getText()));
                        em.getTransaction().begin();
                        em.merge(contato);
                        em.getTransaction().commit();
                        JOptionPane.showMessageDialog(null, "Registro alterado", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    limpar();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao inserir/atualizar", "Erro", JOptionPane.ERROR_MESSAGE);
                    em.getTransaction().rollback();
                } finally {
                    em.close();
                }
            }
        });

        btnExcluir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EntityManager em = emf.createEntityManager();
                Contato contato = em.find(Contato.class, Integer.parseInt(txtIdContato.getText()));
                if (contato == null){
                    JOptionPane.showMessageDialog(null, "Registro não encontrado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    em.close();
                } else {
                    em.getTransaction().begin();
                    em.remove(contato);
                    em.getTransaction().commit();
                    limpar();
                    JOptionPane.showMessageDialog(null, "Registro excluído com sucesso", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        btnListar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    JFrame frame2 = new JFrame("ListaContatoView");
                    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    ListaContatoView listaContatoView = new ListaContatoView();
                    frame2.setContentPane(listaContatoView.getPanel1());
                    frame2.setTitle("Lista de resultados");
                    frame2.pack();
                    frame2.setVisible(true);
                });
            }
        });
    }

    public JPanel getPanelContato() {
        return panelContato;
    }

    public void limpar() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtObservacao.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ContatoView");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ContatoView contatoView = new ContatoView();
            frame.setContentPane(contatoView.getPanelContato());
            frame.setTitle("Formulário de contatos");
            frame.pack();
            frame.setVisible(true);
        });
    }
}
