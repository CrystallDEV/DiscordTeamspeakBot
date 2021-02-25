package dev.crystall.teamspeakbot.manager;

import dev.crystall.teamspeakbot.Constants;
import dev.crystall.teamspeakbot.command.BaseCommand;
import dev.crystall.teamspeakbot.command.CommandType;
import dev.crystall.teamspeakbot.command.commands.CmdCreateTeamspeakChannel;
import dev.crystall.teamspeakbot.command.commands.CmdPingPong;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by CrystallDEV on 24/02/2021
 */
public class CommandManager {

  private final Map<CommandType, BaseCommand> commands = new EnumMap<>(CommandType.class);

  public CommandManager() {
    commands.put(CommandType.PING_PONG, new CmdPingPong());
    commands.put(CommandType.CREATE_CHANNEL, new CmdCreateTeamspeakChannel());
  }

  public void fireCommand(MessageCreateEvent event) {
    Optional<User> author = event.getMessage().getAuthor();
    if (author.isEmpty() || author.get().isBot()) {
      return;
    }

    for (BaseCommand command : commands.values()) {
      if (command.doesMatch(event.getMessage())) {
        command.execute(event, truncateArgs(event.getMessage().getContent()));
        return;
      }
    }

    // We do this check after we went through all commands since some commands could have a custom prefix
    if (!event.getMessage().getContent().startsWith(Constants.DEFAULT_COMMAND_PREFIX)) {
      return;
    }

    final MessageChannel channel = event.getMessage().getChannel().block();
    channel.createMessage("Unknown chat command!").block();
  }

  private List<String> truncateArgs(String content) {
    var args = new ArrayList<>(Arrays.asList(content.split(" ")));
    if (args.size() > 0) {
      args.remove(0);
    }
    return args;
  }

}
