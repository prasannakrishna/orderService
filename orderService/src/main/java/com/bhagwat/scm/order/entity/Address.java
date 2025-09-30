package com.bhagwat.scm.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
// FIX: Removed the 'schema' attribute to default to the existing 'public' schema.
@Table(name = "\"address\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    private String line1;
    private String line2;
    private String line3;
    private String post;
    private String city;
    private String pincode;
    private String contactNumber;
    private String email;

    // Lombok handles Getters and Setters via @Data
}
