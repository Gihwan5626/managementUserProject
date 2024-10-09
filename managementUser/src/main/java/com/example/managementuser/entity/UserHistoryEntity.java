package com.example.managementuser.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import com.example.managementuser.type.ActionType;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table(name = "user_history")
public class UserHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_idx")
    private Long historyIdx;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(name = "action_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Column(name = "reg_user_idx", nullable = false)
    private Integer regUserIdx;

    @Column(name = "reg_ip", nullable = false, length = 16)
    private String regIp;

    @CreationTimestamp
    @Column(name = "reg_dt", nullable = false)
    private LocalDateTime regDt;

}
