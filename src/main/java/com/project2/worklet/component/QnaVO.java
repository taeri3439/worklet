package com.project2.worklet.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaVO {
    private Integer inquiryId;
    private Integer userNum;
    private String inquiryTitle;
    private String inquiryContent;
    private String inquiryStatus;
    private LocalDateTime inquiryCreateAt;
    private LocalDateTime inquiryUpdateAt;
    private String inquiryReply;

}
