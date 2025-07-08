package com.project2.worklet.controller;
import com.project2.worklet.board.service.NoticeService;

import com.project2.worklet.component.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notice_write")
    public String notice_write() {
        return "Board/notice_write";
    }

    //공지 등록
    @PostMapping("/noticeForm")
    public String noticeForm(NoticeVO vo) {
        noticeService.noticeForm(vo);
        return "redirect:/notice/notice_list";
    }
    //공지 리스트, 검색, 페이징
    @GetMapping("/notice_list")
    public String noticeList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "amount", defaultValue = "10") int amount,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model
    ) {
        int offset = (page - 1) * amount;
        List<NoticeVO> noticeList;
        int total;

        if (keyword != null && !keyword.isEmpty()) {
            noticeList = noticeService.searchByTitlePaging(keyword, offset, amount);
            total = noticeService.searchTotal(keyword);
        } else {
            noticeList = noticeService.getPageList(offset, amount);
            total = noticeService.getTotal();
        }

        model.addAttribute("noticeList", noticeList != null ? noticeList : new ArrayList<>());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("total", total);
        model.addAttribute("amount", amount);

        return "Board/notice_list";
    }



}
