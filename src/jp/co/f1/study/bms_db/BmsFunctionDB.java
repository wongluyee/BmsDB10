package jp.co.f1.study.bms_db;

import java.util.ArrayList;

import jp.co.f1.study.common.KeyIn;

public class BmsFunctionDB {
	private KeyIn objKeyIn = new KeyIn();

	//書籍データを格納するArrayListオブジェクト
	ArrayList<Book> bookList = new ArrayList<Book>();

	//DAOのオブジェクト
	BookDAO objDao = new BookDAO();

	// メニュー選択肢を表示
	public void displayMenu() {
		System.out.println("""
				***書籍管理MENU***
				1．登録
				2．削除
				3．変更
				4．一覧
				9．終了
				----------------------
				番号を選択してください⇒

				""");
	}

	// メニュー番号を選択し、該当する機能を呼び出す
	public int selectFunctionFromMenu() {
		int inputNum = objKeyIn.readInt();

		switch (inputNum) {
		case 1 -> System.out.println("Register new book");
		case 2 -> System.out.println("Delete book");
		case 3 -> System.out.println("Update book");
		case 4 -> listFunction();
		case 9 -> System.out.println("**処理を終了しました**");
		default -> System.out.println("正しいメニュー番号を入力してください。");
		}

		return inputNum;
	}

	public void bookListDisplay() {
		System.out.println("""
				***書籍一覧***
				No.		ISBN	Title	Price
				-----------------------------------
				""");

		for (int i = 0; i < bookList.size(); i++) {
			Book book = new Book();
			book = bookList.get(i);
			String isbn = book.getIsbn();
			String title = book.getTitle();
			int price = book.getPrice();
			System.out.println((i + 1) + ".	" + isbn + "	" + title + "	" + price);
		}
		System.out.println("----------------------------------");
	}

	public void listFunction() {
		// 全ての書籍データを取得し、bookListに格納する
		bookList = objDao.selectAll();
		bookListDisplay();
	}
}
