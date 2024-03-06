package agenda.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import agenda.modelo.Contacto;

public class ContactoDaoMemSerial implements ContactoDao {
	private Integer proximoId;
	private final String F_MAPA="mapa.dat";
	private final String F_IDX_="indice.dat";
	private Map<Integer,Contacto>almacen;
	
	
	public  ContactoDaoMemSerial() { //solo lee el fichero porque no hay nada
		 try(FileInputStream fis=new FileInputStream(F_MAPA);//leer ficheros binarios del disco
			 FileInputStream fisIdx=new FileInputStream(F_IDX_)){
			ObjectInputStream ois=new ObjectInputStream(fis);//este se conecta fileinputStream lee bites
			 almacen=(Map<Integer,Contacto>)ois.readObject();//aqui me entrega el objeto pero con referencia de tipo object y le hacemos cast y lo tenemos que referencia con Map
			 ObjectInputStream oisIdx=new ObjectInputStream(fisIdx);//aqui creamos un nuevo objeto con el indice
			 proximoId=(Integer) oisIdx.readObject();
			 //Si no existe el fichero creamos una nuevo almacen
		} catch (FileNotFoundException e) {
			almacen=new HashMap<> ();
			proximoId=1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	private void grabar() { //aqui vamos a guardar
		try(FileOutputStream fos =new FileOutputStream(F_MAPA);
			FileOutputStream fosIdx =new FileOutputStream(F_IDX_)){
		   ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(almacen);// hay que saber que tipo de objeto se esta grabando y tienen que tener la interface seriabale
			oos=new ObjectOutputStream(fosIdx);
			oos.writeObject(proximoId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	//General el ID que tiene que ser cero
	//añadir un elemento en el map
	@Override
	public void insertar(Contacto c) {
		c.setIdContacto(proximoId++);
		almacen.put(c.getIdContacto(), c);
		grabar();
		
	}

	@Override
	public void actualizar(Contacto c) {
		 almacen.replace(c.getIdContacto(), c);
		 grabar();
	 }
		

	@Override
	public boolean eliminar(int idContacto) {
		Contacto eliminado=almacen.remove(idContacto);
		grabar();
		return	eliminado!=null; 
		 
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