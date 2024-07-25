package com.fabriciocesar.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabriciocesar.demo.models.User;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> buscarPeloIdentificador(Long id);

}
