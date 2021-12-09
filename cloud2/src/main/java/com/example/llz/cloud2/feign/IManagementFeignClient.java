package com.example.llz.cloud2.feign;

import feign.Headers;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@FeignClient(value = "cloud1",path = "/cloud1")
public interface IManagementFeignClient {
    @RequestMapping(value = "/management/exportPersonForExcel", method = RequestMethod.GET)
    @Headers({"Content-Type: application/octet-stream;charset=UTF-8"})
    Response exportPersonForExcel() throws IOException;
}
