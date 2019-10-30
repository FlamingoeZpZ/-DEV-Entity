package dev.dbdh.Discord.Listeners.Fun;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class Chest extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        EmbedBuilder eb = new EmbedBuilder();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "chest")){
            if(args.length < 2){
                eb.setDescription("You need to specify what type of chest you would like to open. `Basic | Shiny | Legendary | Mythic`");
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queue();
                });
            }
        }
    }

}
