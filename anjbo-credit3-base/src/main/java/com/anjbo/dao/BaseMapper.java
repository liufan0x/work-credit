package com.anjbo.dao;

import java.util.List;

public interface BaseMapper<T> {
	
	T find(T dto);
	
	List<T> search(T dto);

	int count(T dto);
	
	int insert(T dto);

	int delete(T dto);

	int update(T dto);

    int batchInsert(List<T> list);
    
	int batchDelete(List<T> list);
	
    int batchUpdate(List<T> list);
    
}
