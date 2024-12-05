package DB;

import java.util.List;

public interface IDAO<T> {


	public void createTable();

    public void create(T user);

    public T getById(Long id);

    public List<T> getAll();

    public void update(T user);

    public void delete(Long id);

    public boolean isExists(Long id);

	public String getTableName();

	public boolean isTableExist();

}
