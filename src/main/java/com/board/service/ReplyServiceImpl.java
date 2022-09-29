package com.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.ReplyVO;
import com.board.persistence.ReplyMapper;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyMapper mapper;
	
	@Override
	public int add(ReplyVO vo) {
		return mapper.insert(vo);
	}

	@Override
	public ReplyVO getReply(Long repno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int modify(ReplyVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Long repno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReplyVO> getList(Long bno) {
		return mapper.getList(bno);
	}

	
	
	
}
