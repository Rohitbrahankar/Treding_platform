package com.treding_backend.tredingbackend.Repository;

import com.treding_backend.tredingbackend.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

   User findByEmail(String email);

}
