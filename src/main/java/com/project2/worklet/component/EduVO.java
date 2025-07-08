package com.project2.worklet.component;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduVO {

    private int userNum;
    private Long educationId;
    private String schoolName;
    private String major;
    private String part;  // 이수형태 (주간, 야간 등)
    private String degreeType;  // 학위 종류 (학사, 석사 등)
    private String graduationStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")// 졸업 여부 (졸업, 재학 등)
    private LocalDate graduationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")// 졸업일
    private LocalDate createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")// 생성일
    private LocalDate updatedAt;  // 수정일
    private Long resumeId;

    private String formattedGraduationDate;
}
