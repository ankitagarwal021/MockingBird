package com.pack.controller;

import com.pack.service.impl.MockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created By: Ankit Agarwal
 **/

@RestController("/mock")
public class MockController {

    @Autowired
    MockServiceImpl mockService;

    @RequestMapping("/**")
    public Object mockResponse(@RequestBody HttpServletRequest request, String data, @RequestParam Map<String,String> queryParams
            ,@RequestHeader Map<String, String> header){
        return mockService.getMockResponse(request,data,queryParams,header);
    }

}
