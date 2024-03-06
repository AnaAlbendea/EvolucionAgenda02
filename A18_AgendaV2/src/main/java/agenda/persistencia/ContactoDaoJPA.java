package agenda.persistencia;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import agenda.modelo.Contacto;
import es.cursogetafe.agenda.EMF;

public class ContactoDaoJPA implements ContactoDao {
	private EntityManager em;
	@Override
	public void insertar(Contacto c) {
		EntityManager em= EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		em.close();
		
	}

	@Override
	public void actualizar(Contacto c) {
		EntityManager em= EMF.getInstance().createEntityManager();
		em.getTransaction().begin();
		em.merge(c);
		em.getTransaction().commit();
		em.close();
		
	}

	@Override
	public boolean eliminar(int idContacto) {
		em=EMF.getInstance().createEntityManager();
		Contacto c=em.find(Contacto.class, idContacto); //si no existe devuelve un null
		em.close();
		if(c!=null) {
			return eliminar(c);
		
		} else  
			return false;
		
	}

	@Override
	public boolean eliminar(Contacto c) {
		em=EMF.getInstance().createEntityManager();
		Contacto buscado=em.find(Contacto.class, c.getIdContacto());
		if(buscado!=null) {
			em.getTransaction().begin();
			em.remove(buscado);
			em.getTransaction().commit();	
			return true;
		}
		em.close();
		return false;
	}

	@Override
	public Contacto buscar(int idContacto) {
		em=EMF.getInstance().createEntityManager();
		String jpql="select c from Contacto c "
				+ "left join fetch c.telefonos "
				+ " left join fetch c.correos "
				+ " where c.idContacto=:id"; 
		//Contacto c=em.find(Contacto.class, idContacto); /// busca las cosas por defecto Lazy y aqui es necesario Igger se hace con joinFech
		 TypedQuery<Contacto>q=em.createQuery(jpql,Contacto.class); //ejecuta la query
		 q.setParameter("id", idContacto); //le pasamos los parametros
		 Contacto buscado;
		 try {
			 buscado=q.getSingleResult();
		 } catch(NoResultException e){
			 buscado=null;
			 
		 } finally {
			 em.close();
		 }
		return buscado;
	}

	@Override
	public Set<Contacto> buscar(String cadena) {
		em=EMF.getInstance().createEntityManager();
		String jpql="select c from Contacto c  where c.nombre like:cad"
				+ "  or c.apellidos like:cad "
				+ "or c.apodo like:cad";
		TypedQuery<Contacto>q=em.createQuery(jpql,Contacto.class);
		q.setParameter("cad","%" + cadena + "%");
		Set<Contacto>resu= new HashSet<Contacto>(q.getResultList()); 
		em.close();
		return resu;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		em=EMF.getInstance().createEntityManager();
		String jpql="select c from Contacto c";
		TypedQuery<Contacto>q=em.createQuery(jpql,Contacto.class);
		Set<Contacto>resu= new HashSet<Contacto>(q.getResultList()); 
		em.close();
		return resu;
	}
	
	

}
