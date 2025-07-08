package com.project2.worklet.qna.service;



import com.project2.worklet.component.QnaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QnaServiceImpl implements QnaService {

    @Autowired
    private QnaMapper qnaMapper;


    @Override
    public int qnaForm(QnaVO vo) {
        return qnaMapper.qnaForm(vo);
    }

    @Override
    public List<QnaVO> qnalist() {
        return qnaMapper.qnalist();
    }

    @Override
    public void updateReply(QnaVO vo) {
     qnaMapper.updateReply(vo);
    }

    @Override
    public void qnaReply(QnaVO vo) {
        qnaMapper.updateReply(vo);
    }

    @Override
    public QnaVO getQnaById(Integer inquiryId) {
        return qnaMapper.getQnaById(inquiryId);
    }

    @Override
    public List<QnaVO> getQnaListByUserNum(int userNum) {
        return qnaMapper.getQnaListByUserNum(userNum);
    }

    @Override
    public List<QnaVO> searchQnaList(String keyword) {
        return qnaMapper.searchQnaList(keyword);
    }


}

