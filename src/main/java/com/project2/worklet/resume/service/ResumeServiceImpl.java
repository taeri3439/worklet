package com.project2.worklet.resume.service;

import com.project2.worklet.component.ResumeVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resumeService")
public class ResumeServiceImpl implements ResumeService{


    @Autowired
    private ResumeMapper resumeMapper;



    @Override
    public int insertResume(ResumeVO resumeVO) {
        return resumeMapper.insertResume(resumeVO);
    }

    @Override
    public int updateResume(ResumeVO resumeVO) {
        return resumeMapper.updateResume(resumeVO);
    }

    @Override
    public int deleteResume(Long resumeId) {
        return resumeMapper.deleteResume(resumeId);
    }

    @Override
    public ResumeVO getResumeById(Long resumeId) {
        return resumeMapper.getResumeById(resumeId);
    }

    @Override
    public List<ResumeVO> getResumesByUserNum(int userNum) {
        return resumeMapper.getResumesByUserNum(userNum);
    }

    @Override
    public int saveResume(ResumeVO resume) {
        return resumeMapper.saveResume(resume);
    }

    @Override
    public ResumeVO getResumeByResumeId(Long resumeId) {
        return resumeMapper.getResumeByResumeId(resumeId);
    }
}
