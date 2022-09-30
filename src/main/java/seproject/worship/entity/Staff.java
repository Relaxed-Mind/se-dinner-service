package seproject.worship.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Staff {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    private String id;
    private String pw;
    private String name;

}
