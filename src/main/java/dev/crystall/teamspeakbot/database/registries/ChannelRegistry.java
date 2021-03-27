package dev.crystall.teamspeakbot.database.registries;

import com.mongodb.client.model.Filters;
import dev.crystall.teamspeakbot.DiscordTeamspeakBot;
import dev.crystall.teamspeakbot.channel.TeamspeakChannel;
import dev.crystall.teamspeakbot.channel.UniqueIdentifier;
import java.util.ArrayList;
import java.util.List;
import org.mongojack.JacksonMongoCollection;

/**
 * Created by CrystallDEV on 27/03/2021
 */
public class ChannelRegistry extends BaseRegistry<TeamspeakChannel> {

  @Override
  public List<TeamspeakChannel> getAll() {
    List<TeamspeakChannel> teamspeakChannels = new ArrayList<>();
    for (TeamspeakChannel teamspeakChannel : getCollection().find()) {
      teamspeakChannels.add(teamspeakChannel);
    }
    return teamspeakChannels;
  }

  @Override
  public TeamspeakChannel get(String string) {
    return getCollection().findOne(Filters.eq("uniqueIdentifier", string));
  }

  public TeamspeakChannel get(UniqueIdentifier uniqueIdentifier) {
    return get(uniqueIdentifier.toString());
  }

  @Override
  public TeamspeakChannel save(TeamspeakChannel teamspeakChannel) {
    if (get(teamspeakChannel.getUniqueIdentifier().toString()) != null) {
      getCollection().replaceOne(Filters.eq("uniqueIdentifier", teamspeakChannel.getUniqueIdentifier()), teamspeakChannel);
    } else {
      getCollection().save(teamspeakChannel);
    }
    return teamspeakChannel;
  }

  @Override
  public boolean delete(TeamspeakChannel teamspeakChannel) {
    String uniqueIdentifier = teamspeakChannel.getUniqueIdentifier().toString();
    if (get(uniqueIdentifier) != null) {
      return getCollection().deleteOne(Filters.eq("uniqueIdentifier", uniqueIdentifier)).wasAcknowledged();
    }

    return false;
  }

  /**
   * Wrapper function for getting the collection
   */
  @Override
  public JacksonMongoCollection<TeamspeakChannel> getCollection() {
    return JacksonMongoCollection.builder().build(DiscordTeamspeakBot.getDatabaseConnector().getMongoDatabase(), "channels", TeamspeakChannel.class);
  }
}
