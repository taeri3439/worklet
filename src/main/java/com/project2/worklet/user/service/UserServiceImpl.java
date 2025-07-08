package com.project2.worklet.user.service;



import com.project2.worklet.component.*;

import com.project2.worklet.jobPostingService.JobPostingMapper;
import com.project2.worklet.util_interceptor.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // application.properties에 정의된 값을 주입받음
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public int insertUser(UserVO user) {
        return userMapper.insertUser(user);
    }

    @Override
    public UserVO loginUser(Map<String, String> paramMap) {
        String userId = paramMap.get("userId");
        String userPw = paramMap.get("userPw");

        return userMapper.loginUser(userId, userPw);
    }

    @Override
    public UserVO getUserById(String userId) {
        return userMapper.getUserById(userId);
    }


    @Override
    public boolean existsByUserId(String userId) {
        int count = userMapper.existsByUserId(userId);
        return count > 0;
    }

    @Override
    public UserVO findUserByUserId(String email, String phone) {
        return userMapper.findUserByUserId(email, phone);
    }

    @Override
    public UserVO findUserByUserPw(String userId, String userEmail) {
        return userMapper.findUserByUserPw(userId, userEmail);
    }

    @Override
    public int updatePw(UserVO user) {
        return userMapper.updatePw(user);
    }

    @Override
    public int insertEdu(EduVO edu) {
        return userMapper.insertEdu(edu);
    }

    @Override
    public int updateEdu(EduVO edu) {
        return userMapper.updateEdu(edu);
    }

    @Override
    public int insertCareer(CareerVO career) {
        return userMapper.insertCareer(career);
    }

    @Override
    public int updateCareer(CareerVO career) {
        return userMapper.updateCareer(career);
    }

    @Override
    public List<EduVO> getUserEducation(int userNum, Long resumeId) {
        return userMapper.getUserEducation(userNum, resumeId);
    }

    @Override
    public List<CareerVO> getUserCareer(int userNum, Long resumeId) {
        return userMapper.getUserCareer(userNum, resumeId);
    }

    @Override
    public List<LicenseVO> getUserLicenses(int userNum, Long resumeId) {
        return userMapper.getUserLicenses(userNum, resumeId);
    }

    @Override
    public int deleteEducation(Long educationId) {
        return userMapper.deleteEducation(educationId);
    }

    @Override
    public int deleteCareer(Long careerId) {
        return userMapper.deleteCareer(careerId);
    }

    @Override
    public int updateUser(UserVO user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int insertLicense(LicenseVO license) {
        return userMapper.insertLicense(license);
    }

    @Override
    public int updateLicense(LicenseVO license) {
        return userMapper.updateLicense(license);
    }

    @Override
    public int deleteLicense(Long licenseId) {
        return userMapper.deleteLicense(licenseId);
    }

    @Override
    public List<JobPostingVO2> getScrappedJob(String userId, Criteria cri) {
        return userMapper.getScrappedJob(userId, cri);
    }

    @Override
    public List<JobPostingVO2> getRecommendedJob(List<String> preferredJobTypes, Criteria cri) {
        return userMapper.getRecommendedJob(preferredJobTypes, cri);
    }

    @Override
    public String saveProfileImage(MultipartFile file, int userNum, Long resumeId) throws IOException {
        System.out.println("파일 이름: " + file.getOriginalFilename());

        // 1. 저장 경로 설정 (File.separator 사용으로 OS 호환성 확보)
        // 정적 리소스 경로 설정
        String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath();


        File folder = new File(uploadDir);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            System.out.println("폴더 생성 여부 >> " + created);
        }

        System.out.println("폴더 존재? >> " + folder.exists());
        System.out.println("폴더 쓰기 가능?>> " + folder.canWrite());
        System.out.println("폴더 경로: >> " + folder.getAbsolutePath());

        // 2. 고유 파일명 생성
        String originalName = file.getOriginalFilename();
        String savedName = UUID.randomUUID() + "_" + originalName;

        // 3. 저장할 파일 객체 생성 (파일 경로 확인용 로그 포함)
        File targetFile = new File(folder, savedName);
        System.out.println("저장할 전체 경로: " + targetFile.getAbsolutePath());

        // 4. 실제 파일 저장
        file.transferTo(targetFile);

        // 5. 로그 확인
        System.out.println("userNum: " + userNum);
        System.out.println("resumeId: " + resumeId);
        System.out.println("savedName: " + savedName);

        // 6. DB 경로 업데이트
        userMapper.updatePhotoPath(userNum, savedName, resumeId);

        // 7. 클라이언트에 반환할 경로
        return "/image/" + savedName;
    }

}
