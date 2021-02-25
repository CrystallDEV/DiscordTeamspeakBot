package dev.crystall.teamspeakbot.command.commands;

import dev.crystall.teamspeakbot.DiscordTeamspeakBot;
import dev.crystall.teamspeakbot.command.BaseCommand;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import java.util.List;

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
    if (DiscordTeamspeakBot.getChannelManager().createChannel(guild, name)) {
      event.getMessage().getChannel().block().createMessage("Successfully created voice and text channel!").block();
      return;
    }
    event.getMessage().getChannel().block().createMessage("Channel already exists!").block();
  }
}
