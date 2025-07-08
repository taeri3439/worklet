package com.project2.worklet.calendarService;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CalendarDTO {

    private int empSeqNo;
    private String userId;
    private String color; //color 필드 추가

}
