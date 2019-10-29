package dev.dbdh.Discord.Listeners.Miscellaneous;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class Leave extends ListenerAdapter {
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Color color = new Color();
        Data data = new Data();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();

        if(!event.getMember().getUser().isBot()){
            ecu.removeMemberFromDatabase(event, event.getMember().getUser().getName());
            eb.setDescription("Removed " + event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator() + " from the database.");
            eb.setColor(color.errorRed);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Member Left | Removed from database", data.getSelfAvatar(event));
            data.getLogChannel(event).sendMessage(eb.build()).queue((message) ->{
                eb.clear();
            });
        }
    }
}
