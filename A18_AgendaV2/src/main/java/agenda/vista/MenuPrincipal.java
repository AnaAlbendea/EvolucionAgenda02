package agenda.vista;

import agenda.negocio.Agenda;
import agenda.negocio.AgendaImpl;
import util.Util;

public class MenuPrincipal {
	
	private Agenda agenda; //como atributo interface agen
	
	public MenuPrincipal() {
		agenda = new AgendaImpl();//esto crea dependencia
	}
	
	public void menu() {
		System.out.println("SUPER AGENDA XX 7 PLUS");
		System.out.println("----------------------");
		boolean salir = false;
		int opcion;
		
		do {
			System.out.println("\nMenu Principal");
			System.out.println("1 - Nuevo Contacto");
			System.out.println("2 - Buscar contactos");
			System.out.println("3 - Listar todos");
			System.out.println("4 - Eliminar Contacto");
			System.out.println("5 - Importar contactos");
			System.out.println("9 - Salir");
			System.out.println("Opción: ");
			opcion = Util.leerInt();
			
			switch (opcion) {
			case 1:
				new NuevoContacto(agenda);
				break;
			case 3:
				new ConsultarTodos(agenda);
				break;
			case 9:
				salir = true;
			}
		} while(!salir);
	}
}
