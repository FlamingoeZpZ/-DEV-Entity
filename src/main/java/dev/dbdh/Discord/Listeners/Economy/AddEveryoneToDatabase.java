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
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;

public class AddEveryoneToDatabase extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        if(args[0].equalsIgnoreCase("addEveryoneStoopidBot")){
            long unixTime = System.currentTimeMillis() / 1000L;
            db.connect();
            MongoCollection<Document> members = db.getCollection("members");
            for(Member member: event.getGuild().getMembers()) {
                if (members.find(eq("memberID", member.getUser().getId())).first() == null) {
                    if (!member.getUser().isBot()) {
                        Document perksActive = new Document(new BasicDBObject("aceInTheHole", 0).append("pharmacy", 0).append("plunderers", 0).append("timeWizard", 0).append("quickAndQuiet", 0 ).append("deadHard", 0).append("devisive" , 0));
                        Document chestsOwned = new Document(new BasicDBObject("basicChest", 0).append("shinyChest", 0).append("legendaryChest", 0).append("mythicChest", 0));
                        Document chestsOpened = new Document(new BasicDBObject("basicChest", 0).append("shinyChest", 0).append("legendaryChest", 0).append("mythicChest", 0));
                        Document memberInfo = new Document(new BasicDBObject("memberId", member.getUser().getId()).append("memberName", member.getUser().getName() + "#" + member.getUser().getDiscriminator()).append("balance", 2500).append("perksActive", perksActive).append("chestsOwned", chestsOwned).append("chestsOpened", chestsOpened).append("eventWins", 0).append("cooldown", unixTime));
                        members.insertOne(memberInfo);

                    }
                }
            }
            eb.setDescription("Added everyone to the Dead by Daylight Hub Database, Thank you for letting us harvest your data!");
            eb.setColor(color.getRandomColor());
            eb.setTimestamp(Instant.now());
            eb.setFooter("Entity Data Harvester", data.getSelfAvatar(event));

            event.getChannel().sendMessage(eb.build()).queueAfter(5, TimeUnit.SECONDS, ((message) -> {
                eb.clear();
            }));
            db.close();
        }
    }
}
