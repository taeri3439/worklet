package com.project2.worklet.qna.service;
import com.project2.worklet.component.QnaVO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaService  {
    int qnaForm(QnaVO vo);
    List<QnaVO> qnalist();
    void updateReply(QnaVO vo);
    void qnaReply(QnaVO vo);
    QnaVO getQnaById(Integer inquiryId);
    List<QnaVO> getQnaListByUserNum(int userNum);

    // 검색 기능을 위한 쿼리 추가
    List<QnaVO> searchQnaList(@Param("keyword") String keyword);

}
