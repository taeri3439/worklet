package com.project2.worklet.component;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResumeVO {


    private Long resumeId;
    private int userNum;
    @JsonProperty("resume_title")
    private String title;
    @JsonProperty("userName")
    private String userName;
    private String growth;
    private String studentDay;
    private String prosAndCons;
    private String aspiration;
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 연관된 학력, 경력, 자격증 리스트
    private List<EduVO> educationList;
    private List<CareerVO> careerList;
    private List<LicenseVO> licenseList;

    // ID 리스트 (Insert/Update용)
    private List<Long> educationIds;
    private List<Long> careerIds;
    private List<Long> licenseIds;

    @JsonProperty("resumeEarlyLife")
    private String resumeEarlyLife;

    @JsonProperty("resumeStrengthWeakness")
    private String resumeStrengthWeakness;

    @JsonProperty("resumeSchoolCareer")
    private String resumeSchoolCareer;

    @JsonProperty("resumeApplyAfterDream")
    private String resumeApplyAfterDream;

    private String photoFileName;
    private String photoFilePath;


}
