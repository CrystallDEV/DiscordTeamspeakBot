package dev.crystall.teamspeakbot.channel;

import dev.crystall.teamspeakbot.Constants;
import dev.crystall.teamspeakbot.DiscordTeamspeakBot;
import dev.crystall.teamspeakbot.database.registries.ChannelRegistry;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;
import discord4j.rest.util.PermissionSet;
import java.util.Set;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by CrystallDEV on 25/02/2021
 */
public class ChannelManager {

  private final Logger logger = LoggerFactory.getLogger(getClass().getCanonicalName());

  @Getter
  private final ChannelRegistry channelRegistry = new ChannelRegistry();

  public boolean createChannel(Guild guild, String name) {
    if (channelRegistry.get(new UniqueIdentifier(guild.getId().asString(), name)) != null) {
      logger.error("A channel with that name already exists!");
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
    TeamspeakChannel teamspeakChannel = new TeamspeakChannel(name, guild, channelRole);
    channelRegistry.save(teamspeakChannel);
    return true;
  }

  public void deleteChannel(Guild guild, String name) {
    UniqueIdentifier uniqueIdentifier = new UniqueIdentifier(guild.getId().asString(), name);
    TeamspeakChannel teamspeakChannel = channelRegistry.get(uniqueIdentifier);
    if (teamspeakChannel == null) {
      logger.error("Can't delete channel. Unable to find channel {}", name);
      return;
    }

    channelRegistry.delete(teamspeakChannel);
    // TODO delete role and remove from all users connected to the channel -> check if a role can be deleted if users still have it
    // TODO delete voice and text channel
  }

  public boolean revokeChannelRole(Guild guild, String oldChannelName, User user) {
    TeamspeakChannel channel = channelRegistry.get(new UniqueIdentifier(guild.getId().asString(), oldChannelName));

    if (channel == null) {
      return false;
    }

    user.asMember(guild.getId()).block().removeRole(channel.getRole().getId()).block();
    return true;
  }

  public boolean grantChannelRole(Guild guild, String channelName, User user) {
    TeamspeakChannel channel = channelRegistry.get(new UniqueIdentifier(guild.getId().asString(), channelName));

    if (channel == null) {
      return false;
    }

    user.asMember(guild.getId()).block().addRole(channel.getRole().getId()).block();
    return true;
  }
}
