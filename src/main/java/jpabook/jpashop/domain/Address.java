package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address { // 값 타입은 변경 불가능하게 설계해야함.
    // Setter를 제거하고 생성자에서 값을 모두 초기화해서 변경 불가능한 클래스를 만들자

    private String city;
    private String street;
    private String zipcode;

    //jpa 스팩상 만들어줌
    protected Address() { // jpa에서 리플랙션이나 프록시같은 기술을 사용해 엔티티를 생성할때가 많은데 이를 위해 기본 생성자를 만들어줌
    } // public은 여러 사람들이 사용할 수 있으니 protected로 사용

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
