package com.fabriciocesar.demo.services;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabriciocesar.demo.models.User;
import com.fabriciocesar.demo.repositories.TaskRepository;
import com.fabriciocesar.demo.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User buscarPeloIdentificador(Long id) {
        Optional<User> user = this.userRepository.buscarPeloIdentificador(id);
        return user.orElseThrow(() -> new RuntimeException(
            "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = buscarPeloIdentificador(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        buscarPeloIdentificador(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
}
