package com.cg.api;


import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.LocationRegionDTO;
import com.cg.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/test")
    public String test() {
        return "Hello";
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {

        List<Customer> customers = customerService.findAll();

        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer : customers) {
//            CustomerDTO customerDTO = new CustomerDTO();
//            customerDTO.setId(customer.getId());
//            customerDTO.setFullName(customer.getFullName());
//            customerDTO.setEmail(customer.getEmail());
//            customerDTO.setPhone(customer.getPhone());
//            customerDTO.setBalance(customer.getBalance());
//
//            LocationRegion locationRegion = customer.getLocationRegion();
//            LocationRegionDTO locationRegionDTO = new LocationRegionDTO();
//            locationRegionDTO.setId(locationRegion.getId());
//            locationRegionDTO.setProvinceId(locationRegion.getProvinceId());
//            locationRegionDTO.setProvinceName(locationRegion.getProvinceName());
//            locationRegionDTO.setDistrictId(locationRegion.getDistrictId());
//            locationRegionDTO.setDistrictName(locationRegion.getDistrictName());
//            locationRegionDTO.setWardId(locationRegion.getWardId());
//            locationRegionDTO.setWardName(locationRegion.getWardName());
//            locationRegionDTO.setAddress(locationRegion.getAddress());
//
//            customerDTO.setLocationRegion(locationRegionDTO);

            CustomerDTO customerDTO = customer.toCustomerDTO();

            customerDTOS.add(customerDTO);

        }

        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {

        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);
        customerService.create(customer);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}
