package dev.dbdh.Discord.Listeners.Fun;

import com.michaelwflaherty.cleverbotapi.CleverBotQuery;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Cleverbot extends ListenerAdapter {

    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();
        if (args[0].equals(event.getGuild().getSelfMember().getAsMention())) {
            CleverBotQuery bot = new CleverBotQuery(System.getenv("CLEVERBOTAPIKEY"), "hi");
            if (args.length < 2) {
                String seedText = "Hi, how are you?";
                String response;
                try{
                    bot.setPhrase(seedText);
                    bot.sendRequest();
                    response = bot.getResponse();
                    event.getChannel().sendMessage(response).queue();
                } catch (IOException e){
                    e.printStackTrace();

                    eb.setDescription("An error has occured with the chatbot API \n\n```\n" + e.toString() + "\n```");
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Cleverbot API Error", data.getSelfAvatar(event));

                    data.getLogChannel(event).sendMessage(eb.build()).queue();
                }
            }
            if (args.length > 1) {
                String seedText = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
                String response;
                try {
                    bot.setPhrase(seedText);
                    bot.sendRequest();
                    response = bot.getResponse();
                    event.getChannel().sendMessage(response).queue();
                } catch (IOException e) {
                    e.printStackTrace();

                    eb.setDescription("An error has occured with the chatbot API \n\n```\n" + e.toString() + "\n```");
                    eb.setColor(color.errorRed);
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Cleverbot API Error", data.getSelfAvatar(event));

                    data.getLogChannel(event).sendMessage(eb.build()).queue();
                }
            }
        }
    }

    public String getName() {
        return "Entity CleverBot";
    }

    public String getDescription() {
        return "Talk to a chatbot from the clever bot API";
    }

    public String getShortDescription() {
        return "Talk to a chatbot";
    }

    public String getCommandSyntax() {
        return "```\n@Entity (" + data.getPrefix() + ") {starting message}\n```";
    }

    public boolean isDisabled() {
        return false;
    }

    public String getRequiredRoles() {
        return "Everyone";
    }

}