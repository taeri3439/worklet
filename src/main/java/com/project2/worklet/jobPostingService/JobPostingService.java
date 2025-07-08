package com.project2.worklet.jobPostingService;

import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.util_interceptor.Criteria;

import java.util.List;

public interface JobPostingService {
    public int selectSeqMax(); //db에서 저장된 채용공고의 seq중 가장큰값 가져옴
    public String postList();//매일 정해진 시간에 채용공고 api 100개씩 불러와서 db에 저장
    public int postDetail(List<String> list);
    public List<JobPostingVO2> getList(Criteria cri, String userId);
    int getTotal(Criteria cri);

    public List<JobPostingVO2> getRecentJobPostings();


    int scrapJob(String userId, String empNoToScrap);
    int unscrapJob(String userId, String empNoToScrap);


}
