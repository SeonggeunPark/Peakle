package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.MyRecordDao;
import com.ssafy.mvc.model.dto.MyRecord;

@Service
public class MyRecordServiceImpl implements MyRecordService{
	
	private final MyRecordDao myRecordDao;
	
	public MyRecordServiceImpl(MyRecordDao myRecordDao) {
		this.myRecordDao = myRecordDao;
	}

	@Override
	public List<MyRecord> selectAll(int facilityId, int userId) {
		return myRecordDao.selectAll(facilityId,userId);
	}

	@Override
	public boolean writeMyRecord(MyRecord myRecord) {
		return myRecordDao.insertMyRecord(myRecord) == 1;
	}

	@Override
	public MyRecord selectOne(int id) {
		return myRecordDao.selectOne(id);
	}

	@Override
	public boolean modifyMyRecord(MyRecord myRecord) {
		return myRecordDao.updateMyRecord(myRecord) == 1;
	}

	@Override
	public boolean removeMyRecord(int id) {
		return myRecordDao.deleteMyRecord(id) == 1;
	}

	@Override
	public List<MyRecord> selectByDate(int userId, int date) {
		return myRecordDao.selectByDate(userId, date);
	}

}
