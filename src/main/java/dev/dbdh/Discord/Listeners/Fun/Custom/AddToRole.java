package dev.dbdh.Discord.Listeners.Fun.Custom;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class AddToRole extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder eb = new EmbedBuilder();
        Color color = new Color();
        Data data = new Data();
        if(args[0].equalsIgnoreCase(Data.getPrefix() + "storm" )){
            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("664300092831825942")).queue();
            event.getMessage().delete().queue();
            eb.setDescription("Thank you for watching " + event.getGuild().getMemberById("664308445213753366").getEffectiveName() + " streams and supporting the " + event.getGuild().getName() +"!");
            eb.setColor(color.successGreen);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Entity Role Added", data.getSelfAvatar(event));

            event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> eb.clear());
        }
    }
}
