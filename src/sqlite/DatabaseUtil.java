package sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DatabaseUtil {
	public static void main(String[] args) throws Exception {
		getConnection();
	}
	public static String URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String DRIVER;

	private DatabaseUtil() {
	}

	static {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:aa.db");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接的方法
	 *
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:aa.db");
			Statement stat = conn.createStatement();

/*			stat.executeUpdate( "CREATE TABLE \"music\" (\n" +
					"    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
					"    \"md5value\" TEXT NOT NULL DEFAULT (''),\n" +
					"    \"name\" TEXT DEFAULT (''),\n" +
					"    \"singer\" TEXT DEFAULT (''),\n" +
					"    \"musicUrl\" TEXT NOT NULL DEFAULT ('')\n" +
					");" );//创建一个表，两列
			stat.executeUpdate("CREATE TABLE \"musicsheet\" (\n" +
					"    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
					"    \"uuid\" TEXT NOT NULL DEFAULT (''),\n" +
					"    \"name\" TEXT DEFAULT (''),\n" +
					"    \"creatorId\" TEXT DEFAULT (''),\n" +
					"    \"creator\" TEXT DEFAULT (''),\n" +
					"    \"dateCreated\" TEXT DEFAULT (''),\n" +
					"    \"pictureUrl\" TEXT DEFAULT ('')\n" +
					");");
			stat.executeUpdate("CREATE TABLE \"musicsheet_music\" (\n" +
					"    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
					"    \"musicId\" INTEGER NOT NULL REFERENCES \"music\"(id) ON UPDATE CASCADE ON DELETE CASCADE,\n" +
					"    \"musicsheetId\" INTEGER NOT NULL REFERENCES \"musicsheet\"(id) ON UPDATE CASCADE ON DELETE CASCADE\n" +
					");");
*/
			ResultSet rs = stat.executeQuery("select * from music;"); //查询数据

			while (rs.next()) { //将查询到的数据打印出来

				System.out.print("name = " + rs.getString("name") + " "); //列属性一

				System.out.println("salary = " + rs.getString("id")); //列属性二

			}
			rs.close();
			conn.close(); //结束数据库的连接
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Get Database Connection failed.");
		}

		return conn;
	}

	/**
	 * 关闭数据库连接
	 *
	 */
	public static void close(ResultSet rs, Statement stat, Connection conn) {

		try {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
