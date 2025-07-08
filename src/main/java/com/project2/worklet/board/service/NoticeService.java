package com.project2.worklet.board.service;


import com.project2.worklet.component.NoticeVO;

import java.util.List;

public interface NoticeService {
    int noticeForm(NoticeVO vo);
    List<NoticeVO> getList();
    List<NoticeVO> searchTitle(String keyword);

    List<NoticeVO> getPageList(int offset, int amount);
    List<NoticeVO> searchByTitlePaging(String keyword, int offset, int amount);
    int getTotal();
    int searchTotal(String keyword);
    List<NoticeVO> getRecentNotices(); // ← 이거 추가!













}
