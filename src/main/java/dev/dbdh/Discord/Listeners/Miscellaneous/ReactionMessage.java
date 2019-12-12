package dev.dbdh.Discord.Listeners.Miscellaneous;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class ReactionMessage extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Data data = new Data();
        Database db = new Database();
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();
        EconomyUtilities ecu = new EconomyUtilities();

        if(args[0].equalsIgnoreCase(data.getPrefix() + "roleassign")){
            event.getMessage().delete().queue();
            eb.setDescription("Welcome to the Dead By Daylight Hub Discord Server! \nTo obtain roles simply click the corresponding reaction emote and the role will be added to you! If you no longer wish to have a role simply click the same reaction again. \n\n**Available Roles**\nWhat type of character you mainly play in Dead By Daylight?\n" + event.getGuild().getEmoteById("575437440450560000").getAsMention() + " KILLER\n" + event.getGuild().getEmoteById("579132892370829333").getAsMention() + " SURVIVOR\n\n**How often are you on?** \n" + event.getGuild().getEmoteById("649444100982177792").getAsMention() + " I'm on all the time \n" + event.getGuild().getEmoteById("649444100864475138").getAsMention() + " I'm on occasionally \n" + event.getGuild().getEmoteById("649444100952817686").getAsMention() + " I'm on very rarely\n\n**Games and Media**\n Nearly done! Now, if you'd like to recieve notifications from streamers, be able to view the NSFW chats or see our games sections. Click on the corresponding reaction emote\n" + event.getGuild().getEmoteById("573230699885232140").getAsMention() + " NSFW CHATS\n" + event.getGuild().getEmoteById("573235478711500837").getAsMention() + " GAME CHATS\n" + "\n\n**Notifcations**\nFinally, if you want announcement notifications click or press the corresponding reaction\n" + event.getGuild().getEmoteById("583392850067324928").getAsMention() + " STREAMER NOTIFICATIONS\n" + event.getGuild().getEmoteById("575437537074479114").getAsMention() + "SERVER ANNOUNCEMENTS\n" + event.getGuild().getEmoteById("577557877343125518").getAsMention() + " GAME ANNOUNCEMENTS\n" + event.getGuild().getEmoteById("575437440509280296").getAsMention() + "EVENT ANNOUNCEMENTS");
            eb.setColor(color.darkGreen);
            eb.setFooter("Entity Role Assign", data.getSelfAvatar(event));
            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                //Killer
                message.addReaction(event.getGuild().getEmoteById("575437440450560000")).queue();
                //Survivor
                message.addReaction(event.getGuild().getEmoteById("579132892370829333")).queue();
                //All the time
                message.addReaction(event.getGuild().getEmoteById("649444100982177792")).queue();
                //Occasionally
                message.addReaction(event.getGuild().getEmoteById("649444100864475138")).queue();
                //Rarely
                message.addReaction(event.getGuild().getEmoteById("649444100952817686")).queue();
                //NSFW
                message.addReaction(event.getGuild().getEmoteById("573230699885232140")).queue();
                //Game
                message.addReaction(event.getGuild().getEmoteById("573235478711500837")).queue();
                //Streamer Announcments
                message.addReaction(event.getGuild().getEmoteById("583392850067324928")).queue();
                //Server Ann
                message.addReaction(event.getGuild().getEmoteById("575437537074479114")).queue();
                //Game Ann
                message.addReaction(event.getGuild().getEmoteById("577557877343125518")).queue();
                //Event Ann
                message.addReaction(event.getGuild().getEmoteById("575437440509280296")).queue();
                ecu.setRoleAssignMessageID(message);
            });

        }
    }
}
