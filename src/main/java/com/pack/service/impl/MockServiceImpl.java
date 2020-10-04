package com.pack.service.impl;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created By: Ankit Agarwal
 **/

public interface MockServiceImpl {

    public Object getMockResponse(HttpServletRequest request, String data, Map<String,String> queryParams,
                                  Map<String, String> header);

}
