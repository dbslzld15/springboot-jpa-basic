package jpabook.jpashop.domain.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.address.Address;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //EnumType.ORDINAL 을 쓰면 숫자로 들어가서 위험
    private DeliveryStatus status; //READY, COMP
}
