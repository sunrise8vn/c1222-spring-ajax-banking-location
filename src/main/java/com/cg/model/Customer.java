package com.cg.model;

import com.cg.model.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

//    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email chưa đúng định dạng")
    private String email;
    private String phone;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;

    @Column(precision = 10, scale = 0, nullable = false)
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

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;

        String fullName = customer.getFullName();
        String email = customer.getEmail();

        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "fullName.length");
        }

        if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            errors.rejectValue("email", "email.match");
        }
    }

}
