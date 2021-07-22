package com.oleksiidev.incidentdashboard.repositories;

import com.oleksiidev.incidentdashboard.model.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    Subscription findSubscriptionById(Long id);

    List<Subscription> findSubscriptionsByEmail(String email);

    void deleteSubscriptionsByEmail(String email);
}
