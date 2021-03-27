package dev.crystall.teamspeakbot.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.crystall.teamspeakbot.database.json.TeamspeakChannelDeserializer;
import dev.crystall.teamspeakbot.database.json.TeamspeakChannelSerializer;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Role;
import lombok.Getter;

/**
 * Created by CrystallDEV on 25/02/2021
 */
@Getter
@JsonSerialize(using = TeamspeakChannelSerializer.class)
@JsonDeserialize(using = TeamspeakChannelDeserializer.class)
public class TeamspeakChannel {

  private final String name;
  @JsonIgnore
  private final Guild guild;
  @JsonIgnore
  private final Role role;
  private final UniqueIdentifier uniqueIdentifier;

  public TeamspeakChannel(String name, Guild guild, Role role) {
    this.name = name;
    this.guild = guild;
    this.role = role;
    this.uniqueIdentifier = new UniqueIdentifier(getGuildId(), getName());
  }

  @JsonIgnore
  public String getGuildId() {
    return guild.getId().asString();
  }

}
