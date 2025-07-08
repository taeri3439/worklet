package com.project2.worklet.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobPostingVO2 {

    private int jpno;
    private int empSeqNo;
    private String empWantedTitle;
    private String empBusiNm;
    private String empWantedStdt;
    private String empWantedEndt;
    private String empWantedTypeNm;
    private String regLogImgNm;
    private String empWantedHomepgDetail;
    private String empWantedCareerNm;
    private String workRegionNm;
    private String empRecrNm;
    private String isOpen;
    private boolean isScrapped;

}
