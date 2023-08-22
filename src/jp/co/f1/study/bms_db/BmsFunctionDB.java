package jp.co.f1.study.bms_db;

import java.util.ArrayList;

import jp.co.f1.study.common.KeyIn;

public class BmsFunctionDB {
	private KeyIn objKeyIn = new KeyIn();
	private final String TAB = "\t";

	//書籍データを格納するArrayListオブジェクト
	ArrayList<Book> bookList = new ArrayList<Book>();

	//DAOのオブジェクト
	BookDAO objDao = new BookDAO();

	// メニュー選択肢を表示
	public void displayMenu() {
		System.out.print("""
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
		case 1 -> addFunction();
		case 2 -> deleteFunction();
		case 3 -> updateFunction();
		case 4 -> listFunction();
		case 9 -> System.out.println("**処理を終了しました**");
		default -> System.out.println("正しいメニュー番号を入力してください。");
		}

		return inputNum;
	}

	public void bookListDisplay() {
		System.out.println("***書籍一覧***");
		System.out.println("No. " + "ISBN" + TAB + "Title" + TAB + "Price");
		System.out.println("----------------------------------");

		for (int i = 0; i < bookList.size(); i++) {
			Book book = new Book();
			book = bookList.get(i);
			String isbn = book.getIsbn();
			String title = book.getTitle();
			int price = book.getPrice();
			System.out.println((i + 1) + ".	" + isbn + TAB + title + TAB + price);
		}

		System.out.println("----------------------------------");
	}

	public void listFunction() {
		// 全ての書籍データを取得し、bookListに格納する
		bookList = objDao.selectAll();
		bookListDisplay();
	}

	public void addFunction() {
		// bookオブジェクトを作成する
		Book book = new Book();

		// ユーザが入力した情報をbookの各フィールドに格納する
		System.out.println("***書籍情報登録***");
		System.out.println("ISBNを入力してください。");
		System.out.println("【ISBN】⇒");
		String isbn = objKeyIn.readKey();
		book.setIsbn(isbn);

		System.out.println("タイトルを入力してください。");
		System.out.println("【タイトル】⇒");
		String title = objKeyIn.readKey();
		book.setTitle(title);

		System.out.println("価格を入力してください。");
		System.out.println("【価格】⇒");
		int price = objKeyIn.readInt();
		book.setPrice(price);

		// データベースに登録する
		objDao.insert(book);

		// 登録情報をコンソールに表示する
		System.out.println();
		System.out.println("***登録済書籍情報***");
		System.out.println("ISBN" + TAB + "Title" + TAB + "Price");
		System.out.println("---------------------------------");
		System.out.println(isbn + TAB + title + TAB + price);
		System.out.println("---------------------------------");
		System.out.println("上記書籍が登録されました。");
		System.out.println();
	}

	public void deleteFunction() {
		listFunction();
		System.out.println();
		System.out.println("***削除対象の書籍選択***");
		System.out.println("削除したい書籍（ISBN）を選択してくだいさい⇒");

		// 削除対象の書籍データの取得する
		String isbn = objKeyIn.readKey();
		Book book = new Book();
		book = objDao.selectByIsbn(isbn);

		System.out.println("***削除対象書籍情報***");
		System.out.println("ISBN" + TAB + "Title" + TAB + "Price");
		System.out.println("---------------------------------");
		System.out.println(book.getIsbn() + TAB + book.getTitle() + TAB + book.getPrice());
		System.out.println("---------------------------------");
		System.out.println("上記書籍を削除しますか（y/n）？");

		String confirm = objKeyIn.readKey();

		if (confirm.toLowerCase().equals("y")) {
			objDao.delete(book);
			System.out.println("ISBN: " + isbn + "の書籍が削除されました");
			System.out.println();
		} else {
			displayMenu();
		}
	}

	public void updateFunction() {
		listFunction();

		System.out.println("***変更対象書籍情報***");
		System.out.println("変更したい書籍（ISBN）を選択してください⇒");
		String isbn = objKeyIn.readKey();
		Book book = new Book();
		book = objDao.selectByIsbn(isbn);
		String oldTitle = book.getTitle();
		int oldPrice = book.getPrice();

		System.out.println("タイトル【" + oldTitle + "】変更⇒");
		String title = objKeyIn.readKey();
		book.setTitle(title);

		System.out.println("価格【" + oldPrice + "】変更⇒");
		int price = objKeyIn.readInt();
		book.setPrice(price);
		
		// DBに保存する
		objDao.update(book, isbn);

		System.out.println();
		System.out.println("更新済書籍情報");
		System.out.println("下記のように書籍情報が更新されました。");
		System.out.println("---------------------------------");
		System.out.println(TAB + "変更前" + TAB + "変更後");
		System.out.println("ISBN" + TAB + book.getIsbn() + " → " + TAB + book.getIsbn());
		System.out.println("Title" + TAB + oldTitle + " → " + TAB + book.getTitle());
		System.out.println("Price" + TAB + oldPrice + " → " + TAB + book.getPrice());
		System.out.println("---------------------------------");

	}
}
