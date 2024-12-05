package DB;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class DAO<T> implements IDAO<T> {
	protected JdbcTemplate jdbc;
	protected JdbcTemplate namedParameterJDBC;
	private String tableName;

	public DAO(String tableName, DataSource datasource){
		jdbc = new JdbcTemplate(datasource);
		namedParameterJDBC = new JdbcTemplate(datasource);
		this.tableName = tableName;
		// if (Consts.STORAGE_CHECK_CONSISTENCY) {
		// 	if (!this.isTableExist()) {
		// 		if (Consts.STORAGE_ALLOW_AUTOREPARATION) {
		// 			DebugTools.log("Creating " + tableName + " table");
		// 			this.create();
		// 		} else {
		// 			throw new Exception("Model table " + tableName + " does not exist");
		// 		}
		// 	}
		// }

		if (!this.isTableExist()) {
			this.createTable();
		}
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public boolean isTableExist() {
		String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ? AND table_schema = 'public'";
		return jdbc.queryForObject(query, Integer.class, tableName) == 1;
	}
}
