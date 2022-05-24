package com.gantz.microservice.videoservice.feign;

import com.gantz.microservice.videoservice.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient("category-service")
public interface CategoryClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/category/list")
    List<CategoryDTO> getCategories();

    @RequestMapping(method = RequestMethod.GET, value = "/api/category")
    List<CategoryDTO> findAllByIdIn(@RequestParam("ids") Collection<Integer> ids);

}
