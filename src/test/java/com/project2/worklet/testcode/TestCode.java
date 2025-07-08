package com.project2.worklet.testcode;

import com.project2.worklet.WorkletApplication;
import com.project2.worklet.component.JobPostingDetailVO;
import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.jobPostingService.JobPostingMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = WorkletApplication.class)
public class TestCode {

    @Autowired
    JobPostingMapper mapper;

    @Test
    public void test01() {

        List<JobPostingVO2> list = new ArrayList<>();
        List<JobPostingDetailVO> dlist = new ArrayList<>();

            for (int i = 100200; i < 100301; i++) {

                    JobPostingVO2 vo2 = new JobPostingVO2();
                    vo2.setEmpSeqNo(i);
                    vo2.setEmpWantedTitle("회계/재무 경력직 "+i);
                    vo2.setEmpBusiNm("WORKLET");
                    vo2.setEmpWantedStdt("20250505");
                    vo2.setEmpWantedEndt("20250615");
                    vo2.setEmpWantedTypeNm("정규직");
                    vo2.setRegLogImgNm("C:\\Users\\user\\Desktop\\WORKLET_logo.png");
                    vo2.setEmpWantedHomepgDetail("");
                    vo2.setIsOpen("N");

                    list.add(vo2);
            }
        System.out.println(mapper.postList(list));

        for (int i = 100200; i < 100301; i++) {

            JobPostingDetailVO detail = new JobPostingDetailVO(null,
                    i, "회계,재무 사원/대리/과장급", "경력|신입", "서울 강남구",
                    "027100", "회계 사무원");

            dlist.add(detail);
        }
        System.out.println(mapper.postDetail(dlist));
    }
}
