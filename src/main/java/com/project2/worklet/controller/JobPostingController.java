package com.project2.worklet.controller;

import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.jobPostingService.JobPostingService;
import com.project2.worklet.util_interceptor.Criteria;
import com.project2.worklet.util_interceptor.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
public class JobPostingController {

    @Autowired
    @Qualifier("JobPosting")
    private JobPostingService jobPostingService;

    @Autowired
    private HttpSession session; //세션 가져오기

    public String getUserIdFromSession() {
        Object userId = session.getAttribute("userId");
        return userId != null ? userId.toString() : null;
    };


    @GetMapping("/jobposting")
    public String jobPosting(Model model, Criteria cri) {

        String userId = getUserIdFromSession();
//        String userId = "test1234";
        List<JobPostingVO2> list = jobPostingService.getList(cri, userId);
        System.out.println(list.toString());

        int total = jobPostingService.getTotal(cri);

        PageVO pagevo = new PageVO(cri, total);
        model.addAttribute("list", list);
        model.addAttribute("pageVO", pagevo);
        model.addAttribute("userId", userId);

        return "jobposting";
    }

    @PostMapping("scrap")
    public ResponseEntity<String> scrapJob(
            @RequestParam String empNoToScrap,
            @RequestParam String userId) {

        // 전달받은 값 출력 (디버깅용)
        System.out.println("empNoToScrap: " + empNoToScrap);
        System.out.println("userId: " + userId);

        int result = jobPostingService.scrapJob(userId,empNoToScrap);
        System.out.println("result는 : "+result);

        // 성공적으로 처리되었음을 알리는 응답 반환
        return ResponseEntity.ok("찜 목록 저장 성공");
    }

    @PostMapping("unscrap")
    public ResponseEntity<String> unscrapJob(@RequestParam String userId,
                                             @RequestParam String empNoToScrap) {
        int result = jobPostingService.unscrapJob(userId,empNoToScrap);
        System.out.println("찜삭제 result는 : "+result);
        return ResponseEntity.ok("찜 삭제 성공");
    }

    @GetMapping("/airesume")
    public String airesume() {
        return "airesume";
    }



}


//    @PostMapping("/submitresume")
//    public String airesume(@RequestParam String content,
//                           Model model) {
//
//        RestTemplate restTemplate = new RestTemplate();
//        String pythonApiUrl = "http://localhost:5000/format-intro";
//
//        Map<String, String> request = new HashMap<>();
//        request.put("content", content);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, entity, Map.class);
//        String formatted = (String) response.getBody().get("result");
//
//        model.addAttribute("result", formatted);
//
//        return "airesumeresult";
//    }