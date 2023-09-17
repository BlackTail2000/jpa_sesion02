package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Usuario;

import jakarta.persistence.*;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Demo05 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblUsuarios;
	private JScrollPane scrollPane;
	private JButton btnListar;
	private DefaultTableModel modelo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo05 frame = new Demo05();
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
	public Demo05() {
		setTitle("Listar usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		btnListar.setBounds(355, 333, 85, 21);
		contentPane.add(btnListar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 766, 313);
		contentPane.add(scrollPane);
		
		tblUsuarios = new JTable();
		tblUsuarios.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblUsuarios);
		
		modelo = new DefaultTableModel();
		modelo.addColumn("Código");
		modelo.addColumn("Usuario");
		modelo.addColumn("Email");
		modelo.addColumn("Fecha de Nacimiento");
		modelo.addColumn("Tipo");
		modelo.addColumn("Estado");
		tblUsuarios.setModel(modelo);
		
		
	}
	
	public void listar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		Query query = em.createNamedQuery("Usuario.findAll");
		for(Object item: query.getResultList()) {
			if(item instanceof Usuario) {
				usuarios.add((Usuario) item);
			}
		}
		modelo.setRowCount(0);
		for(Usuario item: usuarios) {
			
			String estado;
			if(item.getEst_usua()==1)
				estado = "Activo";
			else
				estado = "Inactivo";
			
			Object[] fila = {
					item.getCod_usua(),
					item.getApe_usua() + " " + item.getNom_usua(),
					item.getUsr_usua(),
					item.getFna_usua(),
					item.getTipo().getDescripcion(),
					estado
			};
			
			modelo.addRow(fila);
		}
	}
}
