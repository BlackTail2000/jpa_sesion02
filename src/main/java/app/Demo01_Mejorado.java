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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class Demo01_Mejorado extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNom_usua;
	private JTextField txtApe_usua;
	private JTextField txtUsr_usua;
	private JPasswordField txtCla_usua;
	private JLabel lblApe_usua;
	private JLabel lblNom_usua;
	private JLabel lblUsr_usua;
	private JLabel lblCla_usua;
	private JLabel lblFna_usua;
	private JDateChooser dateChooser;
	private JLabel lblTipo;
	private JComboBox<String> cboTipo;
	private JButton btnRegistrar;
	private static final Pattern email_pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo01_Mejorado frame = new Demo01_Mejorado();
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
	public Demo01_Mejorado() {
		setTitle("Registrar Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNom_usua = new JLabel("Nombres:");
		lblNom_usua.setBounds(10, 10, 60, 13);
		contentPane.add(lblNom_usua);
		
		txtNom_usua = new JTextField();
		txtNom_usua.setBounds(80, 7, 181, 19);
		contentPane.add(txtNom_usua);
		txtNom_usua.setColumns(10);
		
		lblApe_usua = new JLabel("Apellidos:");
		lblApe_usua.setBounds(306, 10, 60, 13);
		contentPane.add(lblApe_usua);
		
		txtApe_usua = new JTextField();
		txtApe_usua.setBounds(376, 7, 150, 19);
		contentPane.add(txtApe_usua);
		txtApe_usua.setColumns(10);
		
		lblUsr_usua = new JLabel("Email:");
		lblUsr_usua.setBounds(10, 33, 60, 13);
		contentPane.add(lblUsr_usua);
		
		txtUsr_usua = new JTextField();
		txtUsr_usua.setBounds(80, 30, 181, 19);
		contentPane.add(txtUsr_usua);
		txtUsr_usua.setColumns(10);
		
		lblCla_usua = new JLabel("Clave:");
		lblCla_usua.setBounds(306, 33, 60, 13);
		contentPane.add(lblCla_usua);
		
		txtCla_usua = new JPasswordField();
		txtCla_usua.setBounds(376, 30, 150, 19);
		contentPane.add(txtCla_usua);
		
		lblFna_usua = new JLabel("Fecha de Nacimiento:");
		lblFna_usua.setBounds(10, 56, 150, 13);
		contentPane.add(lblFna_usua);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(141, 54, 120, 19);
		contentPane.add(dateChooser);
		
		lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(306, 56, 60, 13);
		contentPane.add(lblTipo);
		
		cboTipo = new JComboBox<String>();
		cboTipo.setBounds(376, 52, 150, 21);
		contentPane.add(cboTipo);
		
		cboTipo.addItem("--Seleccionar Tipo--");
		cargarTipos();
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		btnRegistrar.setBounds(208, 112, 120, 21);
		contentPane.add(btnRegistrar);
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
	
	public void registrar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
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
							if(dateChooser.getDate()!=null) {
								Date fna_usua = dateChooser.getDate();
								if(cboTipo.getSelectedIndex()!=0) {
									String tipo_descripcion = cboTipo.getSelectedItem().toString();
									Query query = em.createNamedQuery("Tipo.findByDescripcion");
									query.setParameter("descripcion", tipo_descripcion);
									Tipo tipo = (Tipo) query.getSingleResult();
									
									Usuario usuario = new Usuario();
									usuario.setNom_usua(nom_usua);
									usuario.setApe_usua(ape_usua);
									usuario.setUsr_usua(usr_usua);
									usuario.setCla_usua(cla_usua);
									usuario.setFna_usua(fna_usua);
									usuario.setTipo(tipo);
									usuario.setEst_usua(1);
									
									em.getTransaction().begin();
									em.persist(usuario);
									em.getTransaction().commit();
								} else {
									JOptionPane.showMessageDialog(this, "Seleccionar un tipo de usuario.");
								}
							} else {
								JOptionPane.showMessageDialog(this, "Seleccionar fecha de nacimiento.");
							}
						} else {
							JOptionPane.showMessageDialog(this, "Ingresar clave.");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Ingresar email valido.");
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
		
		em.close();
		emf.close();
	}
}
