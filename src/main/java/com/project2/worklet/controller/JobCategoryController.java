package com.project2.worklet.controller;

import com.project2.worklet.component.JobCategoryVO;
import com.project2.worklet.jobcategory.JobCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/category")
public class JobCategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @GetMapping("/all")
    public List<JobCategoryVO> getAllJobCategories() {
        return jobCategoryService.getAllJobCategories();
    }

    @GetMapping("/secondary")
    public List<JobCategoryVO> getSecondaryJobCategories(@RequestParam("primary") String primaryCategoryNum) {
        return jobCategoryService.getSecondaryCategories(primaryCategoryNum);
    }


}
