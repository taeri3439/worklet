package com.project2.worklet.util_interceptor;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageVO {

    private int start;//시작페이지 번호
    private int end;//끝페이지 번호
    private boolean prev; //이전페이지 있는가?
    private boolean next; //다음페이지 있는가?

    private int page; //현재 조회하는 페이지번호
    private int amount; //한페이지에서 보여주는 데이터 개수
    private int total; //총 게시물 수
    private int realEnd; //페이지 끝번호

    private Criteria cri;
    private List<Integer> pageList = new ArrayList<>();
    //화면에서 start 부터 end까지 계산된 결과를 담아놓기 위한 번호리스트(타임리프의 향상된 for문)
    private int pageCount = 5;//화면에 그려지는 페이지네이션 개수


    public PageVO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;
        this.page = cri.getPage();
        this.amount = cri.getCount();

        //페이지 끝번호 계산
        //데이터가 165개일때 실제 끝번호가 17
        //(int)Math.ceil(총 게시글 수/현재조회하는 데이터 개수)
        this.end = (int)(Math.ceil(this.page / 10.0)) * 10;
        this.start = end - 10 +1;
        this.realEnd = (int)Math.ceil(total / (double)this.amount);

        //실제 마지막번호를 다시 계산
        //데이터가 165개일때
        //1~10페이지 조회시 end값은 10, realEnd = 17
        //11~20페이지 조회시 end값은 20, realEnd = 17
        if(this.end > realEnd) this.end = realEnd;

        //이전 버튼 활성화 여부
        this.prev = this.start > 1;
        this.next = realEnd > this.end;

        //페이지리스트 초기화
        for(int i = start; i<=end; i++) {
            pageList.add(i);
        }
    }

}
