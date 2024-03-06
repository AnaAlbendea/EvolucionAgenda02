package agenda.tests;

import agenda.modelo.Contacto;
import agenda.persistencia.ContactoDao;
import agenda.persistencia.ContactoDaoMemArray;

public class TestContactoDaoMemArray {
	public static void main(String[] args) {
		
		ContactoDao dao = new ContactoDaoMemArray();

		System.out.println(dao.buscar(1));
		
		Contacto nuevo = new Contacto("Juan", "Perez", "Juanito");
		System.out.println(nuevo.getIdContacto()); // 0
		
		dao.insertar(nuevo);
		
		System.out.println(nuevo.getIdContacto()); // 1

		
		Contacto buscado = dao.buscar(1);
		System.out.println(buscado);
	}
}
