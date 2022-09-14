package com.lamine.dao;

import com.lamine.model.Project;
import com.mongodb.client.MongoCollection;

public class TasksOrganizerDAO {
    public void createProject(Project project) throws Exception {
        MongoCollection<Project> projectsCollection = Connection.getDiscordBotsDB().getCollection("projects", Project.class);

        projectsCollection.insertOne(project);
    }
}
