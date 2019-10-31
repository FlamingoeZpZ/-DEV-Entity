package dev.dbdh.Discord.Listeners.Miscellaneous;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.Arrays;

public class Censor extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        Color color = new Color();
        Data data = new Data();
        String[] curseWords = {"Ass", "Fuck", "Nigger", "Fag", "Cunt", "Bitch", "Shit"};
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder eb = new EmbedBuilder();
        for (String arg : args) {
            if(Arrays.stream(curseWords).anyMatch(arg::equalsIgnoreCase)){
                eb.setDescription("Deleted message from {member} for saying a blacklisted word!".replace("{member}", event.getMember().getAsMention()));
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Blacklisted Word Filter", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    data.getLogChannel(event).sendMessage(eb.build()).queue((msg) -> {
                        event.getMessage().delete().queue();
                    });
                });
            }
        }
    }
}
