package com.cg.service.transfer;

import com.cg.model.Customer;
import com.cg.model.Transfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ITransferService {

    List<Transfer> findAll();

    List<Transfer> findAllBySender(Customer sender);

    BigDecimal getAllFeesAmount();

    Optional<Transfer> findOne(Long id);

    Transfer save(Transfer transfer);
}
