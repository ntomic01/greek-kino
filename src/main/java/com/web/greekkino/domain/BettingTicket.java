package com.web.greekkino.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "betting_ticket")
public class BettingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "draws_id")
    private Draw draws;

    @ManyToOne
    @JoinColumn(name = "system_user")
    private SystemUser systemUser;




}
