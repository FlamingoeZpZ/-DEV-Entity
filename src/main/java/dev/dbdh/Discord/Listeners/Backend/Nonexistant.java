package dev.dbdh.Discord.Listeners.Backend;

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

public class Nonexistant extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        RoleCheck rc = new RoleCheck();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "nonexsistant")) {
            if (rc.isDeveloper(event)) {
                db.connect();
                MongoCollection<Document> members = db.getCollection("members");
                Long unixTime = System.currentTimeMillis();
                eb.setDescription("The following members joined while the bot was down/broken. I have added them to the database. If nothing is here then no members have joined while the bot was down.\n\n");
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Non-existant member", data.getSelfAvatar(event));
                for (
                        Member member : event.getJDA().getGuildById("537736395268161537").getMembers()) {
                    if (members.find(eq("memberId", member.getId())).first() == null) {
                        if (!member.getUser().isBot() || member.getUser().isFake()) {
                            Document items = new Document(new BasicDBObject("ACE_IN_THE_HOLE", 0).append("PHARMACY", 0).append("PLUNDERS_INSTINCT", 0).append("QUICK_AND_QUIET", 0).append("DEAD_HARD", 0).append("DECISIVE_STRIKE", 0).append("WHISPERS", 0).append("BARBEQUE_AND_CHILI", 0).append("SLOPPY_BUTCHER", 0).append("TENACITY", 0).append("TINKERER", 0).append("BORROWED_TIME", 0).append("BASIC_CHEST", 10).append("SAFETY_CHEST", 5).append("GLITCH_CHEST", 5).append("SHINY_CHEST", 1).append("EPIC_CHEST", 0).append("LEGENDARY_CHEST", 0).append("GODLY_CHEST", 0));
                            Document chestsOpened = new Document(new BasicDBObject("BASIC_CHEST", 0).append("SAFETY_CHEST", 0).append("GLITCH_CHEST", 0).append("SHINY_CHEST", 0).append("EPIC_CHEST", 0).append("LEGENDARY_CHEST", 0).append("GODLY_CHEST", 0));
                            Document memberInfo = new Document(new BasicDBObject("memberId", member.getUser().getId()).append("memberName", member.getUser().getName() + "#" + member.getUser().getDiscriminator()).append("level", 1).append("balance", 100000L).append("experience", 0L).append("items", items).append("chestsOpened", chestsOpened).append("eventWins", 0).append("dailyCooldown", unixTime).append("freeBasicCooldown", unixTime).append("chaseCooldown", unixTime));
                            members.insertOne(memberInfo);
                            eb.appendDescription(member.getAsMention() + "\n");
                        }
                    }
                }
                db.close();
                data.getLogChannel(event).sendMessage(eb.build()).queue((message) -> {
                    eb.clear();
                });
            } else {
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
}
