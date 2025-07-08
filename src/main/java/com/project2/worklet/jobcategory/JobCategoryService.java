package com.project2.worklet.jobcategory;

import com.project2.worklet.component.JobCategoryVO;

import java.util.List;


public interface JobCategoryService {
    List<JobCategoryVO> getAllJobCategories();

    List<JobCategoryVO> getSecondaryCategories(String primaryCategoryNum);

}
