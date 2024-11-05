package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@Transactional
class OrderServiceTest {

    @Autowired private EntityManager em;
    @Autowired private OrderService orderService;
    @Autowired private ItemService itemService;

    @Test
    @DisplayName("상품주문")
    void test1() {
        // given
        Member member = createMember();
        em.persist(member);

        Book book = createBook("JPA", 10000, 11);
        em.persist(book);

        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = em.find(Order.class, orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000*orderCount, getOrder.getTotalPrice());
        assertEquals(9, book.getStockQuantity());
    }

    private static Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }

    private static Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("대한민국", "경기도", "성남"));
        return member;
    }

    @Test
    @DisplayName("주문취소")
    void test2() {
        // given
        Member member = createMember();
        em.persist(member);
        Book book = createBook("JPA", 10000, 11);
        em.persist(book);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);


        // then
        Order getOrder = em.find(Order.class, orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(11, book.getStockQuantity());
    }

    @Test
    @DisplayName("상품주문_재고수량초과")
    void test3() {
        // given"
        Member member = createMember();
        em.persist(member);
        Item item = createBook("TWO JPA", 10000, 11);
        em.persist(item);
        System.err.println("============================= " + member);
        System.err.println("============================= " + item);
        int orderCount = 12;

        // when

        // then
        assertThrows(
                NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount));

    }
}