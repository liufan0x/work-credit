package com.anjbo.chromejs;

public class DAOFactory {
	private static ChromeJsProcessorDAO chromeJsProcessorDAO;
	public static ChromeJsProcessorDAO getProcessorDAO(){
		if (chromeJsProcessorDAO == null) {
			chromeJsProcessorDAO = new ChromeJsProcessorDAOImpl();
		}
		return chromeJsProcessorDAO;
	}
}
