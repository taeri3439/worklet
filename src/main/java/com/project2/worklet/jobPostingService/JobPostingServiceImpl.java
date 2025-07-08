package com.project2.worklet.jobPostingService;



import com.project2.worklet.component.JobPostingDetailVO;
import com.project2.worklet.component.JobPostingVO2;
import com.project2.worklet.util_interceptor.Criteria;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("JobPosting")
public class JobPostingServiceImpl implements JobPostingService {

    @Autowired
    private JobPostingMapper mapper;

    private final static Logger logger = LoggerFactory.getLogger(JobPostingServiceImpl.class);

    private final String basicUrlL = "https://www.work24.go.kr/cm/openApi/call/wk/callOpenApiSvcInfo210L21.do?authKey=";
    private final String basicUrlD = "https://www.work24.go.kr/cm/openApi/call/wk/callOpenApiSvcInfo210D21.do?authKey=";
    private final String key = "a9f4eb74-7618-4fd6-a80b-4a6a7fff0190";


    @Override
    public int selectSeqMax() {
        return mapper.selectSeqMax();
    }

    @Override
    @Transactional
    public String postList() {

        StringBuilder sb = new StringBuilder(basicUrlL);
        String url = sb.append(key).append("&callTp=L&returnType=XML&startPage=1&display=100").toString();
        List<JobPostingVO2> list = new ArrayList<>();
        List<String> seqList = new ArrayList<>();
        int result = 1230;
        int detailResult = 1230;
        String finalResult = "더 이상 업데이트 될 공채가 없습니다.";

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String xml = response.body();
            JSONObject jsonObject = XML.toJSONObject(xml);
            JSONObject jsonList = jsonObject.getJSONObject("dhsOpenEmpInfoList");

            int maxSeq = mapper.selectSeqMax();

            Object empInfo = jsonList.get("dhsOpenEmpInfo");
            JSONArray infos = empInfo instanceof JSONArray ? (JSONArray) empInfo : new JSONArray().put(empInfo);

            for (int i = 0; i < infos.length(); i++) {
                JSONObject obj = infos.getJSONObject(i);
                if (Integer.parseInt(obj.optString("empSeqno")) > maxSeq) {
                    seqList.add(obj.optString("empSeqno"));

                    JobPostingVO2 vo2 = new JobPostingVO2();
                    vo2.setEmpSeqNo(Integer.parseInt(obj.optString("empSeqno")));
                    vo2.setEmpWantedTitle(obj.optString("empWantedTitle"));
                    vo2.setEmpBusiNm(obj.optString("empBusiNm"));
                    vo2.setEmpWantedStdt(obj.optString("empWantedStdt"));
                    vo2.setEmpWantedEndt(obj.optString("empWantedEndt"));
                    vo2.setEmpWantedTypeNm(obj.optString("empWantedTypeNm"));
                    vo2.setRegLogImgNm(obj.optString("regLogImgNm"));
                    vo2.setEmpWantedHomepgDetail(obj.optString("empWantedHomepgDetail"));

                    list.add(vo2);
                }
            }
            System.out.println("리스트 길이 : "+list.size());
            if (list.size() > 0) {
                result = mapper.postList(list);
                detailResult = postDetail(seqList);
                finalResult = "리스트 저장 결과 : "+result+" 디테일 저장 결과 : "+detailResult;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalResult;
    }

    @Override
    public int postDetail(List<String> list) {


        List<JobPostingDetailVO> dlist = new ArrayList<>();
        int result = -1;
        String jobsCd;

        String empRecrNm;
        String empWantedCareerNm;
        String workRegionNm;
        String jobsCdKorNm;


        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            for(int j = 0; j<list.size(); j++) {

                StringBuilder sb = new StringBuilder(basicUrlD);
                String seqNo = list.get(j);
                String url = sb.append(key).append("&callTp=D&returnType=XML&empSeqno=").append(seqNo).toString();

                System.out.println((j+1)+" 번째");

                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                JSONObject obj = XML.toJSONObject(response.body());
                JSONObject root = obj.getJSONObject("dhsOpenEmpInfoDetailRoot");

                int empSeqNo = root.getInt("empSeqno");

                Object object1 = root.getJSONObject("empRecrList").get("empRecrListInfo");
                if (object1 instanceof JSONArray) {
                    JSONObject empRecr = root.getJSONObject("empRecrList").getJSONArray("empRecrListInfo").getJSONObject(0);
                    empRecrNm = empRecr.getString("empRecrNm");
                    empWantedCareerNm = empRecr.getString("empWantedCareerNm");
                    workRegionNm = empRecr.getString("workRegionNm");
                } else {
                    JSONObject empRecr = root.getJSONObject("empRecrList").getJSONObject("empRecrListInfo");
                    empRecrNm = empRecr.getString("empRecrNm");
                    empWantedCareerNm = empRecr.getString("empWantedCareerNm");
                    workRegionNm = empRecr.getString("workRegionNm");
                }


                Object object = root.getJSONObject("empJobsList").get("empJobsListInfo");
                if (object instanceof JSONArray) {
                    JSONObject empJobs = root.getJSONObject("empJobsList").getJSONArray("empJobsListInfo").getJSONObject(0);
                    Object obj3 = empJobs.get("jobsCd");
                    if (obj3 instanceof String) {
                        jobsCd = obj3.toString();
                    } else {
                        jobsCd = String.valueOf(empJobs.getInt("jobsCd"));
                    }
                    jobsCdKorNm = empJobs.getString("jobsCdKorNm");
                } else {
                    JSONObject empJobs = root.getJSONObject("empJobsList").getJSONObject("empJobsListInfo");
                    Object obj3 = empJobs.get("jobsCd");
                    if (obj3 instanceof String) {
                        jobsCd = obj3.toString();
                    } else {
                        jobsCd = String.valueOf(empJobs.getInt("jobsCd"));
                    }
                    jobsCdKorNm = empJobs.getString("jobsCdKorNm");
                }


                JobPostingDetailVO detail = new JobPostingDetailVO(null, empSeqNo, empRecrNm, empWantedCareerNm, workRegionNm,
                                                                    jobsCd, jobsCdKorNm);
                dlist.add(detail);
            }
            result = mapper.postDetail(dlist);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<JobPostingVO2> getList(Criteria cri, String userId) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return mapper.getList(cri, userId);
    }

    @Override
    public int getTotal(Criteria cri) {
        return mapper.getTotal(cri);
    }

    @Override

    public List<JobPostingVO2> getRecentJobPostings() {
        return mapper.getRecentJobPostings();
    }



    public int scrapJob(String userId, String empNoToScrap) {
        return mapper.scrapJob(userId, empNoToScrap);
    }
    @Override
    public int unscrapJob(String userId, String empNoToScrap) {
        return mapper.unscrapJob(userId, empNoToScrap);
    }



}




//    @Override
//    public String getApi() {
//
//        StringBuilder sb = new StringBuilder(basicUrl);
//        String url = sb.append(key).append("&callTp=L&returnType=XML&startPage=1&display=10").toString();
//
//        try {
//            // HttpClient 생성
//            HttpClient httpClient = HttpClient.newHttpClient();
//
//            // HttpRequest 생성
//            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
//
//            // 요청 보내고 응답 받기 (동기 방식)
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//            // 2. XML을 JSON으로 변환
//            String xml = response.body();
//            JSONObject jsonObject = XML.toJSONObject(xml);
//
//            // 들여쓰기 2칸
//            System.out.println(jsonObject.toString(2));
//
//            // 응답 출력
//            System.out.println("Status Code: " + response.statusCode());
//            System.out.println("Response Body:");
//            System.out.println(response.body());
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "됨?";
//    }