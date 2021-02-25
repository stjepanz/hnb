package com.hnb.app.repository;

import com.hnb.app.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Users, Integer> {
}
