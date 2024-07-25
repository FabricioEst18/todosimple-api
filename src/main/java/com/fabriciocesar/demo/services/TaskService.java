package com.fabriciocesar.demo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabriciocesar.demo.models.Task;
import com.fabriciocesar.demo.models.User;
import com.fabriciocesar.demo.repositories.TaskRepository;

@Service
public class TaskService { 
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task buscarPeloIdentificador(Long id) {
        Optional<Task> task = this.taskRepository.buscarPeloIdentificador(id);
        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa não encontrado! Id: " + id + ", Tipo: " + Task.class.getName()));
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.buscarPeloIdentificador(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = buscarPeloIdentificador(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        buscarPeloIdentificador(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
}    
