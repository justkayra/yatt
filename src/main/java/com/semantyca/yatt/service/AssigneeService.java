package com.semantyca.yatt.service;

import com.semantyca.yatt.EnvConst;
import com.semantyca.yatt.dao.IAssigneeDAO;
import com.semantyca.yatt.dto.view.ViewPage;
import com.semantyca.yatt.model.Assignee;
import com.semantyca.yatt.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssigneeService {

    @Lazy
    @Autowired
    IAssigneeDAO assigneeDAO;

    public long getCountOfAll() {
        return assigneeDAO.getCountAll();
    }

    public ViewPage findAll(String pageSizeAsString, String pageNumAsString, String pattern) {
        long count = getCountOfAll();
        int size = NumberUtil.stringToInt(pageSizeAsString, EnvConst.DEFAULT_PAGE_SIZE);
        int num = NumberUtil.stringToInt(pageNumAsString, 0);
        int startEntry = NumberUtil.calcStartEntry(num, size);
        List<?> result = null;
        if (pattern.equals("OPTION")) {
            result = assigneeDAO.findAllUnrestricted(size, startEntry).stream()
                    .map(e -> {
                        Map map = new HashMap();
                        map.put("value", e.getId());
                        map.put("label", e.getName());
                        return map;
                    }).collect(Collectors.toList());
        } else {
            result = assigneeDAO.findAllUnrestricted(size, startEntry);
        }
        return new ViewPage(result, count, NumberUtil.countMaxPage(count, size), num, size);
    }

    public Assignee findById(UUID id) {
         return assigneeDAO.findById(id);
    }

}
