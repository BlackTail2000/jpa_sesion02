package model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_productos")
@NamedQuery(name = "Producto.findAll", query = "Select p From Producto p")
public class Producto {

	@Id
	@Column(columnDefinition = "CHAR(5)")
	private String id_prod;
	
	@Column(length = 45, nullable = false)
	private String des_prod;
	
	@Column(nullable = true)
	private int stk_prod;
	
	@Column(columnDefinition = "DECIMAL(8,2)", nullable = false)
	private BigDecimal pre_prod;
	
	@ManyToOne
	@JoinColumn(name = "idcategoria", nullable = true)
	private Categoria categoria;
	
	@Column(columnDefinition = "TINYINT(1)")
	private byte est_prod;
	
	@ManyToOne
	@JoinColumn(name = "idproveedor", nullable = true)
	private Proveedor proveedor;
}
