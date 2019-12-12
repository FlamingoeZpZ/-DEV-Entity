package dev.dbdh.Discord.Listeners.Information;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class DBCompare extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "compare")){
            event.getMessage().delete().queue();
            db.connect();
            MongoCollection<Document> members = db.getCollection("members");
            for(Member member: event.getGuild().getMembers()){
                if(members.find(eq("memberID", member.getUser().getId())).first() == null) {
                    eb.setDescription("There is a member missing from the database!\n\n```\nMember Name: " + member.getUser().getName() + "#" + member.getUser().getDiscriminator() + "\nMember ID: " + member.getUser().getId() + "\n```");
                    eb.setColor(color.errorRed);
                    eb.setFooter("Entity Missing Persons Report", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        eb.clear();
                    });
                    continue;
                } else {
                    continue;
                }
            }
            db.close();
        }
    }
}
