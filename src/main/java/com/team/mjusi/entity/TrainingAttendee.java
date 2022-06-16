package com.team.mjusi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "training_attendees", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "training_id"})})
public class TrainingAttendee extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;
}