package agenda.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class Config {

	private static DataSource ds; //metodo singlestone
	private static Properties prop;
	
	private Config() {
		
	}
	
	public static DataSource getDataSource() { //metodo factoria, que fabrica un objeto
		if(ds==null) {
			BasicDataSource bds=new BasicDataSource();
			bds.setDriverClassName(getProp().getProperty("bbdd.driver"));
			bds.setUrl(getProp().getProperty("bbdd.url"));
			bds.setUsername(getProp().getProperty("bbdd.user"));
			bds.setPassword(getProp().getProperty("bbdd.pass"));
			ds=bds;
		}
		return ds;
	}
	public static Properties getProp()  { //creamos otro singletone para  el fichero properties
		if(prop==null) { //la clase properties es un map
			prop=new Properties();
			try (FileReader fr=new FileReader("agenda.properties")) { //abre el fichero modo lectura
				prop.load(fr); //metemos el archivo properties				
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Problema con el fichero properties"); //si no hay fichero se lanza el mensaje con la exception uncatche
			}
		}
		return prop;
		
	}
}
