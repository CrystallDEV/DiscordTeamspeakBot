package dev.crystall.teamspeakbot.database.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import dev.crystall.teamspeakbot.DiscordTeamspeakBot;
import dev.crystall.teamspeakbot.channel.TeamspeakChannel;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by CrystallDEV on 27/03/2021
 */
public class TeamspeakChannelDeserializer extends JsonDeserializer<TeamspeakChannel> {

  private final Logger logger = LoggerFactory.getLogger(getClass().getCanonicalName());

  @Override
  public TeamspeakChannel deserialize(JsonParser p, DeserializationContext ctxt) {
    try {
      ObjectCodec oc = p.getCodec();
      JsonNode node = oc.readTree(p);

      String name = node.get("name").asText();
      Snowflake guildId = Snowflake.of(node.get("guildId").asText());
      Snowflake roleId = Snowflake.of(node.get("roleId").asText());

      Guild guild = DiscordTeamspeakBot.getClient().getGuildById(guildId).block();
      Role role = DiscordTeamspeakBot.getClient().getRoleById(guildId, roleId).block();

      return new TeamspeakChannel(name, guild, role);
    } catch (Exception ex) {
      logger.error("Unable to parse teamspeak channel - " + ex.getMessage());
      return null;
    }
  }
}