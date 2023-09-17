package model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_categorias")
@NamedQuery(name = "Categoria.findAll", query = "Select c From Categoria c")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcategoria;
	
	@Column(length = 45)
	private String descripcion;
	
	@OneToMany(mappedBy = "categoria")
	private List<Producto> productos;
}
