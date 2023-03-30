package com.interview.idol.InterviewWithIdol.repository;

import com.interview.idol.InterviewWithIdol.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
}
