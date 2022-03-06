package jpabook.jpashop.api;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.domain.order.OrderSearch;
import jpabook.jpashop.repository.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.simplequery.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() { //양방향 연관관계가 걸린곳은 무한루프를 방지하기 위해 한곳은 @JsonIgnore을 사용
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Member를 조회하기 위해 연관 엔티티 필드를 불러옴 (프록시때문)
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());

        // N+1 문제가 발생, Order 쿼리 1개를 위해 회원 N + 배송 N 개의 추가 쿼리 발생
       List<OrderSimpleQueryDto> collect = orders.stream()
                .map(o -> new OrderSimpleQueryDto(o))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderSimpleQueryDto> collect = orders.stream()
                .map(o -> new OrderSimpleQueryDto(o))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result ordersV4() {
        List<OrderSimpleQueryDto> orderDtos = orderSimpleQueryRepository.findOrderDtos();
        return new Result(orderDtos.size(), orderDtos);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}
