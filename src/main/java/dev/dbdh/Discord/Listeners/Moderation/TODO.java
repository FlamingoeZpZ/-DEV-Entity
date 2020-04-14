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
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {//LMAO U GAY
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase("!todo")){
            EmbedBuilder TODOEmbed = new EmbedBuilder();
            MessageEmbed prvEmbed;
            Message test;
            String addedPhrase = "";
            int characterLimit = 50;
            int charCount = 0;
            int indexLimit = 38;
            int curIndexs = 0;
            event.getMessage().delete().queue();
            if (args.length > 2){
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
