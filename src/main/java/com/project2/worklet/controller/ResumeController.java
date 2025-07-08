package com.project2.worklet.controller;

import com.project2.worklet.component.*;
import com.project2.worklet.user.service.UserService;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.project2.worklet.resume.service.ResumeService;


import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RestController
public class ResumeController {

   @Autowired
   @Qualifier("resumeService")
   private ResumeService resumeService;

    @Autowired
    @Qualifier("userService")
    private UserService userService;


    @PostMapping("/user/submitresume")
    @ResponseBody
    public Map<String, String> airesume(@RequestBody Map<String, String> payload) {

        String content = payload.get("content");

        RestTemplate restTemplate = new RestTemplate();
        String pythonApiUrl = "http://localhost:5000/format-intro";

        Map<String, String> request = new HashMap<>();
        request.put("content", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, entity, Map.class);
        String formatted = (String) response.getBody().get("result");

        Map<String, String> result = new HashMap<>();
        result.put("result", formatted);
        return result;
    }


    @PostMapping("/generatepdf")
    @ResponseBody
    public ResponseEntity<byte[]> generateStatus(@RequestBody ResumeVO resumeVO,
                                                 HttpSession session) throws Exception, JRException {

        System.out.println("컨트롤러 탔음");

        // JasperReport 템플릿 파일 로드
        JasperReport report = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/resume_ex_01.jrxml")
        );

        UserVO vo = (UserVO) session.getAttribute("loginUser");
        System.out.println("resumevo는 "+resumeVO.toString());
        System.out.println("uservo는 "+vo.toString());
        System.out.println("eduvo는 "+resumeVO.getEducationList().toString());
        System.out.println("careervo는 "+resumeVO.getCareerList().toString());
        System.out.println("licensevo는 "+resumeVO.getLicenseList().toString());


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("resume_title", resumeVO.getTitle());
        parameters.put("userName", vo.getUserName());
        parameters.put("userBirthday", vo.getUserBirthday());
        parameters.put("userAddress", vo.getUserAddress());
        parameters.put("userEmail", vo.getUserEmail());
        parameters.put("userPhone", vo.getUserPhone());
        parameters.put("army", "군필");
        for(int i = 0; i < resumeVO.getEducationList().size(); i++){
            parameters.put("resumeEduPeriod"+(i+1), String.valueOf(resumeVO.getEducationList().get(i).getGraduationDate()));
            parameters.put("resumeEduCont"+(i+1), resumeVO.getEducationList().get(i).getSchoolName()+" "
                                                    +resumeVO.getEducationList().get(i).getMajor()+ " "
                                                    +resumeVO.getEducationList().get(i).getGraduationStatus());
        }
        for(int i = 0; i < resumeVO.getCareerList().size(); i++){
            parameters.put("resumeCarPeriod"+(i+1), resumeVO.getCareerList().get(i).getJoinDate()+"~"
                                                        +resumeVO.getCareerList().get(i).getQuitDate());
            parameters.put("resumeCarBusiName"+(i+1), resumeVO.getCareerList().get(i).getCompanyName());
            parameters.put("resumeCarWorkCont"+(i+1), resumeVO.getCareerList().get(i).getJobDescription());

        }

        parameters.put("resumeCertRegDate1", "2020.01");
        parameters.put("resumeCertRegDate2", "2024.04");
        parameters.put("resumeCertRegDate3", "2024.11");
        parameters.put("resumeCertName1", "정보처리기사");
        parameters.put("resumeCertName2", "토익 950");
        parameters.put("resumeCertName3", "리눅스마스터 2급");
        parameters.put("resumeCertFrom1", "한국산업인력공단");
        parameters.put("resumeCertFrom2", "YBM토익");
        parameters.put("resumeCertFrom3", "KAIT");
        parameters.put("resumeEarlyLife", resumeVO.getResumeEarlyLife());
        parameters.put("resumeStrengthWeakness", resumeVO.getResumeStrengthWeakness());
        parameters.put("resumeSchoolCareer", resumeVO.getResumeSchoolCareer());
        parameters.put("resumeApplyAfterDream", resumeVO.getResumeApplyAfterDream());

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        byte[] pdfBytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("resume.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

    }

    @PostMapping("/resume/create")
    public String createResume(
            @RequestParam String title,
            @RequestParam String growth,
            @RequestParam String student_day,
            @RequestParam String pros_and_cons,
            @RequestParam String aspiration,
            @RequestParam List<Long> education_ids,  // 다중 선택된 값들을 받는 경우
            @RequestParam List<Long> career_ids,
            @RequestParam List<Long> license_ids) {

        // ResumeVO 객체 생성 후 저장
        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setTitle(title);
        resumeVO.setGrowth(growth);
        resumeVO.setStudentDay(student_day);
        resumeVO.setProsAndCons(pros_and_cons);
        resumeVO.setAspiration(aspiration);
        resumeVO.setEducationIds(education_ids);
        resumeVO.setCareerIds(career_ids);
        resumeVO.setLicenseIds(license_ids);

        resumeService.insertResume(resumeVO);  // 이력서 저장 서비스 호출

        return "이력서가 성공적으로 저장되었습니다.";
    }

    @GetMapping("/resume/create")
    public String createResume(){
        return "User/resume";
    }


