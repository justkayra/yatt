package com.semantyca.yatt.dao;

import com.semantyca.yatt.model.Assignee;
import com.semantyca.yatt.model.Task;
import com.semantyca.yatt.model.User;
import org.jdbi.v3.sqlobject.config.RegisterColumnMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@UseClasspathSqlLocator
public interface IAssigneeDAO extends IDAO<Assignee> {

    @SqlQuery
    @RegisterColumnMapper(TaskMapper.class)
    Task findById(@Bind("id") int id);

    @SqlQuery
    @RegisterColumnMapper(AssigneeMapper.class)
    List<User> findAll(@Bind("limit") int limit, @Bind("offset") int offset);

    @SqlQuery
    long getCountOfAll();

    @SqlUpdate
    @GetGeneratedKeys("id")
    int insert(@BindBean Assignee assignee);

    @SqlQuery
    @RegisterColumnMapper(AssigneeMapper.class)
    Assignee findByLogin(String datum);
}



