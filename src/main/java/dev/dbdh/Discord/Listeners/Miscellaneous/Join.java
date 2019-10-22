package dev.dbdh.Discord.Listeners.Miscellaneous;


import dev.dbdh.Discord.Utilities.Counter;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Join extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Counter counter = new Counter();
        Data data = new Data();
        EmbedBuilder eb = new EmbedBuilder();

    }
}
