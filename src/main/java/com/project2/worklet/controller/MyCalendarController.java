package com.project2.worklet.controller;


import com.project2.worklet.calendarService.CalendarDTO;
import com.project2.worklet.calendarService.CalendarService;
import com.project2.worklet.component.CalendarVO;
import com.project2.worklet.component.MyCalendarVO;
import com.project2.worklet.myCalendarService.MyCalendarDTO;
import com.project2.worklet.myCalendarService.MyCalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/mycalendar")
public class MyCalendarController {

    @Autowired
    private MyCalendarService myCalendarService;

    @Autowired
    private HttpSession session; //세션 가져오기

    public String getUserIdFromSession() {
        Object userId = session.getAttribute("userId");

        return userId != null ? userId.toString() : null;
    };

    @GetMapping("/mycalendar")
    public String myCalendar() {
        return "calendar/myCalendar";
    };

    @ResponseBody
    @GetMapping("/events")
    public List<MyCalendarVO> getEvents() {

        String userId = getUserIdFromSession(); //세션에서 userId 가져오기


        if(userId == null) {
            return myCalendarService.getAllEvent(null);
        }

        return myCalendarService.getAllEvent(userId); //내부에 favorite 설정 완료

    };


    //시작일
//    @ResponseBody
//    @GetMapping("/eventsStartDay")
//    public List<MyCalendarVO> geteventsStartDay() {
//        String userId = getUserIdFromSession(); //세션에서 userId 가져오기
//
//        if(userId == null) {
//            return myCalendarService.getStartDayEvents(null);
//        }
//
//        return myCalendarService.getStartDayEvents(userId); //내부에 favorite 설정 완료
//
//    }

    @ResponseBody
    @GetMapping("/eventsStartDay")
    public List<Map<String, Object>> geteventsStartDay(HttpSession session) {
        String userId = getUserIdFromSession();
        if (userId == null) return List.of();
        List<MyCalendarVO> events = myCalendarService.getFavoriteEvents(userId);

        // events가 비어있는지 확인하는 로그
        log.info("Number of favorite events: {}", events.size());

        if (events.isEmpty()) {
            log.info("No favorite events found for user {}", userId);
        }

        return events.stream().map(vo -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", vo.getEmpWantedTitle());
            event.put("start", vo.getEmpWantedStdt()); // 종료일을 start로 사용
            event.put("end", null);

            event.put("empSeqNo", vo.getEmpSeqNo());
            event.put("favorite", vo.isFavorite());
            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
            return event;
        }).collect(Collectors.toList());
    }

    //종료일
//    @GetMapping("/eventsEndDay")
//    @ResponseBody
//    public List<Map<String, Object>> getEndDayEvents(HttpSession session) {
//        String userId = (String) session.getAttribute("userId");
//        List<MyCalendarVO> events = myCalendarService.getEndDayEvents(userId);
//
//        return events.stream().map(vo -> {
//            Map<String, Object> event = new HashMap<>();
//            event.put("title", vo.getEmpWantedTitle());
//            event.put("start", vo.getOnlyEndAsStart()); // 종료일을 start로 사용
//            event.put("empSeqNo", vo.getEmpSeqNo());
//            event.put("favorite", vo.isFavorite());
//            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
//            return event;
//        }).collect(Collectors.toList());
//    }

    //종료일
    @GetMapping("/eventsEndDay")
    @ResponseBody
    public List<Map<String, Object>> getEndDayEvents(HttpSession session) {
        String userId = getUserIdFromSession();
        if (userId == null) return List.of();
        List<MyCalendarVO> events = myCalendarService.getFavoriteEvents(userId);

        // events가 비어있는지 확인하는 로그
        log.info("Number of favorite events: {}", events.size());

        if (events.isEmpty()) {
            log.info("No favorite events found for user {}", userId);
        }

        return events.stream().map(vo -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", vo.getEmpWantedTitle());
            event.put("start", vo.getOnlyEndAsStart()); // 종료일을 start로 사용
            event.put("end", null);
            event.put("empSeqNo", vo.getEmpSeqNo());
            event.put("favorite", vo.isFavorite());
            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
            return event;
        }).collect(Collectors.toList());
    };

    //종료일
//    @GetMapping("/eventsEndDay")
//    @ResponseBody
//    public List<Map<String, Object>> getEndDayEvents(HttpSession session) {
//        String userId = (String) session.getAttribute("userId");
//        List<MyCalendarVO> events = myCalendarService.getEndDayEvents(userId);
//
//        return events.stream().map(vo -> {
//            Map<String, Object> event = new HashMap<>();
//            event.put("title", vo.getEmpWantedTitle());
//            event.put("start", vo.getOnlyEndAsStart()); // 종료일을 start로 사용
//            event.put("empSeqNo", vo.getEmpSeqNo());
//            event.put("favorite", vo.isFavorite());
//            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
//            return event;
//        }).collect(Collectors.toList());
//    }

    //찜 추가
    @PostMapping("/favorite/add")
    @ResponseBody
    public ResponseEntity<Void> addFavorite(@RequestBody MyCalendarDTO request) {
        myCalendarService.addFavorite(request.getEmpSeqNo(), request.getUserId());
        return ResponseEntity.ok().build();
    }

    //찜 삭제
    @PostMapping("/favorite/remove")
    @ResponseBody
    public ResponseEntity<Void> removeFavorite(@RequestBody MyCalendarDTO request) {
        myCalendarService.removeFavorite(request.getEmpSeqNo(), request.getUserId());
        return ResponseEntity.ok().build();
    }



    @GetMapping("/favoriteList")
    @ResponseBody
    public List<Map<String, Object>> getFavoritesOnly(HttpSession session) {
        String userId = getUserIdFromSession();
        if (userId == null) return List.of();
        List<MyCalendarVO> events = myCalendarService.getFavoriteEvents(userId);

        // events가 비어있는지 확인하는 로그
        log.info("Number of favorite events: {}", events.size());

        if (events.isEmpty()) {
            log.info("No favorite events found for user {}", userId);
        }

        return events.stream().map(vo -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", vo.getEmpWantedTitle());
            event.put("start", vo.getEmpWantedStdt()); // 종료일을 start로 사용
            event.put("end", vo.getEmpWantedEndt());
            event.put("empSeqNo", vo.getEmpSeqNo());
            event.put("favorite", vo.isFavorite());
            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
            event.put("color", vo.getColor());
            return event;
        }).collect(Collectors.toList());
    }




    //찜 목록 가져오기

//    @GetMapping("/favorites")
//    public ResponseEntity<List<Integer>> getFavoriteEvents(@RequestParam String userId) {
//        List<Integer> favoriteEmpSeqNos = calendarService.getFavoriteEmpSeqNos(userId);
//
//        return ResponseEntity.ok(favoriteEmpSeqNos);
//
//    }



}
