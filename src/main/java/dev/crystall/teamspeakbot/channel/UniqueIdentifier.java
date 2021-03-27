package dev.crystall.teamspeakbot.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by CrystallDEV on 27/03/2021
 */

@Getter
@AllArgsConstructor
public class UniqueIdentifier {

  private final String guildId;
  private final String channelName;

  @Override
  public String toString() {
    return getGuildId() + "-" + getChannelName();
  }
}
