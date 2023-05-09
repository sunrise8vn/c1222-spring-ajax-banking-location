package com.cg.api;


import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.LocationRegion;
import com.cg.model.dto.*;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping("/test")
    public String test() {
        return "Hello";
    }

//    @GetMapping
//    public ResponseEntity<List<CustomerDTO>> getAll() {
//
//        List<Customer> customers = customerService.findAll();
//
//        List<CustomerDTO> customerDTOS = new ArrayList<>();
//
//        for (Customer customer : customers) {
//            CustomerDTO customerDTO = customer.toCustomerDTO();
//            customerDTOS.add(customerDTO);
//        }
//
//        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<?> getAllPageable(@PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 5) Pageable pageable) {

        Page<Customer> customers = customerService.findAll(pageable);

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerCreateReqDTO customerCreateReqDTO, BindingResult bindingResult) {

        new CustomerCreateReqDTO().validate(customerCreateReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existEmail = customerService.existsByEmail(customerCreateReqDTO.getEmail());

        if (existEmail) {
            throw new EmailExistsException("Email đã tồn tại");
        }


        Customer customer = customerCreateReqDTO.toCustomer(null, BigDecimal.ZERO);

        customerService.create(customer);

        return new ResponseEntity<>(customer.toCustomerCreateResDTO(), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody CustomerUpdateReqDTO customerUpdateReqDTO) {

//        Boolean existEmail = customerService.existsByEmail(customerCreateReqDTO.getEmail());
//
//        if (existEmail) {
//            throw new EmailExistsException("Email đã tồn tại");
//        }


        Customer customer = customerUpdateReqDTO.toCustomer();

        customerService.update(customer);

        return new ResponseEntity<>(customer.toCustomerCreateResDTO(), HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositReqDTO depositReqDTO) {

        Optional<Customer> customerOptional = customerService.findById(depositReqDTO.getCustomerId());

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Thông tin khách hàng không tồn tại");
        }

        Customer customer = customerOptional.get();
        BigDecimal transactionAmount = depositReqDTO.getTransactionAmount();

        customerService.deposit(customer, transactionAmount);

        CustomerDTO customerDTO = customer.toCustomerDTO();

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }
}
