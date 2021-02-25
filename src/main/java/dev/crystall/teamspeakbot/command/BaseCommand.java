package dev.crystall.teamspeakbot.command;

import dev.crystall.teamspeakbot.Constants;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import java.util.List;
import lombok.Getter;

/**
 * Created by CrystallDEV on 24/02/2021
 */
@Getter
public class BaseCommand {

  private final String name;
  private final String alias;
  private final String prefix;

  public BaseCommand(String name, String alias, String prefix, String... args) {
    this.name = name;
    this.alias = alias;
    this.prefix = prefix;
  }

  public BaseCommand(String name, String alias) {
    this(name, alias, Constants.DEFAULT_COMMAND_PREFIX);
  }

  public void execute(MessageCreateEvent event, List<String> args) {

  }

  /**
   * Checks if a message starts with the needed String to execute this command
   *
   * @param message
   * @return
   */
  public boolean doesMatch(Message message) {
    return message.getContent().toLowerCase().startsWith(prefix + alias.toLowerCase());
  }

  /**
   * Returns the full command containing the prefix and the alias
   *
   * @return fullCommand
   */
  public String getFullCommand() {
    return prefix + alias;
  }

}
