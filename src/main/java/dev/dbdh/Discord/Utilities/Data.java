package dev.dbdh.Discord.Utilities;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Random;

public class Data {

    public String getPrefix(){
        return "~";
    }

    public int getJoinNumber(){

        Random RNJesus = new Random();
        int i = RNJesus.nextInt(23);

        return i;
    }

    public TextChannel getJoinChannel(GuildMemberJoinEvent event){
        return event.getGuild().getTextChannelById("540740427511234583");
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

    public int killerRed(){
        return 0xb20c0c;
    }

    public int survivorBlue(){
        return 0xa1e5f3;
    }
}
