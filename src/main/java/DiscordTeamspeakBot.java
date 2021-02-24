import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import manager.EventManager;

/**
 * Created by CrystallDEV on 24/02/2021
 */
public class DiscordTeamspeakBot {

  private static final String token = "";

  private EventManager eventManager;

  public DiscordTeamspeakBot() {
    GatewayDiscordClient client = DiscordClientBuilder.create(token).build().login().block();

    if (client == null) {
      throw new RuntimeException("Unable to connect to server");
    }

    client.getEventDispatcher().on(ReadyEvent.class).subscribe(event -> {
      User self = event.getSelf();
      System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
    });

    client.onDisconnect().block();

    eventManager = new EventManager();
  }

  public static void main(String[] args) {
    new DiscordTeamspeakBot();
  }


}
