SELECT * FROM assignees, users WHERE assignees.user_id = users.id AND assignees.id = :id;