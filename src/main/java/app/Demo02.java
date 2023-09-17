package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import com.toedter.calendar.JDateChooser;

import model.Tipo;
import model.Usuario;

import javax.swing.JComboBox;
import jakarta.persistence.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class Demo02 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNom_usua;
	private JTextField txtApe_usua;
	private JTextField txtUsr_usua;
	private JPasswordField txtCla_usua;
	private JLabel lblCod_usua;
	private JLabel lblNom_usua;
	private JLabel lblApe_usua;
	private JLabel lblUsr_usua;
	private JLabel lblCla_usua;
	private JLabel lblFna_usua;
	private JDateChooser dateChooser;
	private JLabel lblTipo;
	private JComboBox<String> cboTipo;
	private JLabel lblEst_usua;
	private JComboBox<String> cboEst_usua;
	private JButton btnActualizar;
	private static final Pattern email_pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private JComboBox<Integer> cboCod_usua;
	private JButton btnBuscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo02 frame = new Demo02();
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
	public Demo02() {
		setTitle("Actualizar Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblCod_usua = new JLabel("Código:");
		lblCod_usua.setBounds(10, 10, 60, 13);
		contentPane.add(lblCod_usua);
		
		lblNom_usua = new JLabel("Nombres:");
		lblNom_usua.setBounds(10, 33, 60, 13);
		contentPane.add(lblNom_usua);
		
		txtNom_usua = new JTextField();
		txtNom_usua.setBounds(80, 30, 200, 19);
		contentPane.add(txtNom_usua);
		txtNom_usua.setColumns(10);
		
		lblApe_usua = new JLabel("Apellidos:");
		lblApe_usua.setBounds(290, 33, 60, 13);
		contentPane.add(lblApe_usua);
		
		txtApe_usua = new JTextField();
		txtApe_usua.setBounds(360, 30, 200, 19);
		contentPane.add(txtApe_usua);
		txtApe_usua.setColumns(10);
		
		lblUsr_usua = new JLabel("Email:");
		lblUsr_usua.setBounds(10, 56, 60, 13);
		contentPane.add(lblUsr_usua);
		
		txtUsr_usua = new JTextField();
		txtUsr_usua.setBounds(80, 53, 200, 19);
		contentPane.add(txtUsr_usua);
		txtUsr_usua.setColumns(10);
		
		lblCla_usua = new JLabel("Clave:");
		lblCla_usua.setBounds(290, 56, 60, 13);
		contentPane.add(lblCla_usua);
		
		txtCla_usua = new JPasswordField();
		txtCla_usua.setBounds(360, 53, 200, 19);
		contentPane.add(txtCla_usua);
		
		lblFna_usua = new JLabel("Fecha de nacimiento:");
		lblFna_usua.setBounds(10, 79, 150, 13);
		contentPane.add(lblFna_usua);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(136, 76, 144, 19);
		contentPane.add(dateChooser);
		
		lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(290, 79, 60, 13);
		contentPane.add(lblTipo);
		
		cboTipo = new JComboBox<String>();
		cboTipo.setBounds(360, 75, 200, 21);
		contentPane.add(cboTipo);
		
		cboTipo.addItem("--Seleccionar Tipo--");
		cargarTipos();
		
		lblEst_usua = new JLabel("Estado:");
		lblEst_usua.setBounds(10, 102, 60, 13);
		contentPane.add(lblEst_usua);
		
		cboEst_usua = new JComboBox<String>();
		cboEst_usua.setModel(new DefaultComboBoxModel<String>(new String[] {"--Seleccionar Estado--", "Activo", "Inactivo"}));
		cboEst_usua.setBounds(80, 102, 200, 21);
		contentPane.add(cboEst_usua);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizar();
			}
		});
		btnActualizar.setBounds(243, 132, 100, 21);
		contentPane.add(btnActualizar);
		
		cboCod_usua = new JComboBox<Integer>();
		cboCod_usua.setBounds(80, 6, 200, 21);
		contentPane.add(cboCod_usua);
		cargarCombo();
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(290, 6, 85, 21);
		contentPane.add(btnBuscar);
	}
	
	public void buscar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		int cod_usua = (int) cboCod_usua.getSelectedItem();
		Query query = em.createQuery("Select u From Usuario u Where u.cod_usua = :cod_usua", Usuario.class);
		query.setParameter("cod_usua", cod_usua);
		
		Usuario usuario = (Usuario) query.getSingleResult();
		txtNom_usua.setText(usuario.getNom_usua());
		txtApe_usua.setText(usuario.getApe_usua());
		txtUsr_usua.setText(usuario.getUsr_usua());
		txtCla_usua.setText(usuario.getCla_usua());
		dateChooser.setDate(usuario.getFna_usua());
		cboTipo.setSelectedItem(usuario.getTipo().getDescripcion());
		cboEst_usua.setSelectedIndex(usuario.getEst_usua());
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
			cboCod_usua.addItem(item.getCod_usua());
		}
	}
	
	public void cargarTipos() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
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
	
	public void actualizar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
			int cod_usua = (int) cboCod_usua.getSelectedItem();
			String nom_usua = txtNom_usua.getText();
			if(nom_usua.length()>0) {
				String ape_usua = txtApe_usua.getText();
				if(ape_usua.length()>0) {
					String usr_usua = txtUsr_usua.getText();
					if(usr_usua.length()>0) {
						Matcher matcher = email_pattern.matcher(usr_usua);
						if(matcher.matches()) {
							String cla_usua = String.valueOf(txtCla_usua.getPassword());
							if(cla_usua.length()>0) {
								try {
									Date fna_usua = dateChooser.getDate();
									if(cboTipo.getSelectedIndex()!=0) {
										Query query = em.createNamedQuery("Tipo.findByDescripcion");
										query.setParameter("descripcion", cboTipo.getSelectedItem().toString());
										Tipo tipo = (Tipo) query.getSingleResult();
										if(cboEst_usua.getSelectedIndex()!=0) {
											int est_usua = cboEst_usua.getSelectedIndex();
											
											Usuario usuario = new Usuario();
											usuario.setCod_usua(cod_usua);
											usuario.setNom_usua(nom_usua);
											usuario.setApe_usua(ape_usua);
											usuario.setUsr_usua(usr_usua);
											usuario.setCla_usua(cla_usua);
											usuario.setFna_usua(fna_usua);
											usuario.setTipo(tipo);
											usuario.setEst_usua(est_usua);
											
											em.getTransaction().begin();
											em.merge(usuario);
											em.getTransaction().commit();
										} else {
											JOptionPane.showMessageDialog(this, "Seleccionar un estado.");
										}
									} else {
										JOptionPane.showMessageDialog(this, "Seleccionar un tipo.");
									}
								} catch(Exception e) {
									JOptionPane.showMessageDialog(this, "Ingresar fecha de nacimiento.");
								}
							} else {
								JOptionPane.showMessageDialog(this, "Ingresar clave.");
							}
						} else {
							JOptionPane.showMessageDialog(this, "Ingresar email válido.");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Ingresar email.");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Ingresar apellidos.");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Ingresar nombres.");
			}
	}
}
