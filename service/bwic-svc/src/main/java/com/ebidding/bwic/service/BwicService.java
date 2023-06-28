package com.ebidding.bwic.service;

import com.ebidding.bwic.domain.Bwic;
import com.ebidding.bwic.repository.BwicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BwicService {
    @Autowired
    private BwicRepository bwicRepository;

    public Bwic findByCusip(String cusip) {
        return this.bwicRepository.findByCusip(cusip).orElse(null);
    }

}
