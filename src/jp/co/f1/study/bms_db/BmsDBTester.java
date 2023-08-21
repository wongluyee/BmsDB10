package jp.co.f1.study.bms_db;

public class BmsDBTester {
	public static void main(String[] args) {
		BmsFunctionDB bms = new BmsFunctionDB();
		bms.displayMenu();
		while (bms.selectFunctionFromMenu() != 9) {
			bms.displayMenu();
		}
	}
}
