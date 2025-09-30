package com.bhagwat.scm.order.entity;

import com.bhagwat.scm.order.common.PartyType;
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

