package com.project2.worklet.myCalendarService;

import com.project2.worklet.component.CalendarVO;
import com.project2.worklet.component.MyCalendarVO;

import java.util.List;

public interface MyCalendarService {

    List<MyCalendarVO> getAllEvent(String userId);


    //달력버튼
    List<MyCalendarVO> getStartDayEvents(String userId);
    List<MyCalendarVO> getEndDayEvents(String userId);
    List<MyCalendarVO> getStartDayEventsOnlyY(String userId);
    List<MyCalendarVO> getEndDayEventsOnlyY(String userId);
    List<MyCalendarVO> getStartDayEventsOnlyN(String userId);
    List<MyCalendarVO> getEndDayEventsOnlyN(String userId);

    //즐겨찾기 추가,삭제
    void addFavorite(int empSeqNo, String userId) ;
    void removeFavorite(int empSeqNo, String userId);

    List<Integer> getFavoriteEmpSeqNos(String userId);

    //찜 목록 불러오기
    List<MyCalendarVO> getFavoriteEvents(String userId);

}
