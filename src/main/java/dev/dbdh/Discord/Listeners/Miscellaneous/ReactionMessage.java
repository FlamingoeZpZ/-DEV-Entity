package dev.dbdh.Discord.Listeners.Miscellaneous;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionMessage extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Data data = new Data();
        Database db = new Database();
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();

        if(args[0].equalsIgnoreCase(data.getPrefix() + "roleassign")){
            event.getMessage().delete().queue();
            eb.setDescription("Welcome to the Dead By Daylight Hub Discord Server! \nTo obtain roles simply click the corresponding reaction emote and the role will be added to you! If you no longer wish to have a role simply click the same reaction again. \n\n**Available Roles**\nWhat type of character you mainly play in Dead By Daylight?\n" + event.getGuild().getEmoteById("575437440450560000").getAsMention() + " KILLER\n" + event.getGuild().getEmoteById("579132892370829333").getAsMention() + "\n\nHow often are you on? \n" + event.getGuild().getEmoteById("649444100982177792").getAsMention() + " I'm on all the time \n" + event.getGuild().getEmoteById("649444100864475138").getAsMention() + " I'm on occasionally \n" + event.getGuild().getEmoteById("649444100952817686").getAsMention() + " I'm on very rarely\n\n");

        }
    }
}
