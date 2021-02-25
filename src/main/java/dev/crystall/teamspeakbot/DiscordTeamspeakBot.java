package dev.crystall.teamspeakbot;

import dev.crystall.teamspeakbot.manager.ChannelManager;
import dev.crystall.teamspeakbot.manager.CommandManager;
import dev.crystall.teamspeakbot.manager.EventManager;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import lombok.Getter;

/**
 * Created by CrystallDEV on 24/02/2021
 */
public class DiscordTeamspeakBot {

  private static String token;

  @Getter
  private static User self;

  @Getter
  private static EventManager eventManager;

  @Getter
  private static CommandManager commandManager;

  @Getter
  private static ChannelManager channelManager;

  public DiscordTeamspeakBot() {
    GatewayDiscordClient client = DiscordClientBuilder.create(token).build().login().block();

    if (client == null) {
      throw new RuntimeException("Unable to connect to server");
    }

    client.getEventDispatcher().on(ReadyEvent.class).subscribe(event -> {
      self = event.getSelf();
      setupManager(client);
      System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
    });

    client.onDisconnect().block();
  }

  private void setupManager(GatewayDiscordClient client) {
    eventManager = new EventManager(client);
    commandManager = new CommandManager();
    channelManager = new ChannelManager();
  }

  public static void main(String[] args) {
    token = args[0];
    new DiscordTeamspeakBot();
  }


}
