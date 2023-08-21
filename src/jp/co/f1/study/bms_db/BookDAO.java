package jp.co.f1.study.bms_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookDAO {
	private static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost/mybookdb?characterEncoding=utf8";
	private static String USER = "root";
	private static String PASS = "password";

	// データベースに接続するクラスメソッド
	private static Connection getConnection() {
		try {
			// JDBCドライバーを読み込む
			Class.forName(RDB_DRIVE);

			// Connectionする
			Connection con = DriverManager.getConnection(URL, USER, PASS);

			return con;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public ArrayList<Book> selectAll() {

		Connection con = null;
		Statement smt = null;

		// bookListを生成する
		ArrayList<Book> bookList = new ArrayList<Book>();

		try {
			String sql = "SELECT * FROM bookinfo";
			con = getConnection();
			smt = con.createStatement();
			ResultSet rs = smt.executeQuery(sql);

			// Bookインスタンス化して、各データを格納して、bookListに追加する
			while (rs.next()) {
				Book book = new Book();
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setPrice(rs.getInt("price"));
				bookList.add(book);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			if (smt != null) {
				try {
					smt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		return bookList;
	}
}
