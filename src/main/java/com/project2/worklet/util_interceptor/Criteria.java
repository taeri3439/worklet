package com.project2.worklet.util_interceptor;

import lombok.Data;

import java.util.List;

@Data
public class Criteria {

    private int page; //페이지번호
    private int count; //페이지당 보여줄 데이터 개수

    private List<String> searchRegion;
    private List<String> searchJob;



    //페이지 처음 진입시 1페이지, 한페이지에 9개 데이터 보여줌
    public Criteria () {
        this.page = 1;
        this.count = 9;
    }

    //pageNum과 count가 변할때 새로운 criteria 생성
    public Criteria (int page, int count) {
        this.page = page;
        this.count = count;
    }

    //마이바티스 sql문에서 필요한 함수
    public int getPageStart() {
        return (page - 1) * count;
    }

    //mysql의 limit함수에서
    //select * from job_posting order by empSeqno desc
    //limit 0, 9;
    //라고 하면 1번부터 9개의 데이터가 조회됨.

}
