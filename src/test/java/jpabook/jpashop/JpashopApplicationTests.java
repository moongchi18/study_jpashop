package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpashopApplicationTests {

	@Test
	void contextLoads() {
		Hello h = new Hello();
		h.setData("TEST");
		System.out.println(h.getData());
	}

}
