package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.model.Transfer;
import com.cg.repository.CustomerRepository;
import com.cg.repository.LocationRegionRepository;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAllByIdNot(Long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public List<Customer> findAllByEmailLike(String email) {
        return customerRepository.findAllByEmailLike(email);
    }

    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLike(String keySearch) {
        return customerRepository.findAllByFullNameLikeOrEmailLike(keySearch, keySearch);
    }

    @Override
    public List<Customer> findAllByKeySearch(String keySearch) {
        return customerRepository.findAllByKeySearch(keySearch);
    }

    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String keySearch) {
        return customerRepository.findAllByFullNameLikeOrEmailLikeOrPhoneLike(keySearch, keySearch, keySearch);
    }

    @Override
    public Customer getOne(Long id) {
        return customerRepository.getOne(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Customer create(Customer customer) {

        LocationRegion locationRegion = customer.getLocationRegion();
        locationRegion.setId(null);
        locationRegionRepository.save(locationRegion);

        customer.setLocationRegion(locationRegion);
        customerRepository.save(customer);

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer transfer(Transfer transfer) {

        BigDecimal transferAmount = transfer.getTransferAmount();
        Long fees = 10L;
        BigDecimal feesAmount = transferAmount.multiply(BigDecimal.valueOf(10L)).divide(BigDecimal.valueOf(100L));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        transfer.setId(null);
        transfer.setFees(fees);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);
        transferRepository.save(transfer);

        Customer sender = transfer.getSender();
        BigDecimal senderCurrentBalance = sender.getBalance();
        BigDecimal senderNewBalance = senderCurrentBalance.subtract(transactionAmount);
        sender.setBalance(senderNewBalance);
        customerRepository.save(sender);

        Customer recipient = transfer.getRecipient();
        BigDecimal recipientCurrentBalance = recipient.getBalance();
        BigDecimal recipientNewBalance = recipientCurrentBalance.add(transferAmount);
        recipient.setBalance(recipientNewBalance);
        customerRepository.save(recipient);

        return sender;
    }
}
