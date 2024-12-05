package DB;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import DB.entities.*;
import java.util.List;

public class UserDAO  extends DAO<User> implements IDAO<User>{
    DataSource source;
    JdbcTemplate jdbcTemplate;

    public UserDAO(DataSource source) {
        super("users", source);

        this.source = source;
        jdbcTemplate = new JdbcTemplate(source);
    }


    @Override
	public void createTable() {
		String query = "CREATE TABLE " + getTableName() + "(pk BLOB PRIMARY KEY);";
		jdbc.update(query);
	}

    public void create(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.name, user.passwordHash);
    }

    public User getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public void update(User user) {
        String sql = "UPDATE users SET name = ?, passwordHash = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.name, user.passwordHash, user.id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean isExists(Long id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

}
