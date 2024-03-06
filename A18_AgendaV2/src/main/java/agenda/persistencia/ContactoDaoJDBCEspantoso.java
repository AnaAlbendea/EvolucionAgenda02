//package agenda.persistencia;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Set;
//import javax.sql.DataSource;
//
//import agenda.config.Config;
//import agenda.modelo.Contacto;
//
//public class ContactoDaoJDBCEspantoso implements ContactoDao {
//	private DataSource ds; //creamos una variable local
//	
//	public ContactoDaoJDBCEspantoso() {
//		ds= Config.getDataSource();
//	}
//
//	@Override
//	public void insertar(Contacto c) {
//		try (Connection cx=ds.getConnection()){
//			String sql="insert into values(null,'"+c.getNombre()+"'"); //por que JDBC esta preparado para String
//			
//			
//		}catch(SQLException e) {
//			e.printStackTrace();					
//		}
//	
//		
//	}
//
//	@Override
//	public void actualizar(Contacto c) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean eliminar(int idContacto) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean eliminar(Contacto c) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Contacto buscar(int idContacto) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Set<Contacto> buscar(String cadena) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Set<Contacto> buscarTodos() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
