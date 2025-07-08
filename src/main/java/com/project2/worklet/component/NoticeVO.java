package com.project2.worklet.component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeVO {
    private Integer noticeId;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime noticeCreatedAt;
}
