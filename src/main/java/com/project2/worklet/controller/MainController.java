package com.project2.worklet.controller;
import com.project2.worklet.board.service.NoticeService;
import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.component.NoticeVO;
import com.project2.worklet.jobPostingService.JobPostingService;
import com.project2.worklet.util_interceptor.Criteria;
import com.project2.worklet.util_interceptor.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/Board")

public class MainController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private JobPostingService jobPostingService;

    @Autowired
    private HttpSession session;

    public String getUserIdFromSession() {
        Object userId = session.getAttribute("userId");
        return userId != null ? userId.toString() : null;
    };



//    @GetMapping("/mainPage")
//    public String showMainPage(Model model) {
//        // 최신 공지사항 6개 가져오기
//        List<NoticeVO> noticeList = noticeService.getRecentNotices();
//        // 최신 채용 공고 6개 가져오기
//        List<JobPostingVO2> jobPostingList = jobPostingService.getRecentJobPostings(); // 채용 공고
//
//        // 뷰로 전달
//        model.addAttribute("noticeList", noticeList); // 공지사항 목록
//        model.addAttribute("jobPostingList", jobPostingList); // 채용 공고 목록
//
//        return "Board/mainPage"; // mainPage 뷰로 이동
//    }

    @GetMapping("/mainPage")
    public String showMainPage(Model model, Criteria cri123) {
        cri123 = new Criteria(1, 6);

        // 최신 공지사항 6개 가져오기
        List<NoticeVO> noticeList = noticeService.getRecentNotices();
        // 최신 채용 공고 6개 가져오기
        List<JobPostingVO2> jobPostingList = jobPostingService.getRecentJobPostings(); // 채용 공고

        // 뷰로 전달
        model.addAttribute("noticeList", noticeList); // 공지사항 목록
        model.addAttribute("jobPostingList", jobPostingList); // 채용 공고 목록

        String userId = getUserIdFromSession();
//        String userId = "test1234";
        List<JobPostingVO2> list = jobPostingService.getList(cri123, userId);
        System.out.println(list.toString());

        int total = jobPostingService.getTotal(cri123);

        PageVO pagevo = new PageVO(cri123, total);
        model.addAttribute("list", list);
        model.addAttribute("pageVO", pagevo);
        model.addAttribute("userId", userId);

        return "Board/mainPage"; // mainPage 뷰로 이동

    }























}
