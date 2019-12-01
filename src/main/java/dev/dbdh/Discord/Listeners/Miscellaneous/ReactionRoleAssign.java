package dev.dbdh.Discord.Listeners.Miscellaneous;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionRoleAssign extends ListenerAdapter {

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event){
        if(!event.getMember().getUser().isBot()){
            if(event.getChannel().getId().equals("638558586645118997")){

            }
        }
    }

}
