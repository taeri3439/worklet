package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarVO> getAllEvent();

    //달력버튼
    List<CalendarVO> getStartDayEvents(String userId);
    List<CalendarVO> getEndDayEvents(String userId);
    List<CalendarVO> getStartDayEventsOnlyY(String userId);
    List<CalendarVO> getEndDayEventsOnlyY(String userId);
    List<CalendarVO> getStartDayEventsOnlyN(String userId);
    List<CalendarVO> getEndDayEventsOnlyN(String userId);

    //찜 추가 삭제
    public void insertFavorite(@Param("empSeqNo") int empSeqNo,
                        @Param("userId") String userId,
                        @Param("color") String color);

    public void deleteFavorite(@Param("empSeqNo") int empSeqNo,
                        @Param("userId") String userId);

    List<Integer> findFavoriteEmpSeqNosUserId(@Param("userId") String userId);

}



