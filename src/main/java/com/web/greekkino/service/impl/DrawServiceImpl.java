package com.web.greekkino.service.impl;


import com.web.greekkino.domain.Draw;
import com.web.greekkino.domain.dto.OpapDrawResponse;
import com.web.greekkino.enums.DrawStatus;
import com.web.greekkino.repository.DrawRepository;
import com.web.greekkino.service.DrawService;
import com.web.greekkino.service.OpapIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DrawServiceImpl implements DrawService {
    @Autowired
    private DrawRepository drawRepository;
    @Autowired
    private OpapIntegrationService opapIntegrationService;

    @Override
    public void syncData() {
        fetchResult();
        fetchNewDraws();
    }

    private void fetchNewDraws(){
        List<OpapDrawResponse> upcomingDraws = opapIntegrationService.getUpcomingDraws();
        if(CollectionUtils.isEmpty(upcomingDraws)){
            return;
        }
        for (OpapDrawResponse drawResponse: upcomingDraws){
            LocalDateTime drawTime = Instant.ofEpochMilli(drawResponse.getDrawTime())
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
            drawRepository.save(new Draw(drawResponse.getDrawId(), drawTime));

        }

    }


    private void fetchResult(){

        List<Draw> uncompletedDraws = drawRepository.findByStatus(DrawStatus.upcoming);
        if(CollectionUtils.isEmpty(uncompletedDraws)){
            return;
        }
        for(Draw draw: uncompletedDraws){
            OpapDrawResponse response = opapIntegrationService.getCompletedDrawById(draw.getId());
            if(response == null || response.getWinningNumbers() == null){
                continue;
            }
            draw.setStatus(DrawStatus.completed);

            draw.setWinningNumbers(numbersToMap(response.getWinningNumbers().getList()));

            drawRepository.save(draw);

        }

    }

    private Map<Integer, Integer> numbersToMap(List<Integer>numbers){
        /*
            ideja metode je da za listu brojeva dobijem mapu sa redosledom
            npr. ulaz [5, 11, 17, 4, 8]
            izlaz:
             1 - 5
             2 - 11
             3 - 17
             4 - 4
             5 - 8
         */
        int position = 1;
        Map<Integer, Integer> values = new HashMap<>();
        for(Integer number: numbers){
            values.put(position,number);
            position++;
        }
        return values;

    }



}
