package com.ebidding.bwic.service;


import com.ebidding.bwic.domain.Bond;
import com.ebidding.bwic.repository.BondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BondService {

    @Autowired
    private BondRepository bondRepository;


    public String findCuSipByBondId(String bondId) {
        return  this.bondRepository.findByBondId(bondId).orElse(null).getCusip();
    }

    public String getBondid(String cusip) {
        return  this.bondRepository.getBondid(cusip).orElse(null);
    }

    @Transactional
    public void incrementTransactionCount(String bondId) {
        this.bondRepository.findByBondId(bondId).orElseThrow(() -> new RuntimeException("Bond not found: " + bondId));
        this.bondRepository.incrementTransactionCount(bondId);
    }


    public String findIssuerByBondId(String bondId) {
        return  this.bondRepository.findByBondId(bondId).orElse(null).getIssuer();
    }
}
