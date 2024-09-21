package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.item.Item;
import lombok.Data;

@Entity
@DiscriminatorValue("M")
@Data
public class Movie extends Item {

    private String director;
    private String actor;
}
