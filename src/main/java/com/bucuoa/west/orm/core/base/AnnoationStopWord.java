package com.bucuoa.west.orm.core.base;

import java.util.ArrayList;
import java.util.List;

public class AnnoationStopWord {
	
	static public List<String> getStopWords()
	{
		String[] words = new String[]{"serialVersionUID","isSerialVersionUID"};
		
		List<String> wordlist = new ArrayList<String>();
		for(String word:words)
		{
			wordlist.add(word.toLowerCase());
		}
		return wordlist;
	}

}
