package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CalendarService {



    List<CalendarVO> getAllEvent(String userId);


    //달력버튼
    List<CalendarVO> getStartDayEvents(String userId);
    List<CalendarVO> getEndDayEvents(String userId);
    List<CalendarVO> getStartDayEventsOnlyY(String userId);
    List<CalendarVO> getEndDayEventsOnlyY(String userId);
    List<CalendarVO> getStartDayEventsOnlyN(String userId);
    List<CalendarVO> getEndDayEventsOnlyN(String userId);

    //즐겨찾기 추가,삭제
    void addFavorite(int empSeqNo, String userId, String color) ;
    void removeFavorite(int empSeqNo, String userId);

    List<Integer> getFavoriteEmpSeqNos(String userId);


}
