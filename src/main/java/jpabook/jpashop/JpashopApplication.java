package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpashopApplication.class, args);
        System.out.println("Hello World!");
    }

    @Bean
    Hibernate5JakartaModule hibernate5Module() {
        Hibernate5JakartaModule hibernate5Module = new Hibernate5JakartaModule();
        //강제 지연 로딩 설정 (권장되지 않음)
        hibernate5Module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
        return hibernate5Module;
    }
}
