package dev.jpa.movie.reservation.domain.entity;

import dev.jpa.movie.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ReservastionAccount extends BaseEntity {

    @Id
    @Column(name = "account_id")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String payment;

    @Column(name = "del_yn", columnDefinition = "VARCHAR(1) default 'N'")
    private String delYn = "N";

    public ReservastionAccount(String id, Reservation reservation, String payment) {
        this.id = id;
        this.reservation = reservation;
        this.payment = payment;
    }

    public ReservastionAccount() {
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void updateDelYn(String delYn) {
        this.delYn = delYn;
    }
}
