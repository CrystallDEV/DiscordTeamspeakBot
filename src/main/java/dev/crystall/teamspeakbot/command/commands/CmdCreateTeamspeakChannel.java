package dev.crystall.teamspeakbot.command.commands;

import dev.crystall.teamspeakbot.Constants;
import dev.crystall.teamspeakbot.DiscordTeamspeakBot;
import dev.crystall.teamspeakbot.command.BaseCommand;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.PermissionOverwrite;
import discord4j.core.object.entity.Guild;
import discord4j.rest.util.PermissionSet;
import java.util.List;
import java.util.Set;

/**
 * Created by CrystallDEV on 24/02/2021
 */
public class CmdCreateTeamspeakChannel extends BaseCommand {

  public CmdCreateTeamspeakChannel() {
    super("Create Channel", "createChannel");
  }

  @Override
  public void execute(MessageCreateEvent event, List<String> args) {
    super.execute(event, args);
    String name = args.get(0);
    Guild guild = event.getGuild().block();
    if (guild == null) {
      System.out.println("Unable to find guild for " + getClass().getCanonicalName() + " command");
      return;
    }

    if (guild.getChannels().toStream().anyMatch(guildChannel -> guildChannel.getName().equalsIgnoreCase(name))) {
      event.getMessage().getChannel().block().createMessage("Channel already exists!").block();
      return;
    }

    var channelRole = guild.createRole(roleCreateSpec -> roleCreateSpec.setName(name)).block();

    Set<PermissionOverwrite> permissionOverwrite = Set.of(
      PermissionOverwrite.forRole(guild.getEveryoneRole().block().getId(), PermissionSet.none(), Constants.DEFAULT_CHANNEL_PERMISSIONS),
      PermissionOverwrite.forRole(channelRole.getId(), Constants.DEFAULT_CHANNEL_PERMISSIONS, PermissionSet.none()),
      PermissionOverwrite.forMember(DiscordTeamspeakBot.getSelf().getId(), Constants.DEFAULT_CHANNEL_PERMISSIONS, PermissionSet.none())
    );

    guild.createTextChannel(textChannelCreateSpec -> {
      textChannelCreateSpec.setName(name);
      textChannelCreateSpec.setPermissionOverwrites(permissionOverwrite);
    }).block();

    guild.createVoiceChannel(voiceChannelCreateSpec -> voiceChannelCreateSpec.setName(name)).block();
    event.getMessage().getChannel().block().createMessage("Successfully created voice and text channel!").block();
  }
}
