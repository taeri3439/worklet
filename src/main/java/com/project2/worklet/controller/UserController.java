package com.project2.worklet.controller;

import com.project2.worklet.component.*;

import com.project2.worklet.resume.service.ResumeService;
import com.project2.worklet.user.service.UserService;
import com.project2.worklet.util_interceptor.Criteria;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("resumeService")
    private ResumeService resumeService;


    @GetMapping("/regist")
    public String showRegistForm() {
        return "User/regist";  // 회원가입 폼을 보여줌
    }

    @PostMapping("/regist")
    public String regist(UserVO user, @RequestParam("wantJobType") String[] wantJobTypes,
                         @RequestParam(value = "preferredJobTypes", required = false) String[] preferredJobTypes) {

        log.info( user.toString() );
        user.setJoinDate(LocalDateTime.now());

        user.setWantJobType1(wantJobTypes.length > 0 ? wantJobTypes[0] : null);
        user.setWantJobType2(wantJobTypes.length > 1 ? wantJobTypes[1] : null);
        user.setWantJobType3(wantJobTypes.length > 2 ? wantJobTypes[2] : null);

        if (preferredJobTypes != null) {
            user.setPreferredJobType1(preferredJobTypes.length > 0 ? preferredJobTypes[0] : null);
            user.setPreferredJobType2(preferredJobTypes.length > 1 ? preferredJobTypes[1] : null);
            user.setPreferredJobType3(preferredJobTypes.length > 2 ? preferredJobTypes[2] : null);
        } else {
            user.setPreferredJobType1(null);
            user.setPreferredJobType2(null);
            user.setPreferredJobType3(null);
        }


        int result = userService.insertUser(user);
        if(result == 1) {
            log.info( user.toString() );
        } else {
            log.info( "실패");
        }

        return "redirect:/user/login";  // 로그인 페이지로 리다이렉트
    }


    @GetMapping("/login")
    public String login() {
        return "User/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId,
                        @RequestParam String userPw,
                        Model model,
                        HttpSession session) {


        // paramMap 만들어서 서비스로 전달
        Map<String, String> paramMap = Map.of(
                "userId", userId,
                "userPw", userPw
        );

        // 서비스 호출
        UserVO user = userService.loginUser(paramMap);

        if (user != null) {
            model.addAttribute("user", user);
            session.setAttribute("loginUser", user);
            session.setAttribute("userNum", user.getUserNum());
            session.setAttribute("userId",user.getUserId());
            System.out.println(">>> login – session: " + session.getId());
            System.out.println(">>> loginUser: " + session.getAttribute("loginUser"));
            return "redirect:/Board/mainPage";// 로그인 성공 시
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "User/login"; // 실패 시
        }
    }


