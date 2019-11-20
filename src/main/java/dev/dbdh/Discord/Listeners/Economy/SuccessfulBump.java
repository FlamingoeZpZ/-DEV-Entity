package dev.dbdh.Discord.Listeners.Economy;

import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuccessfulBump extends ListenerAdapter {

    public void onGuildMessageEmbed(GuildMessageEmbedEvent event){
        if(event.getMessageEmbeds().get(0).toData().getString("description").contains("<@") && event.getMessageEmbeds().get(0).toData().getString("description").contains(">")){
            String embedDescription = event.getMessageEmbeds().get(0).toData().getString("description");
        }
    }

}
