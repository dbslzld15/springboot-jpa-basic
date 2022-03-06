package jpabook.jpashop.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * xToOne(ManyToOne, OneToOne) 에서의 성능 최적화)
 * Order 조회
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() { //양방향 연관관계가 걸린곳은 무한루프를 방지하기 위해 한곳은 @JsonIgnore을 사용
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Member를 조회하기 위해 연관 엔티티 필드를 불러옴 (프록시때문)
        }
        return all;
    }

}
