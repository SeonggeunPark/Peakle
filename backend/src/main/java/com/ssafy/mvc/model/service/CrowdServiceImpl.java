package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dao.CrowdDao;
import com.ssafy.mvc.model.dto.Crowd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrowdServiceImpl implements CrowdService{

	@Autowired
	CrowdDao crowdDao;
    // 1시간마다 혼잡도 계산 메서드
    private volatile Map<Integer, Double> crowdMap = new HashMap<>(); // ← volatile

    @Scheduled(fixedRate = 1000 * 60 * 3) // fixedRate 권장(드리프트 줄이기)
    public void updateCrowdList() {
        crowdMap = Map.copyOf(getCrowds());  // 새 Map으로 통째 교체(변경 X, 교체 O)
    }
    // 컨트롤러에서 캐시 읽도록 제공
    public Map<Integer, Double> getCrowdSnapshot() {
        return crowdMap;
    }

	// 1시간 이내 혼잡도 수치 계산
	@Override
	public Map<Integer, Double> getCrowds() {

		List<Crowd> list = crowdDao.getCrowdList();
		Map<Integer, Double> map = new HashMap<>();
		
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
				tmp = (double) sumManager /cntManager;
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
