package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Tipo;
import model.Usuario;

import jakarta.persistence.*;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class Demo06 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tblUsuarios;
	private JScrollPane scrollPane;
	private JButton btnListar;
	private DefaultTableModel modelo;
	private JComboBox<String> cboTipo;
	private JLabel lblTipo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo06 frame = new Demo06();
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
	public Demo06() {
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
		scrollPane.setBounds(10, 44, 766, 279);
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
		
		lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(10, 10, 45, 13);
		contentPane.add(lblTipo);
		
		cboTipo = new JComboBox<String>();
		cboTipo.setBounds(65, 6, 160, 21);
		contentPane.add(cboTipo);
		cargarTipos();
		
	}
	
	public void cargarTipos() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		cboTipo.addItem("--Seleccionar tipo--");
		List<Tipo> tipos = new ArrayList<Tipo>();
		
		Query query = em.createQuery("Select t From Tipo t");
		for(Object item: query.getResultList()) {
			if(item instanceof Tipo) {
				tipos.add((Tipo) item);
			}
		}
		
		for(Tipo item: tipos) {
			cboTipo.addItem(item.getDescripcion());
		}
	}
	
	public void listar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		if(cboTipo.getSelectedIndex()!=0) {
			Query query = em.createQuery("Select u From Usuario u Where u.tipo.descripcion = :descripcion");
			query.setParameter("descripcion", cboTipo.getSelectedItem().toString());
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
		} else {
			JOptionPane.showMessageDialog(this, "Seleccionar un Tipo.");
		}
	}
}