    @GetMapping("/user/resumeProc")
    public String resumeProc(HttpSession session) {
        long uniqueTime = System.currentTimeMillis();

        // 로그인된 사용자 정보에서 user_num 꺼내기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        int userNum = loginUser.getUserNum();

        // VO에 값 넣기
        ResumeVO resume = new ResumeVO();
        resume.setResumeId(uniqueTime);
        resume.setUserNum(userNum);

        System.out.println(">>> [resumeProc] session ID: " + session.getId());
        System.out.println(">>> [resumeProc] loginUser: " + session.getAttribute("loginUser"));
        System.out.println("resume: " + resume);
        // 서비스 호출
        resumeService.insertResume(resume);

        return "redirect:/user/resume?uniqueTime="+uniqueTime;
    }

    @PostMapping("/user/saveResume")
    public String saveResume(
            @RequestParam("resumeId") long   resumeId,
            @RequestParam("title2") String title,
            @RequestParam("growth2") String growth,
            @RequestParam("studentDay2") String studentDay,
            @RequestParam("prosAndCons2") String prosAndCons,
            @RequestParam("aspiration2") String aspiration
    ) {
        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setResumeId(resumeId);
        resumeVO.setTitle(title);
        resumeVO.setGrowth(growth);
        resumeVO.setStudentDay(studentDay);
        resumeVO.setProsAndCons(prosAndCons);
        resumeVO.setAspiration(aspiration);

        int result= resumeService.saveResume(resumeVO);


        // ✅ 올바른 VO 전달
        if(result==1){
            // 업데이트된 이력서를 가져옴
            ResumeVO updatedResume = resumeService.getResumeById(resumeVO.getResumeId());

            // updatedAt 값 확인을 위한 출력
            System.out.println("updatedAt 값 확인"+updatedResume.getUpdatedAt());
        }else{
            System.out.println("안됨");
        }

        return "redirect:/user/resumePage?uniqueTime=" + resumeVO.getResumeId();
    }

//    @GetMapping("/user/resumePage")
//    public String saveResume(HttpSession session, Model model) {
//        // 로그인한 사용자 정보 가져오기
//        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
//
//
//        // 로그인 정보가 없으면 userNum이 0일 가능성 있음
//        if (loginUser == null || loginUser.getUserNum() == 0) {
//            System.out.println("로그인 정보가 없습니다.");
//            return "redirect:/user/login"; // 로그인 페이지로 리다이렉트할 수도 있음
//        } else {
//            int userNum = loginUser.getUserNum();
//            System.out.println("로그인한 사용자: " + userNum);
//
//            // 서비스에서 이력서 목록 가져오기
//            List<ResumeVO> resumeList = resumeService.getResumesByUserNum(userNum);
//            System.out.println("이력서 목록: " + resumeList);
//
//            model.addAttribute("resumeList", resumeList);
//
//            // userVO 가져와서 모델에 추가 (타임리프 오류 방지)
//            UserVO fullUser = userService.getUserById(loginUser.getUserId());
//
//            // 희망직업 배열로 구성해서 넣기 (UserController 참고)
//            List<String> wantJobTypes = new ArrayList<>();
//            if (fullUser.getWantJobType1() != null) wantJobTypes.add(fullUser.getWantJobType1());
//            if (fullUser.getWantJobType2() != null) wantJobTypes.add(fullUser.getWantJobType2());
//            if (fullUser.getWantJobType3() != null) wantJobTypes.add(fullUser.getWantJobType3());
//            fullUser.setWantJobType(wantJobTypes.toArray(new String[0]));
//
//            model.addAttribute("userVO", fullUser);
//
//            return "User/mypage"; // 타임리프 페이지
//        }
//    }


    // 이력서 수정
    @PostMapping("/user/editResume")
    public String editResume(@RequestParam("resumeId") long resumeId,
                             @RequestParam("title") String title,
                             @RequestParam("growth") String growth,
                             @RequestParam("studentDay") String studentDay,
                             @RequestParam("prosAndCons") String prosAndCons,
                             @RequestParam("aspiration") String aspiration) {
        // 수정된 내용을 ResumeVO에 담기
        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setResumeId(resumeId);
        resumeVO.setTitle(title);
        resumeVO.setGrowth(growth);
        resumeVO.setStudentDay(studentDay);
        resumeVO.setProsAndCons(prosAndCons);
        resumeVO.setAspiration(aspiration);

        // 이력서 수정 서비스 호출
        int result = resumeService.updateResume(resumeVO);

        // 수정 결과에 따라 리다이렉트
        if (result == 1) {
            return "redirect:/user/resumePage"; // 수정된 이력서 목록 페이지로 리다이렉트
        } else {
            return "redirect:/user/resume?resumeId=" + resumeId; // 수정 실패 시 다시 수정 페이지로 리다이렉트
        }
    }


    @GetMapping("/user/editResume")
    public String editResume(@RequestParam("resumeId") long resumeId, Model model) {
        // resumeId로 해당 이력서 정보 가져오기
        ResumeVO resumeVO = resumeService.getResumeById(resumeId);
        model.addAttribute("resume", resumeVO);

        return "User/resume"; // 수정 페이지로 이동
    }

    @PostMapping("/user/deleteResume")
    public String deleteResume(@RequestParam("resumeId") long resumeId) {

        ResumeVO resumeVO = new ResumeVO();
        resumeVO.setResumeId(resumeId);
       return "redirect:/user/resumePage?resumeId=" + resumeId;
    }


}



