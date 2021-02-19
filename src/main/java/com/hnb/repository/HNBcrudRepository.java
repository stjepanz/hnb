package com.hnb.repository;

import com.hnb.models.Tecajevi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HNBcrudRepository extends CrudRepository<Tecajevi, Integer> {
}
