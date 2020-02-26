package dev.dbdh.Discord.Listeners.Moderation;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class TODO extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder TODOEmbed = new EmbedBuilder();
        MessageEmbed prvEmbed;
        Message test;
        String addedPhrase = "";
        int characterLimit = 50;
        int charCount = 0;
        int indexLimit = 38;
        int curIndexs = 0;
        if(args[0].equalsIgnoreCase("!todo")){
            event.getMessage().delete().queue();

            if(args.length < 2) {
                /*
                TODOEmbed.setTitle( event.getGuild().getName() + " TO-DO:");
                TODOEmbed.setFooter("Last editor: " + event.getAuthor().getName() + "\nTotal index's:"  + curIndexs + " character limit: " + 50);
                TODOEmbed.setColor(Color.purpleHaze);
                TODOEmbed.setTimestamp(Instant.now());
                */

                TODOEmbed.setDescription("You need to specify want you want to do. Type "+ Data.getPrefix() + "TODO <add/remove> [if remove then specify index] <TEXT>. \n the <TEXT> must not exceed the maximum character count: " + characterLimit + " exceeding this limit will simply remove text out of bounds.\nThe index limit is: " + indexLimit + " to prevent errors.");
                TODOEmbed.setColor(0xff5555);
                TODOEmbed.setTimestamp(Instant.now());
                TODOEmbed.setFooter("TODO ~ help", Data.getSelfAvatar(event));
                event.getChannel().sendMessage(TODOEmbed.build()).queue((message) -> {
                    TODOEmbed.clear();
                    message.delete().queueAfter(60, TimeUnit.SECONDS);
                });
                event.getChannel().sendMessage(TODOEmbed.build()).queue();
            }
            else if (args.length > 2){
                for(char c : event.getMessage().toString().toCharArray()) {
                    addedPhrase += c;
                    charCount++;
                }
                charCount -= args[0].length() + 1 + args[1].length();
                prvEmbed = event.getGuild().getTextChannelById("680583891639337021").getHistory().getMessageById("680962846733107279").getEmbeds().get(0);
                if (args[1].equalsIgnoreCase("add")) {
                } else if (args[1].equalsIgnoreCase("remove")) {

                }
            }
            //Auto delete function will ALWAYS happen ensuring that the channel will not be flooded

        }
    }
}
