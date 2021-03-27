package dev.crystall.teamspeakbot;

import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;

/**
 * Created by CrystallDEV on 24/02/2021
 */
public class Constants {

  public static final String DEFAULT_COMMAND_PREFIX = "!";
  public static final String DATABASE_NAME = "discord_teamspeak";

  public static final PermissionSet DEFAULT_CHANNEL_PERMISSIONS = PermissionSet.of(
    Permission.SEND_MESSAGES,
    Permission.READ_MESSAGE_HISTORY,
    Permission.VIEW_CHANNEL
  );

  public static final PermissionSet DEFAULT_CHANNEL_CONNECTED_PERMISSIONS = PermissionSet.of(
    Permission.SEND_MESSAGES,
    Permission.VIEW_CHANNEL
  );

  private Constants() {
  }

}
