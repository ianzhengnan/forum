package com.bbs.dao;

import java.util.Iterator;
import org.springframework.stereotype.Repository;

import com.bbs.domain.Board;

@Repository
public class BoardDao extends BaseDao<Board> {

	protected final String GET_BOARD_NUM = "select count(f.boardId) from Board f";
	
	public long getBoardNum(){
		Iterable iter = getHibernateTemplate().iterate(GET_BOARD_NUM);
		return ((Long)iter.next());
	}
}
