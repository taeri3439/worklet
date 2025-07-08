package com.project2.worklet.calendarService;

import com.project2.worklet.component.CalendarVO;
import com.project2.worklet.handlerInterceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("calendar")
@Slf4j
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CalendarMapper calendarMapper;
    @Autowired
    private LoginInterceptor loginInterceptor;



    @Override
    public List<CalendarVO> getAllEvent(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<CalendarVO> events = calendarMapper.getAllEvent();
        log.info("Fetched {} events", events.size());

        if(userId == null) {
            log.warn("UserId is null, setting all favorites to false");
            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        log.info("UserId from session: {}", userId);

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행
        log.info("Favorite empSeqNos for user {}: {}", userId, favoriteEmpseqNos);

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }


    //시작일
    @Override
    public List<CalendarVO> getStartDayEvents(String userId) {
        //사용자의 즐겨찾기 목록을 가져오기

        List<CalendarVO> events = calendarMapper.getStartDayEvents(userId);
        log.info("Fetched {} events", events.size());

        if(userId == null) {
            log.warn("UserId is null, setting all favorites to false");
            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        log.info("UserId from session: {}", userId);

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행
        log.info("Favorite empSeqNos for user {}: {}", userId, favoriteEmpseqNos);

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);
        }

//        for(CalendarVO event : events) {
//            String startDate = event.getEmpWantedt(); //endt (String타입이라 이대로는 +1일 할 수가 없다)
//
//            //date로 변환 후 +1일
//            LocalDate parsedEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")); //date화
//            LocalDate plusOneDay = parsedEndDate.plusDays(1); // +1일
//            String newEndDate = plusOneDay.format(DateTimeFormatter.ofPattern("yyyyMMdd")); //다시 string화
//
//            event.setEmpWantedStdt(event.getEmpWantedEndt()); //empwantedstdt에 empwantedendt 값을 넣어서 종료일을 시작일처럼 사용해 일정에 표시
//            event.setEmpWantedEndt(newEndDate); //종료일에 +1일된 애를 집어넣어 정확한 종료일까지
//            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
//            event.setFavorite(isFavorite);
//        }
//
//
//
        return events;
    }

    //
//    //종료일




    // 종료일
    @Override
    public List<CalendarVO> getEndDayEvents(String userId) {
        //사용자의 즐겨찾기 목록을 가져오기

        List<CalendarVO> events = calendarMapper.getEndDayEvents(userId);
        log.info("Fetched {} events", events.size());

        if(userId == null) {
            log.warn("UserId is null, setting all favorites to false");
            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        log.info("UserId from session: {}", userId);

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행
        log.info("Favorite empSeqNos for user {}: {}", userId, favoriteEmpseqNos);

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            String endDate = event.getEmpWantedEndt(); //endt (String타입이라 이대로는 +1일 할 수가 없다)

            //date로 변환 후 +1일
            LocalDate parsedEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyyMMdd")); //date화
            LocalDate plusOneDay = parsedEndDate.plusDays(1); // +1일
            String newEndDate = plusOneDay.format(DateTimeFormatter.ofPattern("yyyyMMdd")); //다시 string화

            event.setEmpWantedStdt(event.getEmpWantedEndt()); //empwantedstdt에 empwantedendt 값을 넣어서 종료일을 시작일처럼 사용해 일정에 표시
            event.setEmpWantedEndt(newEndDate); //종료일에 +1일된 애를 집어넣어 정확한 종료일까지
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);
        }


        return events;
    }

    //공채시작일
    @Override
    public List<CalendarVO> getStartDayEventsOnlyY(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<CalendarVO> events = calendarMapper.getStartDayEventsOnlyY(userId);

        if(userId == null) {

            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }

    //공채종료일
    @Override
    public List<CalendarVO> getEndDayEventsOnlyY(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<CalendarVO> events = calendarMapper.getEndDayEventsOnlyY(userId);

        if(userId == null) {

            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }

    //채용시작일
    @Override
    public List<CalendarVO> getStartDayEventsOnlyN(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<CalendarVO> events = calendarMapper.getStartDayEventsOnlyN(userId);

        if(userId == null) {

            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }

    //채용종료일
    @Override
    public List<CalendarVO> getEndDayEventsOnlyN(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<CalendarVO> events = calendarMapper.getEndDayEventsOnlyN(userId);

        if(userId == null) {

            for(CalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        List<Integer> favoriteEmpseqNos = calendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(CalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }




    //찜기능
    @Override
    public void addFavorite(int empSeqNo, String userId, String color) {
        log.info("Adding favorite: EmpSeqNo = {}, UserId = {}, Color ={}", empSeqNo, userId, color);
        calendarMapper.insertFavorite(empSeqNo, userId, color);
    }

    @Override
    public void removeFavorite(int empSeqNo, String userId) {
        log.info("Removing favorite: EmpSeqNo = {}, UserId = {}", empSeqNo, userId);
        calendarMapper.deleteFavorite(empSeqNo, userId);

    }



    //즐겨찾기 목록가져오기
    @Override
    public List<Integer> getFavoriteEmpSeqNos(String userId) {
        return calendarMapper.findFavoriteEmpSeqNosUserId(userId);
    };



}
