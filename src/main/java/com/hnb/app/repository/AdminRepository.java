package com.hnb.app.repository;

import com.hnb.app.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Users, Integer> {
}
