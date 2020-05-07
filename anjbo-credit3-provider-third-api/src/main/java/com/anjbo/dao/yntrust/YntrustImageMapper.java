package com.anjbo.dao.yntrust;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/8.
 */
public interface YntrustImageMapper {

    List<Map<String,Object>> list(Map<String, Object> map);
    List<Map<String,Object>> lisMas(Map<String,Object> map);

    void insert(Map<String, Object> map);

    void batchInsert(List<Map<String, Object>> list);

    void delete(Map<String, Object> map);

    void batchDelete(Map<String, Object> map);

    void update(Map<String, Object> map);
    
    void updateIsPush(Map<String, Object> map);
}
