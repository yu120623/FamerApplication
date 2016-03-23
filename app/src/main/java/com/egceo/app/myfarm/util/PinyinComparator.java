package com.egceo.app.myfarm.util;

import com.egceo.app.myfarm.entity.Code;

import java.util.Comparator;

public class PinyinComparator implements Comparator<Code> {

	public int compare(Code o1, Code o2) {
		String pinyin1 = CharacterParser.getInstance().getSelling(o1.getCodeDesc()).substring(0, 1).toUpperCase();
		String pinyin2 = CharacterParser.getInstance().getSelling(o2.getCodeDesc()).substring(0, 1).toUpperCase();
		if (pinyin1.equals("@")
				|| pinyin2.equals("#")) {
			return -1;
		} else if (pinyin1.equals("#") || pinyin2.equals("@")) {
			return 1;
		} else {
			return pinyin1.compareTo(pinyin2);
		}
	}

}
