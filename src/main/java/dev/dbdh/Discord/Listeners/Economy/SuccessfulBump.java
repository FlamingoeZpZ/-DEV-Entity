package dev.dbdh.Discord.Listeners.Economy;

import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuccessfulBump extends ListenerAdapter {

    public void onGuildMessageEmbed(GuildMessageEmbedEvent event){
        System.out.println(event.getMessageEmbeds().get(0).toData());
    }

}
