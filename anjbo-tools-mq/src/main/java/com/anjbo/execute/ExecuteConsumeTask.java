package com.anjbo.execute;


public  class ExecuteConsumeTask {
	
	private String message;
	private ConsumeTask task;
	
	public ExecuteConsumeTask (ConsumeTask task) throws Exception{
		task.executeConsume(message); 
		this.task=task;
	}

	public synchronized ConsumeTask getTask() {
		return task;
	}

	public void setTask(ConsumeTask task) {
		this.task = task;
	}
	
	
	

}
