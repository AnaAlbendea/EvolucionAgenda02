package agenda.vista;

import agenda.modelo.Contacto;
import agenda.negocio.Agenda;
import util.Util;

public class NuevoContacto {
	
	Agenda agenda;
	
	public NuevoContacto(Agenda agenda) {
		this.agenda = agenda;
		init();
	}
	
	public void init() {
		System.out.println("\nNUEVO CONTACTO");
		String nombre = Util.leerString("Nombre");
		String apellidos = Util.leerString("Apellidos");
		String apodo = Util.leerString("Apodo");
		String tel1 = Util.leerString("Telefono 1");
		String tel2 = Util.leerString("Telefono 2");
		
		Contacto nuevo = new Contacto();
		
		nuevo.setNombre(nombre);
		nuevo.setApellidos(apellidos);
		nuevo.setApodo(apodo);
		nuevo.addTelefono(tel1);
		nuevo.addTelefono(tel2);
		agenda.insertarContacto(nuevo);
	}
}
