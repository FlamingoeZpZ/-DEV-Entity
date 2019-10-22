package dev.dbdh.Discord.Utilities;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RoleCheck {

    public boolean isOwner(GuildMessageReceivedEvent event){
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("552572692163985408"));
    }

    public boolean isAdministrator(GuildMessageReceivedEvent event){
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("608869161594126346"));
    }

    public boolean isDeveloper(GuildMessageReceivedEvent event){
        if(event.getMember().getId().equals("79693184417931264") || event.getMember().getId().equals("269913315323543552")){
            return true;
        } else return false;
    }

    public boolean isHeadModerator(GuildMessageReceivedEvent event){
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("608869497998278657"));
    }

    public boolean isModerator(GuildMessageReceivedEvent event){
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("540745858929393689"));
    }
}
