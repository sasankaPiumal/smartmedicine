package com.nibm.smartmedicine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MEDICINES")
public class Medicine extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "price")
    private double price;

}
