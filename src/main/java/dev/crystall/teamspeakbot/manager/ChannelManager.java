package dev.crystall.teamspeakbot.manager;

import dev.crystall.teamspeakbot.Constants;
import dev.crystall.teamspeakbot.DiscordTeamspeakBot;
import dev.crystall.teamspeakbot.TeamspeakChannel;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;
import discord4j.rest.util.PermissionSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by CrystallDEV on 25/02/2021
 */
public class ChannelManager {

  private final List<TeamspeakChannel> channels = new ArrayList<>();

  public boolean createChannel(Guild guild, String name) {
    if (guild.getChannels().toStream().anyMatch(guildChannel -> guildChannel.getName().equalsIgnoreCase(name))) {
      return false;
    }

    var channelRole = guild.createRole(roleCreateSpec -> roleCreateSpec.setName(name)).block();

    Set<PermissionOverwrite> permissionOverwrite = Set.of(
      PermissionOverwrite.forRole(guild.getEveryoneRole().block().getId(), PermissionSet.none(), Constants.DEFAULT_CHANNEL_PERMISSIONS),
      PermissionOverwrite.forRole(channelRole.getId(), Constants.DEFAULT_CHANNEL_CONNECTED_PERMISSIONS, PermissionSet.none()),
      PermissionOverwrite.forMember(DiscordTeamspeakBot.getSelf().getId(), Constants.DEFAULT_CHANNEL_PERMISSIONS, PermissionSet.none())
    );

    guild.createTextChannel(textChannelCreateSpec -> {
      textChannelCreateSpec.setName(name);
      textChannelCreateSpec.setPermissionOverwrites(permissionOverwrite);
    }).block();

    guild.createVoiceChannel(voiceChannelCreateSpec -> voiceChannelCreateSpec.setName(name)).block();
    channels.add(new TeamspeakChannel(name, guild, channelRole));
    return true;
  }

  public void deleteChannel(Guild guild, String name) {
    // TODO remove channel from channels list / delete from file
    // TODO delete role and remove from all users connected to the channel -> check if a role can be deleted if users still have it
    // TODO delete voice and text channel
  }

  public boolean revokeChannelRole(Guild guild, String oldChannelName, User user) {
    Optional<TeamspeakChannel> channel = channels.stream()
      .filter(teamspeakChannel -> teamspeakChannel.getGuild().equals(guild) && teamspeakChannel.getName().equalsIgnoreCase(oldChannelName)).findFirst();

    if (channel.isEmpty()) {
      // TODO log error
      return false;
    }

    user.asMember(guild.getId()).block().removeRole(channel.get().getRole().getId()).block();
    return true;
  }

  public boolean grantChannelRole(Guild guild, String channelName, User user) {
    Optional<TeamspeakChannel> channel = channels.stream()
      .filter(teamspeakChannel -> teamspeakChannel.getGuild().equals(guild) && teamspeakChannel.getName().equalsIgnoreCase(channelName)).findFirst();

    if (channel.isEmpty()) {
      // TODO log error
      return false;
    }

    user.asMember(guild.getId()).block().addRole(channel.get().getRole().getId()).block();
    return true;
  }
}
