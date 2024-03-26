package com.web.greekkino.domain;


import com.web.greekkino.enums.DrawStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "draws")
public class Draw {

    @Id
    private Long id;

    private LocalDateTime drawTime;

    @Enumerated(EnumType.STRING)
    private DrawStatus status;

    @ElementCollection
    private Map<Integer, Integer> winningNumbers;

    public Draw(Long id, LocalDateTime drawTime){
        this.id = id;
        this.drawTime = drawTime;
        this.status = DrawStatus.upcoming;
    }
    @ManyToOne
    private BettingTicket bettingTicket;

}
