package jp.co.f1.study.bms_db;

import jp.co.f1.study.common.KeyIn;

public class BmsFunctionDB {
	private KeyIn objKeyIn = new KeyIn();

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
		case 4 -> System.out.println("Show all books");
		case 9 -> System.out.println("**処理を終了しました**");
		default -> System.out.println("正しいメニュー番号を入力してください。");
		}

		return inputNum;
	}
}
