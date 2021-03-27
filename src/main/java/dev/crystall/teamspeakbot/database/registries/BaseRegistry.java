package dev.crystall.teamspeakbot.database.registries;

import com.mongodb.client.MongoCollection;
import java.util.List;
import lombok.Getter;

/**
 * A registry is a base component that helps executing CRUD operations on a DatabaseModel
 * <p>
 * * Created by CrystallDEV on 26/03/2020
 */
@Getter
public abstract class BaseRegistry<T> {

  /**
   * Fetches all objects of this collection
   *
   * @return
   */
  public abstract List<T> getAll();

  /**
   * Searches for a object
   *
   * @param string
   * @return
   */
  public abstract T get(String string);

  /**
   * Saves the given json to the database
   *
   * @param object
   * @return returns the saved object
   */
  public abstract T save(T object);

  /**
   * deletes the given object
   *
   * @param object to delete
   * @return returns if the object got successfully deleted
   */
  public abstract boolean delete(T object);

  /**
   * Wrapper function that quickly gets the collection for this registry of the database type
   *
   * @return {MongoCollection<T>} collection
   */
  public abstract MongoCollection<T> getCollection();
}
