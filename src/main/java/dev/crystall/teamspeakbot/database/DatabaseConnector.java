package dev.crystall.teamspeakbot.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.crystall.teamspeakbot.Constants;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by CrystallDEV on 27/03/2021
 */
public class DatabaseConnector {

  private final Logger logger = LoggerFactory.getLogger(getClass().getCanonicalName());

  @Getter
  private MongoClient mongoClient;
  @Getter
  private MongoDatabase mongoDatabase;

  @Setter
  private boolean isConnected;


  /**
   * Tries to connect to the MongoDB
   *
   * @return
   */
  public boolean connectToDatabase() {
    try {
      mongoClient = MongoClients.create();
      mongoDatabase = mongoClient.getDatabase(Constants.DATABASE_NAME);
      isConnected = true;
      return true;
    } catch (Exception ex) {
      logger.error(ex.getMessage());
      return false;
    }
  }
}