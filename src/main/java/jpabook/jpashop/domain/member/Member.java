package jpabook.jpashop.domain.member;

import jpabook.jpashop.domain.address.Address;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //order Table에 있는 member 필드에 의해서 맵핑된 것
    private List<Order> orders = new ArrayList<>();
}
