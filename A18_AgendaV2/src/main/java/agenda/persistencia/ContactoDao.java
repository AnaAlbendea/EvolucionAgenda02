package agenda.persistencia;

import java.util.Set;

import agenda.modelo.Contacto;

public interface ContactoDao {

	public void insertar(Contacto c);
	
	public void actualizar(Contacto c);
	
	public boolean eliminar(int idContacto);
	
	public boolean eliminar(Contacto c);
	
	public Contacto buscar(int idContacto);
	//Debe retornar todos los contacto cuyo nombre,apellidos o apodo contenga
	//la cadena "cadena"
	public Set<Contacto> buscar(String cadena);
	
	public Set<Contacto> buscarTodos();
	
	
}
