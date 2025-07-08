package com.project2.worklet.user.service;


import com.project2.worklet.component.*;
import com.project2.worklet.util_interceptor.Criteria;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    // 회원가입
    int insertUser(UserVO user);

    // 로그인
    UserVO loginUser(Map<String, String> paramMap);

    UserVO getUserById(String userId);

    // 아이디 중복체크
    boolean existsByUserId(String userId);


    // 아이디 찾기
    UserVO findUserByUserId(String email, String phone);

    // 비밀번호 찾기
    UserVO findUserByUserPw(String userId, String userEmail);

    // 비밀번호 바꾸기
    int updatePw(UserVO user);

    // 학력 추가
    int insertEdu(EduVO edu);

    // 학력 수정
    int updateEdu(EduVO edu);

    // 경력 추가
    int insertCareer(CareerVO career);

    // 경력 수정
    int updateCareer(CareerVO career);

    // 학력 조회 추가
    List<EduVO> getUserEducation(int userNum, Long resumeId);

    // 경력 조회 추가
    List<CareerVO> getUserCareer(int userNum, Long resumeId);

    // 자격증 조회 추가
    List<LicenseVO> getUserLicenses(int userNum, Long resumeId);

    // 학력 삭제
    int deleteEducation(Long educationId);

    // 경력 삭제
    int deleteCareer(Long careerId);

    // 회원 수정
    int updateUser(UserVO user);

    // 자격증 추가
    int insertLicense(LicenseVO license);

    // 자격증 수정
    int updateLicense(LicenseVO license);

    // 자격증 삭제
    int deleteLicense(Long licenseId);

    //스크랩공고
    List<JobPostingVO2> getScrappedJob(String userId, Criteria cri);

    //추천공고
    List<JobPostingVO2> getRecommendedJob(List<String> preferredJobTypes, Criteria cri);


    // 사진 추가
    String saveProfileImage(MultipartFile file, int userNum, Long resumeId) throws IOException;


}
