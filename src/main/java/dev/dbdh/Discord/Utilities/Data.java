package dev.dbdh.Discord.Utilities;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class Data {
    Random RNJesus = new Random();

    public String getPrefix(){
        return "~";
    }

    public int getJoinNumber(){
        int i = RNJesus.nextInt(23);
        return i;
    }

    public TextChannel getJoinChannel(GuildMemberJoinEvent event){
        return event.getGuild().getTextChannelById("540740427511234583");
    }

    public String getSelfAvatar(GuildMemberJoinEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildMessageReceivedEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfName(GuildMemberJoinEvent event){
        return event.getJDA().getSelfUser().getName();
    }

    public String getGuildName(GuildMemberJoinEvent event){
        return event.getGuild().getName();
    }
}
