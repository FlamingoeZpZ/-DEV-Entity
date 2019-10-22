package dev.dbdh.Discord.Utilities;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Counter {

    public Integer getMemberCount(GuildMemberJoinEvent event){
        Integer bots = 0;
        for (Member member: event.getGuild().getMembers()){
            if(member.getUser().isBot()){
                bots += 1;
            }
        }
        return event.getGuild().getMembers().size() - bots;
    }
}
