package com.web.greekkino.service;



import com.web.greekkino.domain.dto.OpapDrawResponse;

import java.util.List;

public interface OpapIntegrationService {

    List<OpapDrawResponse> getUpcomingDraws();

    OpapDrawResponse getCompletedDrawById(Long drawId);
}
