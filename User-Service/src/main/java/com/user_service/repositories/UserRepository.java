package com.user_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user_service.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

}
