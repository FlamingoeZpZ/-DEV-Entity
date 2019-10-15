package dev.dbdh.Discord.Utilities;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class Data {

    public String getPrefix(){
        return "~";
    }

    public int getRandomNumber(){

        Random RNJesus = new Random();
        int i = RNJesus.nextInt(23);

        return i;
    }

    public String getSelfAvatar(GuildMemberJoinEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildMemberLeaveEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }
    public String getSelfAvatar(GuildMessageReceivedEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }
}
