package agenda.persistencia;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import agenda.modelo.Contacto;

public class ContactoDaoMemDinamica implements ContactoDao {
	private int proximoId=1;
	private Map<Integer,Contacto>almacen;
	
	
	public  ContactoDaoMemDinamica() {
		 almacen=new HashMap<>();	//se crea el objeto
	}
	
	//General el ID que tiene que ser cero
	//añadir un elemento en el map
	@Override
	public void insertar(Contacto c) {
		c.setIdContacto(proximoId++);
		almacen.put(c.getIdContacto(), c);
		
	}

	@Override
	public void actualizar(Contacto c) {
		 almacen.replace(c.getIdContacto(), c);
	 }
		

	@Override
	public boolean eliminar(int idContacto) {
	return	almacen.remove(idContacto)!=null; //si devuelve un null es que no se elimino
		 
	}

	@Override
	public boolean eliminar(Contacto c) {
		return eliminar(c.getIdContacto());
	}

	@Override
	public Contacto buscar(int idContacto) {
		return  almacen.get(idContacto);
		
	}

	@Override
	public Set<Contacto> buscar(String cadena) {
		cadena=cadena.toLowerCase();
		Set<Contacto> resu=new HashSet<Contacto>();
		for(Contacto c:almacen.values()) {
			if (c.getNombre().toLowerCase().contains(cadena)||c.getApellidos().toLowerCase().contains(cadena)||c.getApodo().toLowerCase().contains(cadena)) {
				resu.add(c);
			}
		}
		return resu;		
	}

	@Override
	public Set<Contacto> buscarTodos() {
		return new HashSet<Contacto>(almacen.values());
		
	}

}


//tipo de collecion  set/list/ o map, list descartado, set no por que toda las busquedas serian secuenciasles mejor map