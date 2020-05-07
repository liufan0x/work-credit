package com.anjbo.execute;
/**
 * 队列对象
 * @ClassName: ExecuteData 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author limh@anjbo.com
 * @date 2018年5月22日 下午8:42:20 
 * 
 * @param <T>
 */
public class ExecuteData<T> {
	
	public static final int NOT_READ = 0;
	public static final int ALREADY_READ = 1;
	public static final int COMPLETE = 2;
	public static final int FAILDED = 3;
	
	private String id;
	private long time;
	private int status;
	private String msg;
	private T data;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ExecuteData [id=" + id + ", time=" + time+ ", status=" + status + ", msg=" + msg  + "]";
	}
	
}
