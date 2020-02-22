package dev.dbdh.Discord.Listeners.Moderation;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class TODO extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder TODOEmbed = new EmbedBuilder();
        EmbedBuilder prvEmbed = new EmbedBuilder();
        int characterLimit = 50;
        int indexLimit = 38;
        int curIndexs = 0;
        if(args[0].equalsIgnoreCase("!todo")){
            TODOEmbed.setTitle(event.getGuild().getName() + " TODO list: ");
            TODOEmbed.setColor(Color.purpleHaze);
            TODOEmbed.setFooter("TODO list:\\n Last editor: " + event.getMember().getEffectiveName() + "\nLast edit date: "+ Instant.now() +"\nTotal indexes: " + curIndexs, Data.getSelfAvatar(event));
            if(args.length == 2) {
                TODOEmbed.setDescription("You need to specify want you want to do. Type "+ Data.getPrefix() + "TODO <add/remove> [if remove then specify index] <TEXT>. \n the <TEXT> must not exceed the maximum character count: " + characterLimit + " exceeding this limit will simply remove text out of bounds.\nThe index limit is: " + indexLimit + " to prevent errors.");
                TODOEmbed.setColor(0xff5555);
                TODOEmbed.setTimestamp(Instant.now());
                TODOEmbed.setFooter("TODO ~ help", Data.getSelfAvatar(event));


            }
            else {
                if (args[1].equalsIgnoreCase("add")) {
                    event.getGuild().getTextChannelById("680583891639337021").retrieveMessageById("");

                } else if (args[1].equalsIgnoreCase("remove")) {

                }
            }
            //Auto delete function will ALWAYS happen ensuring that the channel will not be flooded
            event.getChannel().sendMessage(TODOEmbed.build()).queue((message) -> {
                TODOEmbed.clear();
                message.delete().queueAfter(30, TimeUnit.SECONDS);
            });
        }
    }
}
