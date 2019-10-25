package dev.dbdh.Discord.Listeners.Party;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

/**
 * Class to create party voice and text channels for party members to use
 */
public class ChannelCreate extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        EmbedBuilder eb = new EmbedBuilder();
        String[] commandAliases = {data.getPrefix() + "channelcreate", data.getPrefix() + "cc", data.getPrefix() + "channel"};
        if (Arrays.stream(commandAliases).anyMatch(args[0]::equalsIgnoreCase)) {
        }
    }
}

