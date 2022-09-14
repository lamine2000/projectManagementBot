package com.lamine.dao;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Connection {
    private static final ConnectionString connectionString = new ConnectionString("mongodb://discordbot:pass@localhost:27017/?retryWrites=true&w=majority");

    public static MongoDatabase getDiscordBotsDB() throws Exception {
        MongoDatabase discordBotsDB;

        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);
        discordBotsDB = mongoClient.getDatabase("projectManagerBot");
        return discordBotsDB;
    }

    public static MongoCollection<Document> getCollectionFromDB(String collectionName) throws Exception {
        MongoDatabase db = getDiscordBotsDB();
        return db.getCollection(collectionName);
    }
}
