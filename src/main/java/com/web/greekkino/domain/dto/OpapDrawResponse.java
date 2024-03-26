package com.web.greekkino.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpapDrawResponse {

    private Long drawId;

    private Long drawTime;

    private String status;

    private OpapDrawWinningNumbers winningNumbers;
}
