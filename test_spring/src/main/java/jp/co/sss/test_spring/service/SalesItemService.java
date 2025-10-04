package jp.co.sss.test_spring.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.test_spring.entity.SalesItem;
import jp.co.sss.test_spring.repository.SalesItemRepository;

@Service
public class SalesItemService {

    @Autowired
    private SalesItemRepository salesItemRepository;

    public List<SalesItem> getActiveSalesItems() {
        return salesItemRepository.findByEndMonthAfter(LocalDate.now());
    }
    
    public List<SalesItem> findByProductId(Integer productId) {
        return salesItemRepository.findByProductId(productId);
    }
}
