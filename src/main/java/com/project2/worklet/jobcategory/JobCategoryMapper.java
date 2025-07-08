package com.project2.worklet.jobcategory;

import com.project2.worklet.component.JobCategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobCategoryMapper {
    List<JobCategoryVO> getAllJobCategories();

    List<JobCategoryVO> getSecondaryCategories(String primaryCategoryNum);

}
