package com.bhagwat.scm.orderService.entity;
import com.bhagwat.scm.orderService.common.PartyType;
import jakarta.persistence.*;

@Entity
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    private String partyName;

    @Enumerated(EnumType.STRING)
    private PartyType partyType;

    // Getters and Setters
}

