package com.project2.worklet.controller;

import com.project2.worklet.component.QnaVO;
import com.project2.worklet.component.UserVO;
import com.project2.worklet.qna.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @GetMapping("/qna_write")
    public String qna_write(HttpSession session) {
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";  // 로그인 안 됐으면 로그인 페이지로
        }
        return "Board/qna_write";  // 로그인 되어 있으면 문의작성페이지로
    }

    @GetMapping("/qna_list")
    public String qnalist(Model model, HttpSession session) {
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        List<QnaVO> qnaList;

        if (loginUser != null) {
            // 로그인한 유저 → 본인 글만
            qnaList = qnaService.getQnaListByUserNum(loginUser.getUserNum());
        } else {
            // 비로그인 유저 → 전체 목록(제목만 열람)
            qnaList = qnaService.qnalist();
        }

        model.addAttribute("qnaList", qnaList);
        model.addAttribute("loginUser", loginUser); // JS나 화면에서 로그인 여부 판단용
        return "Board/qna_list";
    }




    @PostMapping("/qnaForm")
    public String qnaForm(QnaVO vo, HttpSession session) {
        // 로그인한 사용자 가져오기
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/user/login";
        }

        // 문의글 작성자 정보 추가
        vo.setUserNum(loginUser.getUserNum());
        vo.setInquiryCreateAt(LocalDateTime.now());
        vo.setInquiryUpdateAt(LocalDateTime.now());
        vo.setInquiryStatus("답변대기");

        qnaService.qnaForm(vo);
        return "redirect:/qna/qna_list";
    }


    @GetMapping("/qna_reply")
    public String qnaReply(Model model) {
        List<QnaVO> qnaList = qnaService.qnalist();
        model.addAttribute("qnaList", qnaList);
        return "Board/qna_reply";
    }

    @PostMapping("/qna_reply")
    public String submitReply(@RequestParam("inquiryId") Integer inquiryId, @RequestParam("inquiryReply") String inquiryReply) {
        QnaVO qnaVO = qnaService.getQnaById(inquiryId);  // 문의 ID로 조회
        qnaVO.setInquiryReply(inquiryReply);               // 답변 내용 설정
        qnaVO.setInquiryUpdateAt(LocalDateTime.now());    // 수정일시 설정
        qnaVO.setInquiryStatus("답변완료");                // 상태 변경
        qnaService.qnaReply(qnaVO);                        // DB에 답변 저장
        return "redirect:/qna/qna_list";                  // 답변 후 페이지 리다이렉트
    }





}
