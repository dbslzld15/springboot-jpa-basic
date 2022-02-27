package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class BookForm {

    private Long id;

    @NotEmpty(message = "책의 이름은 필수값입니다")
    private String name;

    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
