package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> findAll();

    Page<Customer> findAll(Pageable pageable);

    List<Customer> findAllByIdNot(Long id);

    List<Customer> findAllByEmailLike(String email);

    List<Customer> findAllByKeySearch(String keySearch);

    List<Customer> findAllByFullNameLikeOrEmailLike(String keySearch);

    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String keySearch);

    Customer getOne(Long id);

    Optional<Customer> findById(Long id);

    Boolean existsByEmail(String email);

    Customer save(Customer customer);

    Customer create(Customer customer);

    Customer update(Customer customer);

    Customer deposit(Customer customer, BigDecimal transactionAmount);

    Customer transfer(Transfer transfer);
}
