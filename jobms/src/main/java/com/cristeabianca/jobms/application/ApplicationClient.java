package com.cristeabianca.jobms.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userms", url = "http://localhost:8081")
public interface ApplicationClient {

    @GetMapping("/applications/{id}")
    ApplicationDTO getApplicationById(@PathVariable("id") Long id);
}
