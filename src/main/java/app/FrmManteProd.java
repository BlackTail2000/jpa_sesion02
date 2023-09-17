package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jakarta.persistence.*;
import model.Categoria;
import model.Producto;
import model.Proveedor;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class FrmManteProd extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextArea txtSalida;
	private JTextField txtCodigo;
	private JComboBox<String> cboCategorias;
	private JComboBox<String> cboProveedores;
	private JTextField txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecio;
	private JButton btnRegistrar;
	private JScrollPane scrollPane;
	private JButton btnListado;
	private JLabel lblCodigo;
	private JLabel lblCategora;
	private JLabel lblNomProducto;
	private JLabel lblStock;
	private JLabel lblPrecio;
	private JLabel lblProveedores;
	private JButton btnBuscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmManteProd frame = new FrmManteProd();
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
	public FrmManteProd() {
		setTitle("Mantenimiento de Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnRegistrar.setBounds(324, 29, 89, 23);
		contentPane.add(btnRegistrar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 414, 143);
		contentPane.add(scrollPane);

		txtSalida = new JTextArea();
		scrollPane.setViewportView(txtSalida);

		btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(177, 322, 89, 23);
		contentPane.add(btnListado);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(122, 11, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		lblCodigo = new JLabel("Id. Producto :");
		lblCodigo.setBounds(10, 14, 102, 14);
		contentPane.add(lblCodigo);

		cboCategorias = new JComboBox<String>();
		cboCategorias.setBounds(122, 70, 144, 22);
		contentPane.add(cboCategorias);

		lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setBounds(10, 74, 102, 14);
		contentPane.add(lblCategora);

		lblNomProducto = new JLabel("Nom. Producto :");
		lblNomProducto.setBounds(10, 45, 102, 14);
		contentPane.add(lblNomProducto);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(122, 42, 144, 20);
		contentPane.add(txtDescripcion);

		lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 106, 102, 14);
		contentPane.add(lblStock);

		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(122, 103, 77, 20);
		contentPane.add(txtStock);

		lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 134, 102, 14);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(122, 131, 77, 20);
		contentPane.add(txtPrecio);

		lblProveedores = new JLabel("Proveedor:");
		lblProveedores.setBounds(230, 106, 102, 14);
		contentPane.add(lblProveedores);

		cboProveedores = new JComboBox<String>();
		cboProveedores.setBounds(300, 104, 120, 22);
		contentPane.add(cboProveedores);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(324, 63, 89, 23);
		contentPane.add(btnBuscar);

		llenaCombo();
	}

	void llenaCombo() {
		cboCategorias.addItem("Seleccione");
		cboProveedores.addItem("Seleccione");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		List<Proveedor> proveedores = new ArrayList<Proveedor>();
		
		Query query = em.createNamedQuery("Categoria.findAll");
		for(Object item: query.getResultList()) {
			if(item instanceof Categoria) {
				categorias.add((Categoria) item);
			}
		}
		
		query = em.createNamedQuery("Proveedor.findAll");
		for(Object item: query.getResultList()) {
			if(item instanceof Proveedor) {
				proveedores.add((Proveedor) item);
			}
		}
		
		for(Categoria item: categorias) {
			cboCategorias.addItem(item.getDescripcion());
		}
		
		for(Proveedor item: proveedores) {
			cboProveedores.addItem(item.getNombre_rs());
		}
		
		em.close();
		emf.close();
	}

	void registrar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		String id_prod = txtCodigo.getText();
		if(id_prod.length()>0) {
			String des_prod = txtDescripcion.getText();
			if(des_prod.length()>0) {
				if(cboCategorias.getSelectedIndex()!=0) {
					Query query = em.createQuery("Select c From Categoria c Where c.descripcion = :descripcion");
					query.setParameter("descripcion", cboCategorias.getSelectedItem().toString());
					Categoria categoria = (Categoria) query.getSingleResult();
					
					try {
						int stk_prod = Integer.parseInt(txtStock.getText());
						if(cboProveedores.getSelectedIndex()!=0) {
							query = em.createQuery("Select p From Proveedor p Where p.nombre_rs = :nombre_rs");
							query.setParameter("nombre_rs", cboProveedores.getSelectedItem().toString());
							Proveedor proveedor = (Proveedor) query.getSingleResult();
							
							try {
								BigDecimal pre_prod = new BigDecimal(Double.parseDouble(txtPrecio.getText()));
								
								Producto producto = new Producto();
								producto.setId_prod(id_prod);
								producto.setDes_prod(des_prod);
								producto.setStk_prod(stk_prod);
								producto.setPre_prod(pre_prod);
								producto.setCategoria(categoria);
								producto.setEst_prod((byte) 1);
								producto.setProveedor(proveedor);
								
								em.getTransaction().begin();
								em.persist(producto);
								em.getTransaction().commit();
								
								JOptionPane.showMessageDialog(this, "Producto registrado.");
							} catch(Exception e) {
								JOptionPane.showMessageDialog(this, "Error al ingresar el precio.");
							}
						} else {
							JOptionPane.showMessageDialog(this, "Seleccionar proveedor");
						}
					} catch(Exception e) {
						JOptionPane.showMessageDialog(this, "Error al ingresar el stock.");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Seleccionar categoría.");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Ingresar descripción.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Ingresar código.");
		}
		
		em.close();
		emf.close();
	}

	void listado() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		List<Producto> productos = new ArrayList<Producto>();
		Query query = em.createNamedQuery("Producto.findAll");
		for(Object item: query.getResultList()) {
			if(item instanceof Producto) {
				productos.add((Producto) item);
			}
		}
		
		txtSalida.setText("");
		for(Producto item: productos) {
			txtSalida.append("**************************************************\n");
			txtSalida.append("Id Producto : " + item.getId_prod() + "\n");
			txtSalida.append("Descripción : " + item.getDes_prod() + "\n");
			txtSalida.append("Stock : " + item.getStk_prod() + "\n");
			txtSalida.append("Precio : " + item.getPre_prod() + "\n");
			txtSalida.append("Categoría : " + item.getCategoria().getIdcategoria() + "-" + item.getCategoria().getDescripcion() + "\n");
			txtSalida.append("Estado : " + item.getEst_prod() + "\n");
			txtSalida.append("Proveedor : " + item.getProveedor().getIdproveedor() + "-" + item.getProveedor().getNombre_rs() + "\n");
		}
		
		em.close();
		emf.close();
	}
	
	void buscar() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		String id_prod = txtCodigo.getText();
		if(id_prod.length()>0) {
			try {
				Query query = em.createQuery("Select p From Producto p Where p.id_prod = :id_prod");
				query.setParameter("id_prod", id_prod);
				Producto producto = (Producto) query.getSingleResult();
				txtSalida.setText("");
				txtSalida.append("**************************************************\n");
				txtSalida.append("Id Producto : " + producto.getId_prod() + "\n");
				txtSalida.append("Descripción : " + producto.getDes_prod() + "\n");
				txtSalida.append("Stock : " + producto.getStk_prod() + "\n");
				txtSalida.append("Precio : " + producto.getPre_prod() + "\n");
				txtSalida.append("Categoría : " + producto.getCategoria().getIdcategoria() + "-" + producto.getCategoria().getDescripcion() + "\n");
				txtSalida.append("Estado : " + producto.getEst_prod() + "\n");
				txtSalida.append("Proveedor : " + producto.getProveedor().getIdproveedor() + "-" + producto.getProveedor().getNombre_rs() + "\n");
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this, "Producto no encontrado.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Ingresar código de producto.");
		}
		
		em.close();
		emf.close();
	}
}