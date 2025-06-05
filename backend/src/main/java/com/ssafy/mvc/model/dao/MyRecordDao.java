package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.MyRecord;

public interface MyRecordDao {

	List<MyRecord> selectAll(int facilityId, int userId);

	int insertMyRecord(MyRecord myRecord);

	MyRecord selectOne(int id);

	int updateMyRecord(MyRecord myRecord);

	int deleteMyRecord(int id);

	List<MyRecord> selectByDate(int userId, int date);

}
