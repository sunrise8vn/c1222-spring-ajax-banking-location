package com.cg.repository;

import com.cg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByIdNot(Long id);

    List<Customer> findAllByEmailLike(String email);

    List<Customer> findAllByFullNameLikeOrEmailLike(String fullName, String email);

    @Query("SELECT cus " +
            "FROM Customer AS cus " +
            "JOIN LocationRegion AS lr " +
            "ON cus.locationRegion = lr " +
            "AND (cus.fullName LIKE :keySearch " +
            "OR cus.email LIKE :keySearch " +
            "OR cus.phone LIKE :keySearch " +
            "OR lr.address LIKE :keySearch)"
    )
    List<Customer> findAllByKeySearch(@Param("keySearch") String keySearch);

    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String fullName, String email, String phone);

    Boolean existsByEmail(String email);


    @Modifying
    @Query("UPDATE Customer AS cus SET cus.balance = cus.balance + :transactionAmount WHERE cus = :customer")
    void incrementBalance(@Param("customer") Customer customer, @Param("transactionAmount") BigDecimal transactionAmount);

}
