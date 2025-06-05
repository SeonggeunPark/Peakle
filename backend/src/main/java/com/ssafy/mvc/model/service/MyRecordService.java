package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.MyRecord;

public interface MyRecordService {

	List<MyRecord> selectAll(int facilityId, int userId);

	boolean writeMyRecord(MyRecord myRecord);

	MyRecord selectOne(int id);

	boolean modifyMyRecord(MyRecord myRecord);

	boolean removeMyRecord(int id);

	List<MyRecord> selectByDate(int userId, int date);

}
