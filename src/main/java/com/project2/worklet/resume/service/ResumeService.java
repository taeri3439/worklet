package com.project2.worklet.resume.service;

import com.project2.worklet.component.ResumeVO;

import java.util.List;

public interface ResumeService {

    int insertResume(ResumeVO resumeVO);

    int updateResume(ResumeVO resumeVO);

    int deleteResume(Long resumeId);

    ResumeVO getResumeById(Long resumeId);

    List<ResumeVO> getResumesByUserNum(int userNum);

    // 자격증 저장
    int saveResume(ResumeVO resume);

    ResumeVO getResumeByResumeId(Long resumeId);
}