//    @GetMapping("/mypage")
//    public String mypage(HttpSession session, Model model, Criteria cri) {
//
//        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
//
//        if(loginUser != null) {
//            String userId = loginUser.getUserId();
//
//            //스크랩공고 가져오기
//            List<JobPostingVO2> list = userService.getScrappedJob(userId, cri);
//            UserVO fullUser = userService.getUserById(loginUser.getUserId());
//            model.addAttribute("list", list);
//
//            //추천공고 가져오기
//            List<String> preferredJobTypes = new ArrayList<>();
//            preferredJobTypes.add(loginUser.getPreferredJobType1());
//            preferredJobTypes.add(loginUser.getPreferredJobType2());
//            preferredJobTypes.add(loginUser.getPreferredJobType3());
//            System.out.println("희망직업번호 가져와짐? "+preferredJobTypes.toString());
//            List<JobPostingVO2> recList = userService.getRecommendedJob(preferredJobTypes, cri);
//            System.out.println(recList.toString());
//
//            model.addAttribute("recList", recList);
//
//            List<String> wantJobTypes = new ArrayList<>();
//            if (fullUser.getWantJobType1() != null) wantJobTypes.add(fullUser.getWantJobType1());
//            if (fullUser.getWantJobType2() != null) wantJobTypes.add(fullUser.getWantJobType2());
//            if (fullUser.getWantJobType3() != null) wantJobTypes.add(fullUser.getWantJobType3());
//            fullUser.setWantJobType(wantJobTypes.toArray(new String[0]));
//
//            model.addAttribute("userVO", fullUser);
//            return "User/mypage";
//        }else {
//            return "redirect:/User/login";
//        }
//
//    }

    @GetMapping({"/resumePage", "/mypage"})
    public String mypage(HttpSession session, Model model, Criteria cri) {

        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if(loginUser != null) {
            String userId = loginUser.getUserId();

            //스크랩공고 가져오기
            List<JobPostingVO2> list = userService.getScrappedJob(userId, cri);
            UserVO fullUser = userService.getUserById(loginUser.getUserId());
            model.addAttribute("list", list);

            //추천공고 가져오기
            List<String> preferredJobTypes = new ArrayList<>();
            preferredJobTypes.add(loginUser.getPreferredJobType1());
            preferredJobTypes.add(loginUser.getPreferredJobType2());
            preferredJobTypes.add(loginUser.getPreferredJobType3());
            System.out.println("희망직업번호 가져와짐? "+preferredJobTypes.toString());
            List<JobPostingVO2> recList = userService.getRecommendedJob(preferredJobTypes, cri);
            System.out.println(recList.toString());

            model.addAttribute("recList", recList);

            List<String> wantJobTypes = new ArrayList<>();
            if (fullUser.getWantJobType1() != null) wantJobTypes.add(fullUser.getWantJobType1());
            if (fullUser.getWantJobType2() != null) wantJobTypes.add(fullUser.getWantJobType2());
            if (fullUser.getWantJobType3() != null) wantJobTypes.add(fullUser.getWantJobType3());
            fullUser.setWantJobType(wantJobTypes.toArray(new String[0]));

            model.addAttribute("userVO", fullUser);


            // 이력서
            int userNum = loginUser.getUserNum();
            System.out.println("로그인한 사용자: " + userNum);

            // 서비스에서 이력서 목록 가져오기
            List<ResumeVO> resumeList = resumeService.getResumesByUserNum(userNum);
            System.out.println("이력서 목록: " + resumeList);

            model.addAttribute("resumeList", resumeList);


            return "User/mypage";
        }else {
            System.out.println("로그인 정보가 없습니다.");
            return "redirect:/User/login";
        }

    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/Board/mainPage"; // 홈페이지로 리다이렉트
    }

    @GetMapping("/resume")
    public String resume(HttpSession session, Model model, @RequestParam long uniqueTime) {

        UserVO vo = (UserVO) session.getAttribute("loginUser");

        if(vo != null){
            UserVO fullUser = userService.getUserById(vo.getUserId());

            if (fullUser != null) {
                EduVO eduVO = new EduVO();

                // ✅ 추가: 학력 목록 조회해서 넣기
                List<EduVO> educationList = userService.getUserEducation(fullUser.getUserNum(), uniqueTime);
                System.out.println("uniqueTime>>>>>>>>>>>>!!!!!!!!!!!!!!>>"+uniqueTime);
                fullUser.setEducationList(educationList);

                // ✅ 추가: 경력 목록 조회해서 넣기
                List<CareerVO> careerList = userService.getUserCareer(fullUser.getUserNum(), uniqueTime);
                fullUser.setCareerList(careerList);

                // ✅ 추가: 경력 목록 조회해서 넣기
                List<LicenseVO> licenseList = userService.getUserLicenses(fullUser.getUserNum(), uniqueTime);
                fullUser.setLicenseList(licenseList);


                model.addAttribute("educationList", educationList);
                model.addAttribute("careerList", careerList);
                model.addAttribute("licenseList", licenseList);

                ResumeVO resume = resumeService.getResumeById(uniqueTime);
                model.addAttribute("resume", resume);
                model.addAttribute("growth", resume.getGrowth());
                model.addAttribute("studentDay", resume.getStudentDay());
                model.addAttribute("prosAndCons", resume.getProsAndCons());
                model.addAttribute("aspiration", resume.getAspiration());

            }


            if (fullUser.getUserBirthday() != null && !fullUser.getUserBirthday().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate birthday = LocalDate.parse(fullUser.getUserBirthday(), formatter);
                    int age = Period.between(birthday, LocalDate.now()).getYears();

                    model.addAttribute("userBirthday", birthday); // 생일 자체도 넘기고
                    model.addAttribute("age", age); // 만나이도 넘김
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            model.addAttribute("userVO", fullUser);
            return "User/resume";
        }else{
            return "redirect:/User/login";
        }

    }


    // 사진 업로드 처리
    @PostMapping("/uploadPhoto")
    public ResponseEntity<Map<String, Object>> uploadPhoto(
            @RequestParam("photo") MultipartFile file,
            @RequestParam("userNum") int userNum,
            @RequestParam("resumeId") Long resumeId) {

        Map<String, Object> response = new HashMap<>();
        try {
            // Service에서 파일을 저장하고 사진 경로를 반환
            String photoPath = userService.saveProfileImage(file, userNum, resumeId);

            response.put("success", true);
            response.put("photoPath", photoPath); // 클라이언트에 반환할 사진 경로
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "파일 업로드 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/id")
    public String id() {
        return "User/id";
    }

    @GetMapping("/pw")
    public String pw() {
        return "User/pw";
    }



    @GetMapping("/check-id")
    @ResponseBody
    public String checkIdDuplicate(@RequestParam String userId) {
        boolean isDuplicate = userService.existsByUserId(userId);

        System.out.println("아이디 중복 여부: " + isDuplicate);
        return isDuplicate ? "duplicate" : "available";
    }

    @GetMapping("/idSearch")
    public String idSearch(Model model) {


        UserVO userVO = new UserVO();
        model.addAttribute("userVO", userVO);

        return "User/idSearch";
    }

    @GetMapping("/pwSearch")
    public String pwSearch(Model model) {
        String userId = (String) model.getAttribute("userId");  // Model에서 userId 가져오기

        if (userId != null) {
            // userId를 사용하여 추가 로직을 처리할 수 있습니다.
        }

        return "User/pwSearch";
    }

    @GetMapping("/pwEdit")
    public String pwEditForm(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/pwEdit";
    }

    @PostMapping("/pwEdit")
    public String pwEdit(@RequestParam String userId,
                         @RequestParam String newPassword) {

        System.out.println("비밀번호 변경 시 userId: " + userId);

        UserVO vo = new UserVO();

        vo.setUserId(userId);
        vo.setUserPw(newPassword);

        int result = userService.updatePw(vo);
        System.out.println("비밀번호 변경 결과: " + result);
        if(result == 1) {
            return "redirect:/user/login";
        }else{
            return "redirect:/user/pwEdit";
        }


    }

    @GetMapping("/pwOk")
    public String pwOk() {
        return "User/pwOk";
    }

    @PostMapping("/pwOk")
    public String pwOk(@RequestParam("userId") String userId,
                       @RequestParam("userEmail") String userEmail,
                       Model model) {

        UserVO vo = userService.findUserByUserPw(userId, userEmail);

        if(vo != null){
            model.addAttribute("userVO", vo);
            model.addAttribute("userId", userId);
            return "User/pwSearch";
        }else{
            model.addAttribute("errorMessage", "비밀번호를 찾을 수 없습니다.");
            return "User/pwOk";
        }


    }

    @GetMapping("/idOk")
    public String idOk() {
        return "User/idOk";
    }

    @PostMapping("/idOk")
    public String idOk(@RequestParam("userEmail") String email,
                       @RequestParam("userPhone") String phone,
                       Model model) {

        UserVO vo = userService.findUserByUserId(email, phone);

        if (vo != null) {

            model.addAttribute("userVO", vo);
        } else {

            model.addAttribute("errorMessage", "아이디를 찾을 수 없습니다.");
        }
        return "User/idSearch";
    }

//    @PostMapping("/updateEdu")
//    public String updateEducation(EduVO edu,
//                                  @RequestParam("resumeId") Long resumeId,
//                                  @RequestParam("userNum") String userNum,
//                                  @RequestParam("schoolName") String schoolName,
//                                  @RequestParam("major") String major,
//                                  @RequestParam("part") String part,
//                                  @RequestParam("degreeType") String degreeType,
//                                  @RequestParam("graduationStatus") String graduationStatus,
//                                  @RequestParam("graduationDate") String graduationDate,
//                                  Model model) {
//
//        // userNum이 숫자인지 확인 (서비스에서 처리할 수도 있음)
//        if (!isValidUserNum(userNum)) {
//            log.error("유효하지 않은 userNum 값: " + userNum);
//            return "redirect:/error"; // 유효하지 않으면 에러 페이지로 리디렉션
//        }
//
//
//        // graduationDate 처리: String -> LocalDate 변환
//        LocalDate graduationDateLocal = null;
//
//        if (graduationDate != null && !graduationDate.isEmpty()) {
//            graduationDateLocal = convertStringToLocalDate(graduationDate);
//        } else {
//            // graduationDate가 빈 값일 경우 기본값 설정 (예: null, 특정 기본 날짜 등)
//            graduationDateLocal = LocalDate.now(); // 기본값을 오늘 날짜로 설정하는 예시
//        }
//
//        // EduVO 객체에 폼에서 받은 데이터를 설정
//        EduVO eduVO = new EduVO();
//        eduVO.setResumeId(resumeId);
//        eduVO.setUserNum(Integer.parseInt(userNum));
//        eduVO.setSchoolName(schoolName);
//        eduVO.setMajor(major);
//        eduVO.setPart(part);
//        eduVO.setDegreeType(degreeType);
//        eduVO.setGraduationStatus(graduationStatus);
//        eduVO.setGraduationDate(graduationDateLocal);
//
//        int result = userService.insertEdu(eduVO);
//
//        eduVO.setFormattedGraduationDate(graduationDateLocal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//
//
//        model.addAttribute("education", eduVO);
//
//        if (result > 0) {
//            log.info("학력 추가 성공");
//            log.info("eduVO 객체 데이터: " + eduVO);
//
//        } else {
//            log.error("학력 추가 실패");
//        }
//
//        return "redirect:/user/resume?uniqueTime=" + edu.getResumeId();
//    }


    // 유효한 userNum인지 확인하는 메서드
    private boolean isValidUserNum(String userNum) {
        return userNum != null && userNum.matches("\\d+");  // 숫자만 포함된 문자열 확인
    }

    // graduationDate 변환 메서드
    private LocalDate convertStringToLocalDate(String graduationDate) {
        if (graduationDate != null && !graduationDate.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(graduationDate, formatter);
            } catch (Exception e) {
                log.error("graduationDate 변환 실패: " + graduationDate, e);
                return null; // 예외 발생 시 null로 반환
            }
        }
        return null; // graduationDate가 없으면 null 반환
    }



    // 학력 수정 처리
    @PostMapping("/editEdu")
    public String editEducation(@RequestBody EduVO edu) {

        // graduationDate를 LocalDate로 변환
        LocalDate graduationDateLocal = convertStringToLocalDate(String.valueOf(edu.getGraduationDate()));

        // graduationDate를 설정
        edu.setGraduationDate(graduationDateLocal);

        // 데이터 처리 후 업데이트
        int result = userService.updateEdu(edu);
        if (result > 0) {
            log.info("학력 수정 성공");
        } else {
            log.error("학력 수정 실패");
        }

        return "redirect:/user/resume?uniqueTime=" + edu.getResumeId();
    }


    // 경력 추가 처리
//    @PostMapping("/updateCareer")
//    public String updateCareer(CareerVO career,
//                               @RequestParam("resumeId") Long resumeId,
//                               @RequestParam("userNum") String userNum,
//                               @RequestParam("companyName") String companyName,
//                               @RequestParam("department") String department,
//                               @RequestParam("position") String position,
//                               @RequestParam("joinDate") String joinDate,
//                               @RequestParam("quitDate") String quitDate,
//                               @RequestParam("jobDescription") String jobDescription,
//                               Model model) {
//
//        if (!isValidUserNum(userNum)) {
//            log.error("유효하지 않은 userNum 값: " + userNum);
//            return "redirect:/error"; // 유효하지 않으면 에러 페이지로 리디렉션
//        }
//
//        // graduationDate 처리: String -> LocalDate 변환
//        LocalDate joinDateLocal = convertStringToLocalDate(joinDate);
//        LocalDate quitDateLocal = convertStringToLocalDate(quitDate);
//
//        CareerVO careerVO = new CareerVO();
//        careerVO.setResumeId(resumeId);
//        careerVO.setUserNum(Integer.parseInt(userNum));
//        careerVO.setCompanyName(companyName);
//        careerVO.setDepartment(department);
//        careerVO.setPosition(position);
//        careerVO.setJoinDate(joinDateLocal);
//        careerVO.setQuitDate(quitDateLocal);
//        careerVO.setJobDescription(jobDescription);
//
//        if (joinDateLocal != null) {
//            careerVO.setJoinDate(joinDateLocal);
//            careerVO.setFormattedJoinDate(joinDateLocal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        }
//
//        if (quitDateLocal != null) {
//            careerVO.setQuitDate(quitDateLocal);
//            careerVO.setFormattedQuitDate(quitDateLocal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        }
//
//        int result = userService.insertCareer(careerVO);
//
//        model.addAttribute("career", careerVO);
//
//        if (result > 0) {
//            log.info("경력 추가 성공");
//        } else {
//            log.error("경력 추가 실패");
//        }
//        return "redirect:/user/resume?uniqueTime=" + career.getResumeId();
//    }

    // 경력 수정 처리
    @PostMapping("/editCareer")
    public String editCareer(CareerVO career,
                             @RequestParam("careerId") Long careerId,
                             @RequestParam("userNum") String userNum,
                             @RequestParam("companyName") String companyName,
                             @RequestParam("department") String department,
                             @RequestParam("position") String position,
                             @RequestParam("joinDate") String joinDate,
                             @RequestParam("quitDate") String quitDate,
                             @RequestParam("jobDescription") String jobDescription) {


        LocalDate joinDateLocal = convertStringToLocalDate(joinDate);
        LocalDate quitDateLocal = convertStringToLocalDate(quitDate);

        CareerVO careerVO = new CareerVO();
        careerVO.setCareerId(careerId);
        careerVO.setUserNum(Integer.parseInt(userNum));
        careerVO.setCompanyName(companyName);
        careerVO.setDepartment(department);
        careerVO.setPosition(position);
        careerVO.setJoinDate(joinDateLocal);
        careerVO.setQuitDate(quitDateLocal);
        careerVO.setJobDescription(jobDescription);

        int result = userService.updateCareer(careerVO);
        if (result > 0) {
            log.info("경력 수정 성공");
        } else {
            log.error("경력 수정 실패");
        }
        return "redirect:/user/resume?uniqueTime=" + career.getResumeId();// 수정 후 프로필 페이지로 리디렉션
    }

    @PostMapping("/deleteEdu/{educationId}")
    public String deleteEducation(EduVO edu,@PathVariable("educationId") Long educationId, @ModelAttribute UserVO userVO) {

        int result = userService.deleteEducation(educationId);


        if (result > 0) {
            log.info("학력 삭제 성공: " + educationId);
        } else {
            log.error("학력 삭제 실패: " + educationId);
        }

        // 학력 리스트 페이지로 리디렉션
        return "redirect:/user/resume?uniqueTime=" + edu.getResumeId();
    }


    @PostMapping("/deleteCareer/{careerId}")
    public String deleteCareer(CareerVO career,@PathVariable("careerId") Long careerId, @ModelAttribute UserVO userVO) {

        int result = userService.deleteCareer(careerId);


        if (result > 0) {
            log.info("학력 삭제 성공: " + careerId);
        } else {
            log.error("학력 삭제 실패: " + careerId);
        }

        // 학력 리스트 페이지로 리디렉션
        return "redirect:/user/resume?uniqueTime=" + career.getResumeId();
    }

    @GetMapping("/modify")
    public String modify(HttpSession session, Model model) {
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if(loginUser == null) {

            return "redirect:/user/login";
        }

        String[] jobTypes = Stream.of(
                        loginUser.getWantJobType1(),
                        loginUser.getWantJobType2(),
                        loginUser.getWantJobType3()
                )
                .filter(Objects::nonNull)
                .toArray(String[]::new);

        loginUser.setWantJobType(jobTypes);

        System.out.println("wantJobType = " + Arrays.toString(loginUser.getWantJobType()));

        model.addAttribute("user", loginUser);
        model.addAttribute("userWantJobTypeList", Arrays.asList(loginUser.getWantJobType()));

        return "user/modify";
    }


    @PostMapping("/modify")
    public String modify(@ModelAttribute UserVO user,
                         @RequestParam("wantJobType") String[] wantJobTypes,
                         @RequestParam(value = "preferredJobTypes", required = false) String[] preferredJobTypes,
                         HttpSession session, Model model) {

        log.info( "회원정보수정: " + user);
        user.setJoinDate(LocalDateTime.now());

        user.setWantJobType1(wantJobTypes.length > 0 ? wantJobTypes[0] : null);
        user.setWantJobType2(wantJobTypes.length > 1 ? wantJobTypes[1] : null);
        user.setWantJobType3(wantJobTypes.length > 2 ? wantJobTypes[2] : null);

        if (preferredJobTypes != null) {
            user.setPreferredJobType1(preferredJobTypes.length > 0 ? preferredJobTypes[0] : null);
            user.setPreferredJobType2(preferredJobTypes.length > 1 ? preferredJobTypes[1] : null);
            user.setPreferredJobType3(preferredJobTypes.length > 2 ? preferredJobTypes[2] : null);
        } else {
            user.setPreferredJobType1(null);
            user.setPreferredJobType2(null);
            user.setPreferredJobType3(null);
        }

        int result = userService.updateUser(user);
        if(result == 1) {
            log.info("수정성공!!!" + user);
            session.setAttribute("loginUser", user);
            model.addAttribute("message", "회원 정보 수정 성공");
        } else {
            log.info( "실패");
        }

        return "redirect:/user/login";  // 로그인 페이지로 리다이렉트
    }


    // 자격증 추가
    @PostMapping("/updateLicens")
    public String updateLicens(LicenseVO license,
                               @RequestParam("resumeId") Long resumeId,
                               @RequestParam("userNum") String userNum,
                               @RequestParam("licenseName") String licenseName,
                               @RequestParam("licenseOrg") String licenseOrg,
                               @RequestParam("acquisition") String acquisition,
                               @RequestParam("expiration") String expiration,
                               Model model) {

        if(!isValidUserNum(userNum)) {
            log.error("유효하진 않은 userNum: " + userNum);
            return "redirect:/error";
        }

        LocalDate acqDate = convertStringToLocalDate(acquisition);
        LocalDate expDate = convertStringToLocalDate(expiration);

        LicenseVO licenseVO = new LicenseVO();
        licenseVO.setResumeId(resumeId);
        licenseVO.setUserNum(Integer.parseInt(userNum));
        licenseVO.setLicenseName(licenseName);
        licenseVO.setLicenseOrg(licenseOrg);
        licenseVO.setAcquisition(acqDate);
        licenseVO.setExpiration(expDate);

        int result = userService.insertLicense(licenseVO);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        if (acqDate != null) {
            licenseVO.setFormattedAcquisition(acqDate.format(formatter));
        } else {
            licenseVO.setFormattedAcquisition(null); // 혹은 빈 문자열 ""로 설정할 수 있음
        }

        if (expDate != null) {
            licenseVO.setFormattedExpiration(expDate.format(formatter));
        } else {
            licenseVO.setFormattedExpiration(null); // 혹은 빈 문자열 ""로 설정할 수 있음
        }


        if (acqDate != null) {
            licenseVO.setFormattedAcquisition(acqDate.format(formatter));
        } else {
            licenseVO.setFormattedAcquisition(null); // 혹은 빈 문자열 ""
        }

        if (expDate != null) {
            licenseVO.setFormattedExpiration(expDate.format(formatter));
        } else {
            licenseVO.setFormattedExpiration(null); // 혹은 빈 문자열 ""
        }

        model.addAttribute("user", licenseVO);

        if(result == 1) {
            log.info("자격증 추가 성공");
        }else{
            log.info("자격증 추가 실패!!!!!!!!!!!!!!!");
        }

        return "redirect:/user/resume?uniqueTime=" + license.getResumeId();

    }

    // 학력 수정 처리
    @PostMapping("/editLicens")
    public String editEducation(LicenseVO license,
                                @RequestParam("licenseId") Long licenseId,
                                @RequestParam("userNum") String userNum,
                                @RequestParam("licenseName") String licenseName,
                                @RequestParam("licenseOrg") String licenseOrg,
                                @RequestParam("acquisition") String acquisition,
                                @RequestParam("expiration") String expiration) {


        LocalDate acqDate = convertStringToLocalDate(acquisition);
        LocalDate expDate = convertStringToLocalDate(expiration);

        LicenseVO licenseVO = new LicenseVO();
        licenseVO.setLicenseId(licenseId);
        licenseVO.setUserNum(Integer.parseInt(userNum));
        licenseVO.setLicenseName(licenseName);
        licenseVO.setLicenseOrg(licenseOrg);
        licenseVO.setAcquisition(acqDate);
        licenseVO.setExpiration(expDate);

        int result = userService.updateLicense(licenseVO);
        if (result > 0) {
            log.info("자격증 수정 성공");
        } else {
            log.error("자격증 수정 실패");
        }
        return "redirect:/user/resume?uniqueTime=" + license.getResumeId(); // 수정 후 프로필 페이지로 리디렉션
    }


    @PostMapping("/deleteLicens/{licenseId}")
    public String deleteLicens(LicenseVO license,
                               @PathVariable("licenseId") Long licenseId, @ModelAttribute UserVO userVO) {

        int result = userService.deleteLicense(licenseId);


        if (result > 0) {
            log.info("학력 삭제 성공: " + licenseId);
        } else {
            log.error("학력 삭제 실패: " + licenseId);
        }

        // 학력 리스트 페이지로 리디렉션
        return "redirect:/user/resume?uniqueTime=" + license.getResumeId();
    }

    @GetMapping("/info")
    public String info() {
        return "User/info";
    }



    @PostMapping("/careerInsert")
    public ResponseEntity<CareerVO> careerInsert(@RequestBody CareerVO careerVO) {
        System.out.println(careerVO);


        LocalDate joinDateLocal = convertStringToLocalDate(String.valueOf(careerVO.getJoinDate()));
        LocalDate quitDateLocal = convertStringToLocalDate(String.valueOf(careerVO.getQuitDate()));

        careerVO.setJoinDate(joinDateLocal);
        careerVO.setQuitDate(quitDateLocal);

        // 데이터 처리 후 업데이트
        int result = userService.insertCareer(careerVO);
        if (result > 0) {
            log.info("학력 insert 성공");
        } else {
            log.error("학력 insert 실패");
        }

        return ResponseEntity.ok(careerVO);
    }

    @PostMapping("/careerUpdate")
    public ResponseEntity<CareerVO> careerUpdate(@RequestBody CareerVO careerVO) {
        System.out.println(careerVO);

        LocalDate joinDateLocal = convertStringToLocalDate(String.valueOf(careerVO.getJoinDate()));
        LocalDate quitDateLocal = convertStringToLocalDate(String.valueOf(careerVO.getQuitDate()));

        careerVO.setJoinDate(joinDateLocal);
        careerVO.setQuitDate(quitDateLocal);

        int result = userService.updateCareer(careerVO);
        if (result > 0) {
            log.info("경력 수정 성공");
        } else {
            log.error("경력 수정 실패");
        }
        return ResponseEntity.ok(careerVO);
    }

    @PostMapping("/eduInsert")
    public ResponseEntity<EduVO> eduInsert(@RequestBody EduVO eduVO) {
        System.out.println(eduVO);


        LocalDate graduationDate = convertStringToLocalDate(String.valueOf(eduVO.getGraduationDate()));
        eduVO.setGraduationDate(graduationDate);


        // 데이터 처리 후 업데이트
        int result = userService.insertEdu(eduVO);
        if (result > 0) {
            log.info("학력 insert 성공");
        } else {
            log.error("학력 insert 실패");
        }

        return ResponseEntity.ok(eduVO);
    }

    @PostMapping("/eduUpdate")
    public ResponseEntity<EduVO> eduUpdate(@RequestBody EduVO eduVO) {
        System.out.println(eduVO);

        LocalDate graduationDate = convertStringToLocalDate(String.valueOf(eduVO.getGraduationDate()));
        eduVO.setGraduationDate(graduationDate);

        int result = userService.updateEdu(eduVO);
        if (result > 0) {
            log.info("학력 수정 성공");
        } else {
            log.error("학력 수정 실패");
        }
        return ResponseEntity.ok(eduVO);
    }

    @PostMapping("/licenseInsert")
    public ResponseEntity<LicenseVO> licenseInsert(@RequestBody LicenseVO licenseVO) {
        System.out.println(licenseVO);


        LocalDate acquisitionDate = convertStringToLocalDate(String.valueOf(licenseVO.getAcquisition()));
        LocalDate expirationDate = convertStringToLocalDate(String.valueOf(licenseVO.getExpiration()));
        licenseVO.setAcquisition(acquisitionDate);
        licenseVO.setExpiration(expirationDate);


        // 데이터 처리 후 업데이트
        int result = userService.insertLicense(licenseVO);
        if (result > 0) {
            log.info("학력 insert 성공");
        } else {
            log.error("학력 insert 실패");
        }

        return ResponseEntity.ok(licenseVO);
    }

    @PostMapping("/licenseUpdate")
    public ResponseEntity<LicenseVO> licenseUpdate(@RequestBody LicenseVO licenseVO) {
        System.out.println(licenseVO);

        LocalDate acquisitionDate = convertStringToLocalDate(String.valueOf(licenseVO.getAcquisition()));
        LocalDate expirationDate = convertStringToLocalDate(String.valueOf(licenseVO.getExpiration()));
        licenseVO.setAcquisition(acquisitionDate);
        licenseVO.setExpiration(expirationDate);

        int result = userService.updateLicense(licenseVO);
        if (result > 0) {
            log.info("학력 수정 성공");
        } else {
            log.error("학력 수정 실패");
        }
        return ResponseEntity.ok(licenseVO);
    }

    @PostMapping("/licenseDelete")
    public ResponseEntity<Map<String, String>> deleteLicense(@RequestBody LicenseVO licenseVO) {
        Long licenseId = licenseVO.getLicenseId();
        int result = userService.deleteLicense(licenseId);

        if (result > 0) {
            log.info("자격증 삭제 성공");
            return ResponseEntity.ok(Map.of("message", "삭제 성공"));
        } else {
            log.error("자격증 삭제 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "삭제 실패"));
        }
    }


}

