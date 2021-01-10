package com.java.service.impl;

import com.java.mapper.WebBannerMapper;
import com.java.service.WebBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Description:	   <br/>
 * Date:     2021/01/07 20:16 <br/>
 *
 * @author Aaron CHEN
 * @see
 */
@Service
@Transactional(readOnly = false)
public class WebBannerServiceImpl implements WebBannerService {

    @Autowired
    private WebBannerMapper webBannerMapper;

    @Override
    public List<Map<String, Object>> findWebBanner() {
        return webBannerMapper.selectWebBanner();
    }


}
