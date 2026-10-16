
package com.juhi.E_commerce.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrder, Long> {

    // Find one order by ID and mobile number
    Optional<CustomerOrder> findByIdAndPhone(Long id, String phone);

    // Find all orders associated with a mobile number
    List<CustomerOrder> findByPhoneOrderByCreatedAtDesc(String phone);
}
