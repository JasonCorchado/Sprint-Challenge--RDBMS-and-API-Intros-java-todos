package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.repository.TodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "todosService")
public class TodosServiceImpl implements TodosService{

    @Autowired
    private TodosRepository todosRepos;

    @Autowired
    UserService userService;


    @Override
    public void markComplete(long todoid) {
        Todos markTodo = todosRepos.findById(todoid)
                .orElseThrow(() -> new EntityNotFoundException("Todo " + todoid + " Not Found!"));
        markTodo.setCompleted(true);
        todosRepos.save(markTodo);
    }

    @Override
    public Todos save(long userid, Todos newTodo) {
        User currentUser = userService.findUserById(userid);
        Todos saveTodo = new Todos(newTodo.getDescription(), currentUser);
        todosRepos.save(saveTodo);
        return saveTodo;
    }
}
