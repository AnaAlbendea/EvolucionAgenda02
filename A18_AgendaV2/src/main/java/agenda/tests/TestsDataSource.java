package agenda.tests;

import java.sql.Connection;		
import javax.sql.DataSource;
import agenda.config.Config;
import java.sql.SQLException;

public class TestsDataSource {

	public static void main(String[] args) throws SQLException {
		DataSource ds=Config.getDataSource();
		Connection cx=ds.getConnection();
		cx.close();
		System.out.println("Todo Ok");

	}

}
