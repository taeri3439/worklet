package com.project2.worklet.resume.service;

import com.project2.worklet.component.ResumeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResumeMapper {

    // 이력서 등록
    int insertResume(ResumeVO resumeVO);

    // 이력서 수정
    int updateResume(ResumeVO resumeVO);

    // 이력서 삭제
    int deleteResume(Long resumeId);

    // 이력서 단건 조회
    ResumeVO getResumeById(Long resumeId);

    // 사용자별 이력서 목록 조회
    List<ResumeVO> getResumesByUserNum(int userNum);


    // ============= 중간 테이블 관련 메서드 =============

    // 학력 연관 추가
    int insertResumeEducation(@Param("resumeId") Long resumeId, @Param("educationId") Long educationId);

    // 경력 연관 추가
    int insertResumeCareer(@Param("resumeId") Long resumeId, @Param("careerId") Long careerId);

    // 자격증 연관 추가
    int insertResumeLicense(@Param("resumeId") Long resumeId, @Param("licenseId") Long licenseId);

    // 학력 연관 삭제
    int deleteResumeEducations(Long resumeId);

    // 경력 연관 삭제
    int deleteResumeCareers(Long resumeId);

    // 자격증 연관 삭제
    int deleteResumeLicenses(Long resumeId);


    // 이력서 저장
    int saveResume(ResumeVO resume);

    // 이력서 뿌리기
    ResumeVO getResumeByResumeId(Long resumeId);
}
