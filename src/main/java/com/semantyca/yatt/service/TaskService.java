package com.semantyca.yatt.service;

import com.semantyca.yatt.dao.ITaskDAO;
import com.semantyca.yatt.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    ITaskDAO taskDAO;


    public long getCountOfAll(long reader) {
        return taskDAO.getCountAll();
    }

    public List<Task> findAll(int pageSize, int calcStartEntry, int i) {
        return taskDAO.findAll(pageSize, calcStartEntry);
    }

    public long post(Task task) {
        return taskDAO.insert(task);
    }
}