package com.project2.worklet.jobcategory;

import com.project2.worklet.component.JobCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCategoryServiceImpl implements JobCategoryService {

    @Autowired
    private JobCategoryMapper jobCategoryMapper;

    @Override
    public List<JobCategoryVO> getAllJobCategories() {
        return jobCategoryMapper.getAllJobCategories();
    }

    @Override
    public List<JobCategoryVO> getSecondaryCategories(String primaryCategoryNum) {
        return jobCategoryMapper.getSecondaryCategories(primaryCategoryNum);
    }


}
