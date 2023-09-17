package model;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tb_tipos")
@NamedQuery(name = "Tipo.findByDescripcion", query = "Select t From Tipo t Where t.descripcion = :descripcion")
public class Tipo {

	@Id
	private int idtipo;
	@Column(length = 15)
	private String descripcion;
	@OneToMany(mappedBy = "tipo")
	private List<Usuario> usuarios;
}
