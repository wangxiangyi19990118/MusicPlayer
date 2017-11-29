package dao;

import java.sql.SQLException;

public interface TablesBase <T>{
	public boolean insert(T obj) throws SQLException;
	public boolean update(T nobj);
	public boolean delete(int id);
	public T getById(int id);
}
