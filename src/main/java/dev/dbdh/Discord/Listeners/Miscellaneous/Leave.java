package dev.dbdh.Discord.Listeners.Miscellaneous;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Leave extends ListenerAdapter {
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Data data = new Data();
        Color color = new Color();
       EmbedBuilder Log = new EmbedBuilder();
        int bots = 0;
        int trueMembers = 0;

        for(Member member: event.getGuild().getMembers()){
            if(member.getUser().isBot()){
                bots += 1;
            }
        }
        trueMembers = event.getGuild().getMembers().size() - bots;

        if(event.getGuild().getMemberCache().size() % 100 == 0) {
            Log.setDescription("Departing member, reaching a multiple of 100:" + event.getMember().getAsMention() + "\nhas left the server! Population is now: __" + trueMembers +"__");
            Log.setColor(color.gold);
            Log.setImage("https://cdn.pixabay.com/photo/2013/07/12/12/37/letter-146018_960_720.png");
        }
        else {
            Log.setDescription("Departing member, " + event.getMember().getAsMention() + "\nhas left the server! Population is now: " + trueMembers);
            Log.setColor(color.red);
            Log.setImage("https://upload.wikimedia.org/wikipedia/commons/f/f1/Heavy_red_%22x%22.png");
        }
        event.getGuild().getTextChannelById("540739308437372935").sendMessage(Log.build()).queue(); //FIX ID

        event.getUser().openPrivateChannel().queue((channel) ->
        {
            EmbedBuilder SentMsg = new EmbedBuilder();
            SentMsg.setTitle("We're sorry that you left, " + channel.getJDA().getGuildById("537736395268161537") + ".\n");
            SentMsg.setImage(channel.getJDA().getGuildById("537736395268161537").getIconUrl());
            SentMsg.setDescription("We hope you enjoyed your stay!\nIf you change your mind join us back: <https://discordapp.com/invite/bcDbsrP> Maybe we can improve your stay");
            SentMsg.setColor(color.darkGreen);
            channel.sendMessage(SentMsg.build()).queue();
        });
    }
}
