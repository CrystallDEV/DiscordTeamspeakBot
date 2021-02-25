package dev.crystall.teamspeakbot.command.commands;

import dev.crystall.teamspeakbot.command.BaseCommand;
import discord4j.core.event.domain.message.MessageCreateEvent;
import java.util.List;

/**
 * Created by CrystallDEV on 24/02/2021
 */
public class CmdPingPong extends BaseCommand {

  public CmdPingPong() {
    super("Ping Pong", "ping");
  }

  @Override
  public void execute(MessageCreateEvent event, List<String> args) {
    event.getMessage().getChannel().block().createMessage("Pong!").block();
  }
}
