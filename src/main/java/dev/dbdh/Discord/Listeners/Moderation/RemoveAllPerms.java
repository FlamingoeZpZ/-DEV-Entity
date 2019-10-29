package dev.dbdh.Discord.Listeners.Moderation;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RemoveAllPerms extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(args[0].equalsIgnoreCase("addperson")){
            
            event.getJDA().getGuildById("608468574264229928").addMember("", args[1]).complete();
        }
    }

}