package es.cursogetafe.agenda;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
	 private static EntityManagerFactory emf; //configura el acceso a una base de datos para disponer un solo objeto de una clase se hace con singletone
	 
	 
	 private EMF() {}//asi evitamos que se cree más objeto de ella hacer un constructor por defecto
	 
	 public static EntityManagerFactory getInstance() { //metodo factoria
		 if(emf==null) {
			 emf=Persistence.createEntityManagerFactory("agenda"); 
		 }
		 
		 return emf;
	 }
}
