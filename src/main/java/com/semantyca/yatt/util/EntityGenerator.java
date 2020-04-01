package com.semantyca.yatt.util;

import com.semantyca.yatt.dao.IAssigneeDAO;
import com.semantyca.yatt.dao.IUserDAO;
import com.semantyca.yatt.model.AnonymousUser;
import com.semantyca.yatt.model.Assignee;
import com.semantyca.yatt.model.Task;
import com.semantyca.yatt.model.User;
import com.semantyca.yatt.model.constant.StageType;
import com.semantyca.yatt.model.constant.StatusType;
import com.semantyca.yatt.model.constant.TaskType;
import com.semantyca.yatt.model.embedded.Reader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntityGenerator {
    private static final String FIRST_NAME_SOURCE = "Roman.txt";
    private static final String LAST_NAME_SOURCE = "Simple.txt";
    private IUserDAO userDAO;
    private IAssigneeDAO assigneeDAO;

    public EntityGenerator(IUserDAO userDAO, IAssigneeDAO assigneeDAO) {
        this.userDAO = userDAO;
        this.assigneeDAO = assigneeDAO;
    }

    public List<User> generateUsers() {
        List users = new ArrayList();
        String[] data = {"test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9"};
        for (int i = 0; i < data.length; i++) {
            User entity = userDAO.findByLogin(data[i]);
            if (entity == null) {
                entity = new User();
                ZonedDateTime currentMoment = ZonedDateTime.now();
                entity.setRegDate(currentMoment);
                entity.setLogin(data[i]);
                entity.setLastModifiedDate(currentMoment);
                entity.setAuthor(AnonymousUser.ID);
                entity.setLastModifier(AnonymousUser.ID);
                entity.setTitle(entity.getLogin());
                users.add(entity);
            }
        }
        return users;
    }

    public List<Assignee> generateAssignees() throws IOException {
        NameGenerator nameGeneratorLastName = new NameGenerator(EntityGenerator.class.getClassLoader().getResource(LAST_NAME_SOURCE).getFile());
        NameGenerator nameGeneratorFirstName = new NameGenerator(EntityGenerator.class.getClassLoader().getResource(FIRST_NAME_SOURCE).getFile());
        List entities = new ArrayList();
        List<User> users = userDAO.findAll(100, 0);
        for (User user : users) {
            Assignee entity = assigneeDAO.findByLogin(user.getLogin());
            if (entity == null) {
                entity = new Assignee();
                ZonedDateTime currentMoment = ZonedDateTime.now();
                entity.setRegDate(currentMoment);
                entity.setLastModifiedDate(currentMoment);
                entity.setAuthor(AnonymousUser.ID);
                entity.setLastModifier(AnonymousUser.ID);
                entity.setRank(NumberUtil.getRandomNumber(1, 10));
                entity.setName(nameGeneratorLastName.compose(3) + " " + nameGeneratorFirstName.compose(3));
                entity.setTitle(entity.getName());
                entity.setUser(user);
                entities.add(entity);
            }
        }
        return entities;
    }

    public List<Task> generateTasks(int count) throws IOException {
        List entities = new ArrayList();
        List<User> users = userDAO.findAll(100, 0);
        Integer[] userIds =  users.stream().map((user) -> user.getId()).toArray(Integer[]::new);
        List<Assignee> assignees = assigneeDAO.findAll(100, 0);
        for (int i = 0; i < count; i++) {
            Task entity = new Task();
            ZonedDateTime currentMoment = ZonedDateTime.now();
            entity.setRegDate(currentMoment);
            entity.setLastModifiedDate(currentMoment);
            entity.setAuthor(AnonymousUser.ID);
            entity.setLastModifier(AnonymousUser.ID);
            entity.setStatus(EnumUtil.getRndElement(StatusType.values()));
            entity.setStage(EnumUtil.getRndElement(StageType.values()));
            entity.setType(EnumUtil.getRndElement(TaskType.values()));
            entity.setAssignee((Assignee) ListUtil.getRndListElement(assignees));
            entity.setTitle(StringUtil.getRndArticle(10));
            entity.setDescription(StringUtil.getRndParagraph(1));
            entity.setDeadline(TimeUtil.getRndDateBetween(LocalDateTime.now(), LocalDateTime.now().plusDays(30)));
            entity.addReader(new Reader().setReader(AnonymousUser.ID));
            entity.addReader(new Reader().setReader(ListUtil.getRndArrayElement(userIds)));
            entities.add(entity);

        }
        return entities;
    }


}