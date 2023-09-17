package model;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tb_usuarios")
@NamedQuery(name = "Usuario.findAll", query = "Select u From Usuario u")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cod_usua;
	@Column(length = 15)
	private String nom_usua;
	@Column(length = 25)
	private String ape_usua;
	@Column(nullable = false, columnDefinition = "CHAR(45)")
	private String usr_usua;
	@Column(columnDefinition = "CHAR(100)")
	private String cla_usua;
	@Column(columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	private Date fna_usua;
	@ManyToOne
	@JoinColumn(name = "idtipo")
	private Tipo tipo;
	@Column(nullable = true)
	private int est_usua;
}
