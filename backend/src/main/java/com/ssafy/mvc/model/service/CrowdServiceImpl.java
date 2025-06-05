package com.ssafy.mvc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.CrowdDao;
import com.ssafy.mvc.model.dto.Crowd;
import com.ssafy.mvc.model.dto.SearchCondition;

@Service
public class CrowdServiceImpl implements CrowdService{

	@Autowired
	CrowdDao crowdDao; 
	
	// 1시간 이내 혼잡도 수치 계산
	@Override
	public Map<Integer, Double> getCrowds() {
		
		List<Crowd> list = crowdDao.getCrowdList();
		Map<Integer, Double> map = new HashMap<>();
		
//		int preFacilId = 0;
		for (int i=0; i<list.size(); i++) {
			int idx = i;
			int facilId = list.get(i).getFacilityId();
			int cntNormal = 0;
			int cntManager = 0;
			int sumNormal = 0;
			int sumManager = 0;
			
			while (idx<list.size() && list.get(idx).getFacilityId() == facilId) {
				Crowd curr = list.get(idx);
				
				// 일반 사용자일 때
				if (curr.getManager()==2) {
					cntNormal += 1;
					sumNormal += curr.getRating();
				// 매니저일 때
				} else {
					cntManager += 1;
					sumManager += curr.getRating();
				}
				
				idx += 1;
			}
			double tmp = 0;
			if (cntNormal == 0 && cntManager > 0) {
				tmp = sumManager;
			} else if (cntManager == 0 && cntNormal > 0) {
				tmp = (double)sumNormal/cntNormal;
			} else {
				tmp = (double)(sumNormal*0.3)/cntNormal+(double)(sumManager*0.7)/cntManager;
			}
			map.put(facilId, Math.round(tmp*10)/10.0);
			
			i += cntManager+cntNormal-1;
		}
		return map;
	}

	@Override
	public boolean writeCrowd(Crowd crowd) {
		return crowdDao.insertCrowd(crowd) == 1;
	}
}
