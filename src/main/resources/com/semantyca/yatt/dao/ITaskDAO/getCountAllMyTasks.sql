SELECT count(t.id) FROM tasks AS t, task_rls AS rls WHERE t.author = :author AND t.id = rls.entity_id AND rls.reader = :reader;