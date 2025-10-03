package com.ssafy.mvc.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.Crowd;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface CrowdService {
	public Map<Integer, Double> getCrowds();

	public boolean writeCrowd(Crowd crowd);

    Map<Integer, Double> getCrowdSnapshot();
}
