package dev.dbdh.Discord.Listeners.Economy;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.time.Instant;

import static com.mongodb.client.model.Filters.eq;

public class AddEveryoneToDatabase extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        if(args[0].equalsIgnoreCase("addEveryoneStoopidBot")){
            db.connect();
            MongoCollection<Document> members = db.getCollection("members");
            for(Member member: event.getGuild().getMembers()) {
                if (members.find(eq("memberId", member.getUser().getId())).first() == null) {
                    Document perksActive = new Document(new BasicDBObject("aceInTheHole", 0).append("pharmacy", 0).append("plunderers", 0));
                    Document memberInfo = new Document(new BasicDBObject("memberId", event.getMember().getUser().getId()).append("memberName", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator()).append("balance", 0).append("perksActive", perksActive).append("eventWins", 0));
                    members.insertOne(memberInfo);
                    eb.setDescription("Added " + member.getUser().getName() + " to Dead by Daylight Hub Database, Thank you for letting us harvest your data!");
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Data Harvester", member.getUser().getEffectiveAvatarUrl());

                    event.getChannel().sendMessage(eb.build()).queue((message) ->{
                        eb.clear();
                    });
                }
            }
            db.close();
        }
    }
}
