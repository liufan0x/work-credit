package com.anjbo.chromejs;

public interface ChromeNotify {
	void tooManyChrome();
	void noChrome();
	void writeUrlTag(String tag,String time,String filePath);
	String getValue(String tag,String filePath);
}
