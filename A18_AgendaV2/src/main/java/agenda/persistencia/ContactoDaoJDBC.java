package agenda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import com.mysql.cj.protocol.Resultset;

import agenda.config.Config;
import agenda.excecepciones.ContactoPersistenceException;
import agenda.modelo.Contacto;

public class ContactoDaoJDBC implements ContactoDao {
	private DataSource ds;
	
	public ContactoDaoJDBC() {
		ds= Config.getDataSource();
	}

	@Override
	public void insertar(Contacto c) {
		String sql="insert into contactos values(null,?,?,?,?,?,?,?,?,?,?,?)"; //sentencia preconpiladas, el id se pone null para que se autoincremente
		
		try(Connection cx=ds.getConnection()){
			cx.setAutoCommit(false); //en esta conexion no estamos trabajando con el modo AutoCommit no se graba nada
			PreparedStatement ps= cx.prepareStatement(sql);// Objeto para la conexion
			ps.setString(1,c.getNombre());
			ps.setString(2, c.getApellidos());
			ps.setString(3, c.getApodo());
			ps.setString(4, c.getDom().getTipoVia());
			ps.setString(5, c.getDom().getVia());
			ps.setInt(6, c.getDom().getNumero());
			ps.setInt(7, c.getDom().getPiso());
			ps.setString(8, c.getDom().getPuerta());
			ps.setString(9, c.getDom().getCodigoPostal());
			ps.setString(10, c.getDom().getCiudad());
			ps.setString(11, c.getDom().getProvincia());
			
			int filas=ps.executeUpdate(); // int finla con este vemos las filas afectadas
			
			//faltan los tlf y correos
			ps.executeUpdate(); //ejecutas los datos del obejto preparedStatemnt
			
			if(filas==1) {
				PreparedStatement psId=cx.prepareStatement("Select LAST_INSERT_ID()"); //te devuleve el ultimo id generado
				ResultSet rs=psId.executeQuery();
				rs.next();
				int idGen=rs.getInt(1);// sin esto no devolveria nada
				sql="insert into telefonos values(null,?,?)";
				PreparedStatement psTel=cx.prepareStatement(sql);
				int canTel=0;
				for(String tel:c.getTelefonos()) { //recorremos la coleccion de tlf del contacto
					psTel.setInt(1, idGen); //esto es la foreing key
					psTel.setString(2, tel); //numero de tfl
					psTel.executeUpdate();
					canTel+=psTel.executeUpdate();
				}
				
				sql="insert into correos values(null,?,?)";
				PreparedStatement psCor=cx.prepareStatement(sql);
				int canCor=0;
				for(String correo:c.getCorreos()) { //recorremos la coleccion de tlf del contacto
					psCor.setInt(1, idGen); //esto es la foreing key
					psCor.setString(2, correo); //numero de tfl
					canCor+=psCor.executeUpdate();
				}
				if (canTel==c.getTelefonos().size() && canCor==c.getCorreos().size()) {
					cx.commit();
				
			}else {
				cx.rollback();
				throw new ContactoPersistenceException();
			}
			}else { 
				cx.rollback();
				throw new ContactoPersistenceException();
			}
			
			
		}catch (SQLException e) {
			 //hacer un log  fecha,hora,usuario,tipo de error
			e.printStackTrace();
			throw new ContactoPersistenceException();
		}
		
	}

	@Override
	public void actualizar(Contacto c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean eliminar(int idContacto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(Contacto c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Contacto buscar(int idContacto) {
		Contacto c=null;
		Set<Contacto>resu=buscarCondicion("idcontactos="+idContacto);
		for(Contacto contacto:resu) {
			c=contacto;
		}
		return c;
	}

	@Override
	public Set<Contacto> buscarTodos() {
	
		return buscarCondicion("true");
	}

	private Set<Contacto> buscarCondicion(String condicion) {
		 Set<Contacto> resu=new HashSet<>();
		 String sql="""
		 	SELECT idcontactos, nombre,apellidos,apodo,
		 		tipo_via, via, numero, piso,puerta, 
		 		codigo_postal,ciudad,provincia
		 		from contactos  WHERE
		 		"""+condicion+";";
		 try(Connection cx=ds.getConnection()){
			 PreparedStatement ps=cx.prepareStatement(sql);
			 ResultSet rs=ps.executeQuery();
			 while(rs.next()) {
				 Contacto c=new Contacto();
				 c.setIdContacto(rs.getInt("idContactos"));
				 c.setNombre(rs.getString("Nombre"));
				 c.setApellidos(rs.getString("apellidos"));
				 c.setApodo(rs.getString("apodo"));
				 
				 c.getDom().setTipoVia(rs.getString("Tipo_via"));
				 c.getDom().setVia(rs.getString("via"));
				 c.getDom().setNumero(rs.getInt("Numero"));
				 c.getDom().setPiso(rs.getInt("piso"));
				 c.getDom().setPuerta(rs.getString("Puerta"));
				 c.getDom().setCodigoPostal(rs.getString("Codigo_postal"));
				 c.getDom().setCiudad(rs.getString("ciudad"));
				 c.getDom().setProvincia(rs.getString("Provincia"));
				 
				 sql="Select telefono from telefonos where fk_contacto=?";
				 PreparedStatement psT=cx.prepareStatement(sql);
				 psT.setInt(1, c.getIdContacto());//si es la primera vez
				 ResultSet rsT=psT.executeQuery();//es un objeto que representa una tabla de los tlf
				 while(rsT.next()) {
					 c.addTelefono(rsT.getString("telefono"));
				 }
				 sql="Select correo from correos where fk_contacto=?";
				 PreparedStatement psC=cx.prepareStatement(sql);
				 psC.setInt(1, c.getIdContacto());//si es la primera vez
				 ResultSet rsC=psC.executeQuery();//es un objeto que representa una tabla de los tlf
				 while(rsC.next()) {
					 c.addCorreo(rsC.getString("Correo"));
				 }
				 resu.add(c);
			 }
		 } catch (SQLException e) {
			e.printStackTrace();
			throw new ContactoPersistenceException();
		}
		 
		return resu;
		 		
		 	
	}

	@Override
	public Set<Contacto> buscar(String cadena) {
		String cond="nombre like '%"+cadena+"%' or "+
					"apellidos like '%" +cadena +"%' or "+
					"apodo like '%"+ cadena +"%'";
		return buscarCondicion(cond);
	}

}
