package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class AddCoins extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        RoleCheck rc = new RoleCheck();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "addcoins")){
            if(rc.isOwner(event) || rc.isDeveloper(event) || rc.isAdministrator(event)){
                if(args.length < 3){
                    eb.setDescription(event.getMember().getAsMention() + " you haven't specified enough arguments \n\n```\n" + data.getPrefix() + "addcoins <@member> <amount of coins to add>\n```");
                    eb.setColor(color.errorRed);
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Insufficient Arguments", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        eb.clear();
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                    });
                } else if (args.length > 2){
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    eb.setDescription("Successfully added " + args[2] + " coins to " + mentioned.getAsMention() + "'balance.");
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Economy Update", data.getSelfAvatar(event));

                    success.setDescription(event.getMember().getAsMention() + " added " + args[2] + " coins to " + mentioned.getAsMention() + "'s balance");
                    success.setColor(color.getRandomColor());
                    success.setTimestamp(Instant.now());
                    success.setFooter("Entity Economy Logs");
                    ecu.addCoins(mentioned.getIdLong(), Integer.parseInt(args[2]));
                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                       eb.clear();
                       data.getLogChannel(event).sendMessage(success.build()).queue();
                       message.delete().queueAfter(15, TimeUnit.SECONDS);
                    });

                }
            } else {
                eb.setDescription(event.getMember().getAsMention() + " you don't have sufficient permissions to edit the balance of members");
                eb.setColor(color.errorRed);
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Insufficient Permissions", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    eb.clear();
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                });
            }
        }
    }

}
