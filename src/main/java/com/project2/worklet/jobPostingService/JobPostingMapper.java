package com.project2.worklet.jobPostingService;


import com.project2.worklet.component.JobPostingDetailVO;
import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.util_interceptor.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface JobPostingMapper {
    public int selectSeqMax();
    public int postList(List<JobPostingVO2> list);
    public int postDetail(List<JobPostingDetailVO> list);
    public List<JobPostingVO2> getList(@Param("cri") Criteria cri,
                                       @Param("userId") String userId);
    public int getTotal(Criteria cri);

    public List<JobPostingVO2> getRecentJobPostings();


    int scrapJob(@Param("userId") String userId,
                 @Param("empNo") String empNoToScrap);
    int unscrapJob(@Param("userId") String userId,
                   @Param("empNo") String empNoToScrap);

}
