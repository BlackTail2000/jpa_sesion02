package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jakarta.persistence.*;
import model.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsr_usua;
	private JButton btnIngresar;
	private JLabel lblCla_usua;
	private JLabel lblUsr_usua;
	private JPasswordField txtCla_usua;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Login Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 120);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblUsr_usua = new JLabel("Usuario:");
		lblUsr_usua.setBounds(10, 10, 60, 13);
		contentPane.add(lblUsr_usua);
		
		lblCla_usua = new JLabel("Clave:");
		lblCla_usua.setBounds(10, 45, 60, 13);
		contentPane.add(lblCla_usua);
		
		txtUsr_usua = new JTextField();
		txtUsr_usua.setBounds(80, 7, 150, 19);
		contentPane.add(txtUsr_usua);
		txtUsr_usua.setColumns(10);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnIngresar.setBounds(240, 6, 85, 21);
		contentPane.add(btnIngresar);
		
		txtCla_usua = new JPasswordField();
		txtCla_usua.setBounds(80, 42, 150, 19);
		contentPane.add(txtCla_usua);
	}
	
	public void login() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		String usr_usua = txtUsr_usua.getText();
		if(usr_usua.length()>0) {
			String cla_usua = String.valueOf(txtCla_usua.getPassword());
			if(cla_usua.length()>0) {
				try {
					Query query = em.createQuery("Select u From Usuario u Where u.usr_usua = :usr_usua And u.cla_usua = :cla_usua");
					query.setParameter("usr_usua", usr_usua);
					query.setParameter("cla_usua", cla_usua);
					Usuario usuario = (Usuario) query.getSingleResult();
					JOptionPane.showMessageDialog(this, "Hola, " + usuario.getApe_usua() + " " + usuario.getNom_usua());
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this, "Usuario y/o contraseña incorrectos.");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Ingresar clave.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Ingresar email.");
		}
		
		em.close();
		emf.close();
	}
}
