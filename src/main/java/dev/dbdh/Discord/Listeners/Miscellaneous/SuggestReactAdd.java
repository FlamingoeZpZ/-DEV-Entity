package dev.dbdh.Discord.Listeners.Miscellaneous;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuggestReactAdd extends ListenerAdapter {

    public static String messageID = "";
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (event.getChannel().getId().equals("680951134726258691")) {
            messageID = "";
            event.getMessage().addReaction("✅").queue((message) -> {
                event.getMessage().addReaction("❌").queue();
            });
            messageID = event.getMessageId();
        }
    }

}
