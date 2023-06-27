package com.ebidding.service.domain;

//import lombok.Data;
//
//@Data
//public class Account {
//    private Long id;
//    private String Name;
////    public static void main(String args[]){
////        Account ac = new Account();
////        ac.setId(1120L);
////        System.out.println(ac.getId());
////    }
//}

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AccountRole role;

    private String password_hash;
}
