package com.cg.model;

import com.cg.model.dto.CustomerCreateResDTO;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.CustomerUpdateReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@Accessors(chain = true)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

//    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email chưa đúng định dạng")
    @Column(unique = true, nullable = false)
    private String email;
    private String phone;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;

    @Column(precision = 10, scale = 0, nullable = false, updatable = false)
    private BigDecimal balance;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Transfer> senders;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Transfer> recipients;


    public CustomerDTO toCustomerDTO() {
        return new CustomerDTO()
            .setId(id)
            .setFullName(fullName)
            .setEmail(email)
            .setPhone(phone)
            .setBalance(balance)
            .setLocationRegion(locationRegion.toLocationRegionDTO())
            ;
    }

    public CustomerCreateResDTO toCustomerCreateResDTO() {
        return new CustomerCreateResDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                ;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", locationRegion=" + locationRegion +
                ", balance=" + balance +
                '}';
    }

}
