package jp.co.f1.study.bms_db;

import java.util.ArrayList;

import jp.co.f1.study.common.KeyIn;
import jp.co.f1.study.common.MyFormat;

public class BmsFunctionDB {
	private KeyIn objKeyIn = new KeyIn();
	private final String TAB = "\t";

	// 価格のフォーマット
	MyFormat fm = new MyFormat();

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
			String formattedPrice = fm.moneyFormat(price);
			System.out.println((i + 1) + ".	" + isbn + TAB + title + TAB + formattedPrice);
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
		String isbn;

		// ユーザが入力した情報をbookの各フィールドに格納する
		loopIsbn: while (true) {
			System.out.println("***書籍情報登録***");
			System.out.println("ISBNを入力してください。");
			System.out.println("【ISBN】⇒");
			isbn = objKeyIn.readKey();

			// 空文字かどうかチェック
			if (isbn.equals("")) {
				System.out.println("空文字が入力されました。ISBNを入力して下さい！");
				continue;
			}

			// ISBN重複しているかどうかチェック
			bookList = objDao.selectAll();
			for (int i = 0; i < bookList.size(); i++) {
				if (isbn.equals(bookList.get(i).getIsbn())) {
					System.out.println("入力ISBNは既に登録されています。:" + isbn);
					continue loopIsbn;
				}
			}

			book.setIsbn(isbn);
			break;
		}

		System.out.println("タイトルを入力してください。");
		System.out.println("【タイトル】⇒");
		String title = objKeyIn.readKey();
		book.setTitle(title);

		System.out.println("価格を入力してください。");
		System.out.println("【価格】⇒");
		int price = objKeyIn.readInt();
		book.setPrice(price);
		// 価格フォーマット
		String formattedPrice = fm.moneyFormat(price);

		// データベースに登録する
		objDao.insert(book);

		// 登録情報をコンソールに表示する
		System.out.println();
		System.out.println("***登録済書籍情報***");
		System.out.println("ISBN" + TAB + "Title" + TAB + "Price");
		System.out.println("---------------------------------");
		System.out.println(isbn + TAB + title + TAB + formattedPrice);
		System.out.println("---------------------------------");
		System.out.println("上記書籍が登録されました。");
		System.out.println();
	}

	public void deleteFunction() {
		listFunction();
		String isbn;
		Book book = new Book();

		while (true) {
			System.out.println();
			System.out.println("***削除対象の書籍選択***");
			System.out.println("削除したい書籍（ISBN）を選択してくだいさい⇒");

			// 削除対象の書籍データの取得する
			isbn = objKeyIn.readKey();
			book = objDao.selectByIsbn(isbn);

			// 削除対象のISBNが存在しない場合
			if (book.getIsbn() == null) {
				System.out.println("入力ISBN：" + isbn + "は存在しませんでした。");
				continue;
			}
			break;
		}

		String formattedPrice = fm.moneyFormat(book.getPrice());
		System.out.println("***削除対象書籍情報***");
		System.out.println("ISBN" + TAB + "Title" + TAB + "Price");
		System.out.println("---------------------------------");
		System.out.println(book.getIsbn() + TAB + book.getTitle() + TAB + formattedPrice);
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
		String isbn;
		Book book = new Book();

		while (true) {
			System.out.println("***変更対象書籍情報***");
			System.out.println("変更したい書籍（ISBN）を選択してください⇒");

			isbn = objKeyIn.readKey();
			book = objDao.selectByIsbn(isbn);

			// 変更対象のISBNが存在しない場合
			if (book.getIsbn() == null) {
				System.out.println("入力ISBN：" + isbn + "は存在しませんでした。");
				continue;
			}
			break;
		}

		// 後でコンソールに表示用
		String oldTitle = book.getTitle();
		int oldPrice = book.getPrice();
		String formattedOldPrice = fm.moneyFormat(oldPrice);

		System.out.println("タイトル【" + oldTitle + "】変更⇒");
		String title = objKeyIn.readKey();
		book.setTitle(title);

		System.out.println("価格【" + formattedOldPrice + "】変更⇒");
		int price = objKeyIn.readInt();
		book.setPrice(price);
		String formattedNewPrice = fm.moneyFormat(price);

		// DBに保存する
		objDao.update(book, isbn);

		System.out.println();
		System.out.println("更新済書籍情報");
		System.out.println("下記のように書籍情報が更新されました。");
		System.out.println("---------------------------------");
		System.out.println(TAB + "変更前" + TAB + "変更後");
		System.out.println("ISBN" + TAB + book.getIsbn() + " → " + TAB + book.getIsbn());
		System.out.println("Title" + TAB + oldTitle + " → " + TAB + book.getTitle());
		System.out.println("Price" + TAB + formattedOldPrice + " → " + TAB + formattedNewPrice);
		System.out.println("---------------------------------");

	}
}
