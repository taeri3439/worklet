package com.project2.worklet.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyCalendarVO {

    private String empSeqNo;
    private String empWantedTitle;
    private String empBusiNm;
    private String empWantedStdt;
    private String empWantedEndt;
    private String empWantedTypeNm;
    private String regLogImgNm;
    private String empWantedHomepgDetail;
    private String color;

    private boolean favorite;

    public String getTitle() {
        return empWantedTitle;
    }

    public String getStart() {
        if(empWantedStdt != null && empWantedStdt.length() == 8) {
            return empWantedStdt.substring(0, 4) + "-" + empWantedStdt.substring(4, 6) + "-" + empWantedStdt.substring(6, 8);
        }
        return null;
    }

    public String getOnlyEndAsStart() {
        if(empWantedEndt != null && empWantedEndt.length() == 8) {
            return empWantedEndt.substring(0, 4) + "-" + empWantedEndt.substring(4, 6) + "-" + empWantedEndt.substring(6, 8);
        }
        return null;
    }


}
