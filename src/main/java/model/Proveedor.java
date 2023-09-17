package model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_proveedor")
@NamedQuery(name = "Proveedor.findAll", query = "Select p From Proveedor p")
public class Proveedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idproveedor;
	
	@Column(nullable = false, length = 45)
	private String nombre_rs;
	
	@Column(nullable = false, length = 10)
	private String telefono;
	
	@Column(nullable = false, length = 45)
	private String email;
	
	@OneToMany(mappedBy = "proveedor")
	private List<Producto> productos;
}
