package com.bank.accountms.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table (name="accountms",uniqueConstraints = @UniqueConstraint(columnNames = {"account_number"}))
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private Long accountId;
    @Column(name="user_id")
    private Long userId;
    private String account_number;
    private String account_type;
    private BigDecimal balance;
    private String currency_type;
    @CreationTimestamp
    private LocalDateTime created_at;



    //@PrePersist
    public void prePersist()
    {
        LocalDateTime currentDateTime=LocalDateTime.now();
        this.created_at=currentDateTime;
    }
    /*@PreUpdate
    public void preUpdate()
    {
        this.created_at=LocalDateTime.now();
    }*/

}
