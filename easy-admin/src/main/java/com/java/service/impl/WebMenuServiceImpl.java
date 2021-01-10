package com.java.service.impl;

import com.java.mapper.WebMenuMapper;
import com.java.service.WebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2020/11/23 22:14 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Service
public class WebMenuServiceImpl implements WebMenuService {

    @Autowired
    private WebMenuMapper webMenuMapper;

    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> findWebMenu(Integer startIndex, Integer pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", webMenuMapper.selectWebMenu(startIndex, pageSize));
        resultMap.put("total", webMenuMapper.selectCountWebMenu());
        return resultMap;
    }

    @Override
    public boolean saveWebMenu(String title, String url) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("title", title);
        parameterMap.put("url", url);
        parameterMap.put("menuType", "1");
        return webMenuMapper.insertWebMenu(parameterMap) == 1;
    }


    @Override
    public boolean modifyWebMenu(String title, String url, Long id) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("title", title);
        parameterMap.put("url", url);
        parameterMap.put("id", id);
        return webMenuMapper.updateWebMenu(parameterMap) == 1;
    }

    @Override
    public boolean removeWebMenu(String idStr) {
        return webMenuMapper.deleteWebMenu(idStr) >= 1;
    }

    @Override
    public Map<String, Object> findWebBanner(Integer startIndex, Integer pageSize) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> rows = webMenuMapper.selectWebBanner(startIndex, pageSize);
        int total = webMenuMapper.selectCountWebBanner();
        resultMap.put("rows", rows);
        resultMap.put("total", total);
        return resultMap;
    }
}
