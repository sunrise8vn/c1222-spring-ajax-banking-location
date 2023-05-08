package com.cg.model.dto;

import com.cg.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerCreateReqDTO implements Validator {

    private String fullName;
    private String email;
    private String phone;
    private LocationRegionDTO locationRegion;

    public Customer toCustomer(Long id, BigDecimal balance) {
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegion())
                ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerCreateReqDTO customer = (CustomerCreateReqDTO) target;

        String fullName = customer.getFullName();
        String email = customer.getEmail();
        String phone = customer.getPhone();

        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "fullName.length", "Độ dài họ tên phải lớn hơn 0");
        }

        if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            errors.rejectValue("email", "email.match", "Email chưa đúng định dạng");
        }

        if (!phone.matches("\\d+")) {
            errors.rejectValue("phone", "phone.match", "Số điện thoại phải là ký tự số");
        }

    }
}
