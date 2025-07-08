
package com.project2.worklet.component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private int userNum;
    private String userId;
    private String userPw;
    private String userName;
    private String userGender;
    private String userPhone;
    private String userEmail;
    private String userAddress;
    private String userBirthday;
    private String wantJobNum;
    private String wantJobType1;
    private String wantJobType2;
    private String wantJobType3;
    private String[] wantJobType;

    private String[] preferredJobTypes;
    private int wantJobWorkexp;
    private String wantJobWorkexpHowlong;
    private LocalDateTime joinDate;
    private String preferredJobType1;
    private String preferredJobType2;
    private String preferredJobType3;


    // 학력, 경력 리스트
    private List<EduVO> educationList;  // 학력 리스트
    private List<CareerVO> careerList;  // 경력 리스트
    private List<LicenseVO> licenseList;  // 자격증 리스트
}
