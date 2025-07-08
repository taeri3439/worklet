package com.project2.worklet.myCalendarService;

import com.project2.worklet.calendarService.CalendarMapper;
import com.project2.worklet.component.CalendarVO;
import com.project2.worklet.component.MyCalendarVO;
import com.project2.worklet.handlerInterceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Collections;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("mycalendar")
@Slf4j
public class MyCalendarServiceImpl implements MyCalendarService {

    @Autowired
    private MyCalendarMapper myCalendarMapper;
    @Autowired
    private LoginInterceptor loginInterceptor;



    @Override
    public List<MyCalendarVO> getAllEvent(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<MyCalendarVO> events = myCalendarMapper.getAllEvent();
        log.info("Fetched {} events", events.size());

        if(userId == null) {
            log.warn("UserId is null, setting all favorites to false");
            for(MyCalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        log.info("UserId from session: {}", userId);

        List<Integer> favoriteEmpseqNos = myCalendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행
        log.info("Favorite empSeqNos for user {}: {}", userId, favoriteEmpseqNos);

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(MyCalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }


    //시작일
    @Override
    public List<MyCalendarVO> getStartDayEvents(String userId) {
        //사용자의 즐겨찾기 목록을 가져오기

        List<MyCalendarVO> events = myCalendarMapper.getStartDayEvents(userId);
        log.info("Fetched {} events", events.size());

        if(userId == null) {
            log.warn("UserId is null, setting all favorites to false");
            for(MyCalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        log.info("UserId from session: {}", userId);

        List<Integer> favoriteEmpseqNos = myCalendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행
        log.info("Favorite empSeqNos for user {}: {}", userId, favoriteEmpseqNos);

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(MyCalendarVO event : events) {
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
    public List<MyCalendarVO> getEndDayEvents(String userId) {
        //사용자의 즐겨찾기 목록을 가져오기

        List<MyCalendarVO> events = myCalendarMapper.getEndDayEvents(userId);
        log.info("Fetched {} events", events.size());

        if(userId == null) {
            log.warn("UserId is null, setting all favorites to false");
            for(MyCalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        log.info("UserId from session: {}", userId);

        List<Integer> favoriteEmpseqNos = myCalendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행
        log.info("Favorite empSeqNos for user {}: {}", userId, favoriteEmpseqNos);

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(MyCalendarVO event : events) {
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
    public List<MyCalendarVO> getStartDayEventsOnlyY(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<MyCalendarVO> events = myCalendarMapper.getStartDayEventsOnlyY(userId);

        if(userId == null) {

            for(MyCalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        List<Integer> favoriteEmpseqNos = myCalendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(MyCalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }

    //공채종료일
    @Override
    public List<MyCalendarVO> getEndDayEventsOnlyY(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<MyCalendarVO> events = myCalendarMapper.getEndDayEventsOnlyY(userId);

        if(userId == null) {

            for(MyCalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        List<Integer> favoriteEmpseqNos = myCalendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(MyCalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }

    //채용시작일
    @Override
    public List<MyCalendarVO> getStartDayEventsOnlyN(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<MyCalendarVO> events = myCalendarMapper.getStartDayEventsOnlyN(userId);

        if(userId == null) {

            for(MyCalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        List<Integer> favoriteEmpseqNos = myCalendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(MyCalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }

    //채용종료일
    @Override
    public List<MyCalendarVO> getEndDayEventsOnlyN(String userId) {

        //사용자의 즐겨찾기 목록을 가져오기

        List<MyCalendarVO> events = myCalendarMapper.getEndDayEventsOnlyN(userId);

        if(userId == null) {

            for(MyCalendarVO event : events) {
                event.setFavorite(false);
            }
            return events;
        }

        List<Integer> favoriteEmpseqNos = myCalendarMapper.findFavoriteEmpSeqNosUserId(userId); //db에서 쿼리 실행

        //디버깅
        Set<String> favoriteEmpSeqNoSet = favoriteEmpseqNos.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        for(MyCalendarVO event : events) {
            boolean isFavorite = favoriteEmpSeqNoSet.contains(event.getEmpSeqNo());
            event.setFavorite(isFavorite);

        }


        return events;
    }




    //찜기능
    @Override
    public void addFavorite(int empSeqNo, String userId) {
        log.info("Adding favorite: EmpSeqNo = {}, UserId = {}", empSeqNo, userId);
        myCalendarMapper.insertFavorite(empSeqNo, userId);
    }

    @Override
    public void removeFavorite(int empSeqNo, String userId) {
        log.info("Removing favorite: EmpSeqNo = {}, UserId = {}", empSeqNo, userId);
        myCalendarMapper.deleteFavorite(empSeqNo, userId);

    }



    //즐겨찾기 목록가져오기
    @Override
    public List<Integer> getFavoriteEmpSeqNos(String userId) {
        return myCalendarMapper.findFavoriteEmpSeqNosUserId(userId);
    };

    @Override
    public List<MyCalendarVO> getFavoriteEvents(String userId) {


        //색깔없이 찜한 애들만 가져올때
//        List<Integer> empSeqNos = myCalendarMapper.findFavoriteEmpSeqNosUserId(userId);
//        if (empSeqNos == null || empSeqNos.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<MyCalendarVO> events = myCalendarMapper.findEventsByEmpSeqNos(empSeqNos);
//
//        // 찜 표시를 직접 붙이기 (favorite = true)
//        for (MyCalendarVO vo : events) {
//            vo.setFavorite(true);
//        }
//
//        return events;

        if (userId == null) {
            return Collections.emptyList();
        }

        List<MyCalendarVO> events = myCalendarMapper.getFavoriteEventsWithColor(userId);
        for (MyCalendarVO vo : events) {
            vo.setFavorite(true); // 찜 표시
        }

        return events;


    }


}
