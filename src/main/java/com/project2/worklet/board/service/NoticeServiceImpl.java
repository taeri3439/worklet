package com.project2.worklet.board.service;


import com.project2.worklet.component.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public int noticeForm(NoticeVO vo) {
        return noticeMapper.noticeForm(vo);
    }

    @Override
    public List<NoticeVO> getList() {
        return noticeMapper.getList();
    }

    @Override
    public List<NoticeVO> searchTitle(String keyword) {
        return noticeMapper.searchTitle(keyword);
    }

    @Override
    public List<NoticeVO> getPageList(int offset, int amount) {
        return noticeMapper.getPageList(offset, amount);
    }

    @Override
    public List<NoticeVO> searchByTitlePaging(String keyword, int offset, int amount) {
        return noticeMapper.searchByTitlePaging(keyword, offset, amount);
    }

    @Override
    public int getTotal() {
        return noticeMapper.getTotal();
    }

    @Override
    public int searchTotal(String keyword) {
        return noticeMapper.searchTotal(keyword);
    }

    @Override
    public List<NoticeVO> getRecentNotices() {
        return noticeMapper.getRecentNotices();
    }




}
