package dev.crystall.teamspeakbot;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by CrystallDEV on 25/02/2021
 */
@Getter
@AllArgsConstructor
public class TeamspeakChannel {

  private final String name;
  private final Guild guild;
  private final Role role;

}
