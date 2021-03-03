package com.hnb.app.repository;

import com.hnb.app.models.Logger;;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerRepository extends CrudRepository<Logger, Integer> {
}
