package dev.dbdh.Discord.Listeners.Economy;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;

public class AddEveryoneToDatabase extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        EmbedBuilder eb = new EmbedBuilder();
        RoleCheck rc = new RoleCheck();
        if(args[0].equalsIgnoreCase("I_CRAVE_DATA_PLEASE_FEED_ME_DATA") && rc.isDeveloper(event)){
            Long unixTime = System.currentTimeMillis();
            Database.connect();
            MongoCollection<Document> members = Database.getCollection("members");
            int count = 0;
            for(Member member : event.getGuild().getMembers()) { // For each member in the servers members
                if (!member.getUser().isBot()) {
                count++;
                boolean test;
                try{
                    test = members.countDocuments(eq("memberID", member.getId())) > 0;
                    String p = (test)?"true":"false"; // The amount of documents is 0.
                    //event.getChannel().sendMessage(p).queue();
                    //test = !members.find(eq("memberID", member.getId())).first().isEmpty();// will always be true or break
                    event.getChannel().sendMessage(member.getId()).queue();
                }
                catch (Exception e){
                    test = true;
                }
                if (!test) {
                        Document items = new Document(
                                new BasicDBObject("ACE_IN_THE_HOLETheHole", 0)
                                .append("PHARMACY", 0)
                                .append("PLUNDERS_INSTINCT", 0)
                                .append("QUICK_AND_QUIET", 0)
                                .append("DEAD_HARD", 0 )
                                .append("DECISIVE_STRIKE", 0)
                                .append("WHISPERS" , 0)
                                .append("BARBECUE_AND_CHILI", 0)
                                .append("SLOPPY_BUTCHER", 0)
                                .append("TENACITY", 0)
                                .append("TINKERER", 0)
                                .append("BORROWED_TIME", 0)
                                .append("BASIC_CHEST", 25)
                                .append("SAFETY_CHEST", 15)
                                .append("GLITCH_CHEST", 5)
                                .append("SHINY_CHEST", 3)
                                .append("EPIC_CHEST", 1)
                                .append("LEGENDARY_CHEST", 1)
                                .append("GODLY_CHEST", 0));

                        Document chestsOpened = new Document(
                                new BasicDBObject("BASIC_CHEST", 0)
                                .append("SAFETY_CHEST", 0)
                                .append("GLITCH_CHEST", 0)
                                .append("SHINY_CHEST", 0)
                                .append("EPIC_CHEST", 0)
                                .append("LEGENDARY_CHEST", 0)
                                .append("GODLY_CHEST", 0));

                        Document memberInfo = new Document(
                                new BasicDBObject("memberId", member.getUser().getId())
                                        .append("memberName", member.getUser().getName() + "#" + member.getUser().getDiscriminator())
                                        .append("balance", 125000L).append("items", items)
                                        .append("chestsOpened", chestsOpened).append("eventWins", 0)
                                        .append("dailyCooldown", unixTime)
                                        .append("freeBasicCooldown", unixTime)
                                        .append("chaseCooldown", unixTime));
                        members.insertOne(memberInfo);
                    }

                }
            }

            eb.setDescription("Added " + count + " members to the Dead by Daylight Hub Database, Thank you for letting us harvest your data!");
            eb.setColor(color.getRandomColor());
            eb.setTimestamp(Instant.now());
            eb.setFooter("Entity Data Harvester", data.getSelfAvatar(event));

            event.getChannel().sendMessage(eb.build()).queueAfter(5, TimeUnit.SECONDS, ((message) -> {
                eb.clear();
            }));
            Database.close();
        }
    }
}
