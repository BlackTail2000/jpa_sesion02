package app;

import java.util.Calendar;

import jakarta.persistence.*;

import model.*;

public class Demo01 {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ciberfarmadawi_pu");
		EntityManager em = emf.createEntityManager();
		
		Usuario usuario = new Usuario();
		usuario.setNom_usua("Carlos Gabriel");
		usuario.setApe_usua("Baca Manrique");
		usuario.setUsr_usua("carlosgabrielbacamanrique@hotmail.com");
		usuario.setCla_usua("123");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2000);
		calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 19);
		usuario.setFna_usua(calendar.getTime());
		usuario.setEst_usua(1);
		
		Tipo tipo = em.find(Tipo.class, 1);
		usuario.setTipo(tipo);
		
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
