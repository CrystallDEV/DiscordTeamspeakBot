package dev.crystall.teamspeakbot.events;

import dev.crystall.teamspeakbot.DiscordTeamspeakBot;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.VoiceStateUpdateEvent;
import discord4j.core.event.domain.channel.TextChannelCreateEvent;
import discord4j.core.event.domain.channel.TextChannelDeleteEvent;
import discord4j.core.event.domain.channel.VoiceChannelCreateEvent;
import discord4j.core.event.domain.channel.VoiceChannelDeleteEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import java.util.Optional;

/**
 * Created by CrystallDEV on 24/02/2021
 */
public class EventManager {

  public EventManager(GatewayDiscordClient discordClient) {
    discordClient.on(MessageCreateEvent.class).subscribe(event -> DiscordTeamspeakBot.getCommandManager().fireCommand(event));
    discordClient.on(VoiceStateUpdateEvent.class).subscribe(this::onVoiceStateUpdate);

    discordClient.on(TextChannelCreateEvent.class).subscribe(this::onChannelCreate);
    discordClient.on(TextChannelDeleteEvent.class).subscribe(this::onChannelDelete);
    discordClient.on(VoiceChannelCreateEvent.class).subscribe(this::onChannelCreate);
    discordClient.on(VoiceChannelDeleteEvent.class).subscribe(this::onChannelDelete);
  }


  private void onChannelCreate(TextChannelCreateEvent textChannelCreateEvent) {

  }

  private void onChannelDelete(TextChannelDeleteEvent textChannelDeleteEvent) {

  }

  private void onChannelCreate(VoiceChannelCreateEvent voiceChannelCreateEvent) {

  }

  private void onChannelDelete(VoiceChannelDeleteEvent voiceChannelDeleteEvent) {

  }


  public void onVoiceStateUpdate(VoiceStateUpdateEvent event) {
    Optional<VoiceState> oldVoiceState = event.getOld();
    if (oldVoiceState.isPresent()) {
      String oldChannelName = oldVoiceState.get().getChannel().block().getName();
      DiscordTeamspeakBot.getChannelManager().revokeChannelRole(oldVoiceState.get().getGuild().block(), oldChannelName, oldVoiceState.get().getUser().block());
    }

    VoiceState voiceState = event.getCurrent();
    if (voiceState.getChannel().block() == null) {
      return;
    }

    // Checks if the channel is the same to prevent double assigning a role
    if (oldVoiceState.isPresent() && oldVoiceState.get().getChannelId().get().equals(voiceState.getChannelId().get())) {
      return;
    }

    String channelName = voiceState.getChannel().block().getName();
    DiscordTeamspeakBot.getChannelManager().grantChannelRole(voiceState.getGuild().block(), channelName, voiceState.getUser().block());
  }

}
