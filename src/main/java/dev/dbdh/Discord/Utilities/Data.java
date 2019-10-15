package dev.dbdh.Discord.Utilities;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;

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


}
