package dev.crystall.teamspeakbot.database.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.crystall.teamspeakbot.channel.TeamspeakChannel;
import java.io.IOException;

/**
 * Created by CrystallDEV on 27/03/2021
 */
public class TeamspeakChannelSerializer extends JsonSerializer<TeamspeakChannel> {

  /**
   * Method that can be called to ask implementation to serialize
   * values of type this serializer handles.
   *
   * @param value Value to serialize; can <b>not</b> be null.
   * @param gen Generator used to output resulting Json content
   * @param serializers Provider that can be used to get serializers for
   */
  @Override
  public void serialize(TeamspeakChannel value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartObject();
    gen.writeStringField("name", value.getName());
    gen.writeStringField("guildId", value.getGuildId());
    gen.writeStringField("roleId", value.getRole().getId().asString());
    gen.writeStringField("uniqueIdentifier", value.getUniqueIdentifier().toString());
    gen.writeEndObject();
  }
}
