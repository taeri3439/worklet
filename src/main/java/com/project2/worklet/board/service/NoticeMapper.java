package com.project2.worklet.board.service;
import com.project2.worklet.component.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    //등록
    int noticeForm(NoticeVO vo);
    //리스트
    List<NoticeVO> getList();
    //검색
    List<NoticeVO> searchTitle(String keyword);

    List<NoticeVO> getPageList(int offset, int amount);
    List<NoticeVO> searchByTitlePaging(String keyword, int offset, int amount);
    int getTotal();
    int searchTotal(String keyword);
    // 최신 공지사항 6개 가져오기
    List<NoticeVO> getRecentNotices();


















}
