package com.semantyca.yatt.controller;

import com.semantyca.yatt.dto.constant.PayloadType;
import com.semantyca.yatt.dto.document.DocumentOutcome;
import com.semantyca.yatt.dto.view.ViewPageOutcome;
import com.semantyca.yatt.model.Task;
import com.semantyca.yatt.model.exception.RLSIsNotNormalized;
import com.semantyca.yatt.security.SessionUser;
import com.semantyca.yatt.service.TaskService;
import com.semantyca.yatt.service.exception.DocumentAccessException;
import com.semantyca.yatt.service.exception.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@Validated
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping("tasks")
    public ResponseEntity getAll(Authentication authentication, String pageNum, String pageSize) {
        SessionUser sessionUser = (SessionUser) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ViewPageOutcome().setPayload(service.findAll(pageSize, pageNum, sessionUser.getUserId())).setPageName("All tasks"));
    }

    @GetMapping("my_tasks")
    public ResponseEntity getAllMyTasks(String pageNum, String pageSize) {
        SessionUser sessionUser = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ViewPageOutcome().setPayload(service.findAllMyTasks(pageSize, pageNum, sessionUser.getUserId(), sessionUser.getUserId())).setPageName("My tasks"));
    }

    @RequestMapping("tasks/{id}")
    public ResponseEntity get(@PathVariable(value = "id") String id) throws DocumentNotFoundException, RLSIsNotNormalized {
        SessionUser sessionUser = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task updatedTask;
        if (id.equalsIgnoreCase("new")) {
            updatedTask = service.getNewTask(sessionUser.getUserId());
        } else {
            updatedTask = service.findById(id, sessionUser.getUserId());
        }
        if (updatedTask != null) {
            return ResponseEntity.status(HttpStatus.OK).body(getDocumentOutcome(updatedTask, sessionUser.getUserId()));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


    @PostMapping(path = "/tasks", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DocumentOutcome> post(@Valid @RequestBody Task task) throws DocumentNotFoundException, DocumentAccessException, RLSIsNotNormalized {
        System.out.println(task);
        SessionUser sessionUser = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = sessionUser.getUserId();
        Task updatedTask = service.save(task, userId);
        return ResponseEntity.status(HttpStatus.OK).body(getDocumentOutcome(updatedTask, userId));
    }

    @PutMapping(path = "/tasks", consumes = "application/json", produces = "application/json")
    public ResponseEntity put(@Valid @RequestBody Task task) throws DocumentNotFoundException, DocumentAccessException, RLSIsNotNormalized {
        SessionUser sessionUser = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return putData(task, sessionUser);
    }


    @DeleteMapping("/tasks")
    public ResponseEntity delete(@RequestBody List<String> ids) throws RLSIsNotNormalized {
        SessionUser sessionUser = (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(ids, sessionUser.getUserId()));

    }

    private ResponseEntity putData(Task task, SessionUser sessionUser) throws DocumentNotFoundException, DocumentAccessException, RLSIsNotNormalized {
        int userId = sessionUser.getUserId();
        Task updatedTask = service.update(task, userId);
        updatedTask.setTitle("updated " + new Date().getTime() + " " + updatedTask.getTitle());
        return ResponseEntity.status(HttpStatus.OK).body(getDocumentOutcome(updatedTask, userId));
    }


    private DocumentOutcome getDocumentOutcome(Task task, int userId) throws RLSIsNotNormalized {
        DocumentOutcome outcome = new DocumentOutcome();
        outcome.setIdentifier(String.valueOf(task.getId()));
        outcome.setPageName(task.getTitle());
        outcome.addPayload(task);
        outcome.addPayload(PayloadType.ACTIONS, service.getActions(task, userId));
        return outcome;
    }

}
