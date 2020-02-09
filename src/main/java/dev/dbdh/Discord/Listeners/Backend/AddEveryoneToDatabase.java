package dev.dbdh.Discord.Listeners.Backend;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
        Database db = new Database();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "rebuilddb")) {
            if (rc.isDeveloper(event)) {
                Long unixTime = System.currentTimeMillis();
                Database.connect();
                MongoCollection<Document> members = Database.getCollection("members");
                int count = 0;
                boolean test;
                for (Member member : event.getGuild().getMembers()) { // For each member in the servers members
                    if (!member.getUser().isBot()) {
                        count++;
                        try {
                            test = members.find().first().containsValue(member.getUser().getId());
                        } catch (Exception e) {
                            test = false;
                        }
                        if (!test) {
                            Document items = new Document(
                                    new BasicDBObject("ACE_IN_THE_HOLE", 0)
                                            .append("PHARMACY", 0)
                                            .append("PLUNDERS_INSTINCT", 0)
                                            .append("QUICK_AND_QUIET", 0)
                                            .append("DEAD_HARD", 0)
                                            .append("DECISIVE_STRIKE", 0)
                                            .append("WHISPERS", 0)
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
                                            .append("level", 1)
                                            .append("balance", 125000L)
                                            .append("experience", 0L)
                                            .append("items", items)
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
        }else {
            eb.setDescription("You don't have the permissions to use this command");
            eb.setColor(color.errorRed);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Insufficient Permissions", data.getSelfAvatar(event));

            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                eb.clear();
                message.delete().queueAfter(15, TimeUnit.SECONDS);
            });
        }
    }
}
