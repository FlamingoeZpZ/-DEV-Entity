package dev.dbdh.Discord.Listeners.Fun;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class Chest extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "chest")) {
            if (event.getMessage().getChannel().equals(event.getGuild().getTextChannelById("632350945891581992"))) {
                if (args.length < 2) {
                    eb.setDescription("You need to specify what type of chest you would like to open. `Basic | Shiny | Legendary | Mythic`");
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queue();
                    });
                }
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("basic")) {
                    if (ecu.getChests(event, event.getMember().getUser().getId(), args[1]) < 1) {
                        if (ecu.isCooldownReady(event, event.getMember().getUser().getId())) {
                            eb.setDescription("You didn't have a basic chest available but your cooldown has ran out so here is a free chest");
                            eb.setColor(color.getRandomColor());
                            eb.setTimestamp(Instant.now());
                            eb.setFooter("Entity Free Chest | Free Basic Chest every 5 minutes | Max of 1 free chest", data.getSelfAvatar(event));

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                eb.clear();
                            });
                        }
                    }
                }
            }
        }
    }
}