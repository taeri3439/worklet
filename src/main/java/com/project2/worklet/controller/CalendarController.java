package com.project2.worklet.controller;

import com.project2.worklet.calendarService.CalendarDTO;
import com.project2.worklet.calendarService.CalendarService;
import com.project2.worklet.component.CalendarVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
//    @Qualifier("calendar")
    private CalendarService calendarService;

    @Autowired
    private HttpSession session; //세션 가져오기


    public String getUserIdFromSession() {

        Object userId = session.getAttribute("userId");

        return userId != null ? userId.toString() : null;

    };



    @GetMapping("/calendar")
    public String calendar() {
      return "calendar/calendar";
    };

    @ResponseBody
    @GetMapping("/events")
    public List<CalendarVO> getEvents() {

        String userId = getUserIdFromSession(); //세션에서 userId 가져오기


        if(userId == null) {
            return calendarService.getAllEvent(null);
        }

        return calendarService.getAllEvent(userId); //내부에 favorite 설정 완료
    }


    //시작일
    @ResponseBody
    @GetMapping("/eventsStartDay")
    public List<CalendarVO> geteventsStartDay() {
        String userId = getUserIdFromSession(); //세션에서 userId 가져오기

        if(userId == null) {
            return calendarService.getStartDayEvents(null);
        }

        return calendarService.getStartDayEvents(userId); //내부에 favorite 설정 완료

    }

    //종료일
    @GetMapping("/eventsEndDay")
    @ResponseBody
    public List<Map<String, Object>> getEndDayEvents(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        List<CalendarVO> events = calendarService.getEndDayEvents(userId);

        return events.stream().map(vo -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", vo.getEmpWantedTitle());
            event.put("start", vo.getOnlyEndAsStart()); // 종료일을 start로 사용
            event.put("empSeqNo", vo.getEmpSeqNo());
            event.put("favorite", vo.isFavorite());
            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
            return event;
        }).collect(Collectors.toList());
    }

    //공채시작일
    @ResponseBody
    @GetMapping("/getStartDayEventsOnlyY")
    public List<Map<String, Object>> getStartDayEventsOnlyY(HttpSession session) {
//        String userId = getUserIdFromSession(); //세션에서 userId 가져오기
//
//        if(userId == null) {
//            return calendarService.getStartDayEventsOnlyY(null);
//        }
//
//        return calendarService.getStartDayEvents(userId); //내부에 favorite 설정 완료


        String userId = (String) session.getAttribute("userId");
        List<CalendarVO> events = calendarService.getStartDayEventsOnlyY(userId);

        return events.stream().map(vo -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", vo.getEmpWantedTitle());
            event.put("start", vo.getStart()); //
            event.put("empSeqNo", vo.getEmpSeqNo());
            event.put("favorite", vo.isFavorite());
            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
            return event;
        }).collect(Collectors.toList());

    }

    //공채종료일
    @GetMapping("/getEndDayEventsOnlyY")
    @ResponseBody
    public List<Map<String, Object>> getEndDayEventsOnlyY(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        List<CalendarVO> events = calendarService.getEndDayEventsOnlyY(userId);

        return events.stream().map(vo -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", vo.getEmpWantedTitle());
            event.put("start", vo.getOnlyEndAsStart()); // 종료일을 start로 사용
            event.put("empSeqNo", vo.getEmpSeqNo());
            event.put("favorite", vo.isFavorite());
            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
            return event;
        }).collect(Collectors.toList());
    }

    //채용시작일
    @ResponseBody
    @GetMapping("/getStartDayEventsOnlyN")
    public List<Map<String, Object>> getStartDayEventsOnlyN(HttpSession session) {
//        String userId = getUserIdFromSession(); //세션에서 userId 가져오기
//
//        if(userId == null) {
//            return calendarService.getStartDayEventsOnlyN(null);
//        }
//
//        return calendarService.getStartDayEvents(userId); //내부에 favorite 설정 완료

        String userId = (String) session.getAttribute("userId");
        List<CalendarVO> events = calendarService.getStartDayEventsOnlyN(userId);

        return events.stream().map(vo -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", vo.getEmpWantedTitle());
            event.put("start", vo.getStart()); //
            event.put("empSeqNo", vo.getEmpSeqNo());
            event.put("favorite", vo.isFavorite());
            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
            return event;
        }).collect(Collectors.toList());

    }

    //채용종료일
    @GetMapping("/getEndDayEventsOnlyN")
    @ResponseBody
    public List<Map<String, Object>> getEndDayEventsOnlyN(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        List<CalendarVO> events = calendarService.getEndDayEventsOnlyN(userId);

        return events.stream().map(vo -> {
            Map<String, Object> event = new HashMap<>();
            event.put("title", vo.getEmpWantedTitle());
            event.put("start", vo.getOnlyEndAsStart()); // 종료일을 start로 사용
            event.put("empSeqNo", vo.getEmpSeqNo());
            event.put("favorite", vo.isFavorite());
            event.put("empWantedHomepgDetail", vo.getEmpWantedHomepgDetail());
            return event;
        }).collect(Collectors.toList());
    }


    //찜 추가
    @PostMapping("/favorite/add")
    @ResponseBody
    public ResponseEntity<Void> addFavorite(@RequestBody CalendarDTO request) {
        calendarService.addFavorite(request.getEmpSeqNo(), request.getUserId(), request.getColor());
        return ResponseEntity.ok().build();
    }

    //찜 삭제
    @PostMapping("/favorite/remove")
    @ResponseBody
    public ResponseEntity<Void> removeFavorite(@RequestBody CalendarDTO request) {
        calendarService.removeFavorite(request.getEmpSeqNo(), request.getUserId());
        return ResponseEntity.ok().build();
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
