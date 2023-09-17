package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Usuario;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import jakarta.persistence.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Demo04 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblCod_usua;
	private JComboBox<Integer> cboCodigo;
	private JScrollPane scrollPane;
	private JTextArea txtS;
	private JButton btnBuscar;
	private JButton btnEliminar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo04 frame = new Demo04();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Demo04() {
		setTitle("Buscar Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblCod_usua = new JLabel("Código:");
		lblCod_usua.setBounds(10, 10, 60, 13);
		contentPane.add(lblCod_usua);
		
		cboCodigo = new JComboBox<Integer>();
		cboCodigo.setBounds(80, 6, 100, 21);
		contentPane.add(cboCodigo);
		cargarCombo();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 466, 264);
		contentPane.add(scrollPane);
		
		txtS = new JTextArea();
		txtS.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(txtS);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(190, 6, 85, 21);
		contentPane.add(btnBuscar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		btnEliminar.setBounds(190, 332, 85, 21);
		contentPane.add(btnEliminar);
	}
	
	public void cargarCombo() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Query query = em.createNamedQuery("Usuario.findAll");
		for(Object item: query.getResultList()) {
			if(item instanceof Usuario) {
				usuarios.add((Usuario) item);
			}
		}
		
		for(Usuario item: usuarios) {
			cboCodigo.addItem(item.getCod_usua());
		}
	}
	
	public void eliminar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		int cod_usua = (int) cboCodigo.getSelectedItem();
		Query query = em.createQuery("Select u From Usuario u Where u.cod_usua = :cod_usua", Usuario.class);
		query.setParameter("cod_usua", cod_usua);
		
		Usuario usuario = (Usuario) query.getSingleResult();
		
		em.getTransaction().begin();
		em.remove(usuario);
		em.getTransaction().commit();
		
		cboCodigo.removeAllItems();
		cargarCombo();
	}
	
	public void buscar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		int cod_usua = (int) cboCodigo.getSelectedItem();
		Query query = em.createQuery("Select u From Usuario u Where u.cod_usua = :cod_usua", Usuario.class);
		query.setParameter("cod_usua", cod_usua);
		
		Usuario usuario = (Usuario) query.getSingleResult();
		
		String estado;
		if(usuario.getEst_usua()==1)
			estado = "Activo";
		else
			estado = "Inactivo";
		
		txtS.setText("");
		txtS.append("**************************************************\n");
		txtS.append("Usuario Nro. " + usuario.getCod_usua() + "\n");
		txtS.append("**************************************************\n");
		txtS.append("Nombres...............: " + usuario.getNom_usua() + "\n");
		txtS.append("Apellidos.............: " + usuario.getApe_usua() + "\n");
		txtS.append("Email.................: " + usuario.getUsr_usua() + "\n");
		txtS.append("Fecha de Nacimiento...: " + usuario.getFna_usua() + "\n");
		txtS.append("Tipo..................: " + usuario.getTipo().getIdtipo() + " - " + usuario.getTipo().getDescripcion() + "\n");
		txtS.append("Estado................: " + estado);
	}
}
