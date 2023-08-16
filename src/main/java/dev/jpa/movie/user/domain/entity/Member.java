package dev.jpa.movie.user.domain.entity;

import dev.jpa.movie.common.entity.BaseEntity;
import dev.jpa.movie.reservation.domain.entity.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(of = {"id", "name", "phoneNumber"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    private String id;

    private String name;

    private String phoneNumber;

    private String pwd;

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Reservation> reservation = new ArrayList<>();

    public Member(String id, String name, String phoneNumber, String pwd) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pwd = pwd;
    }
}
