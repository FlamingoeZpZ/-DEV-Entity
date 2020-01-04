package dev.dbdh.Discord.Listeners.Miscellaneous;


import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Counter;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.time.Instant;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

public class Join extends ListenerAdapter {
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Counter counter = new Counter();
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();

        String[] messages = {
                "[member] just joined the server - glhf!",
                "[member] just joined. Everyone, look busy!",
                "[member] just joined. Can I get a heal?",
                "[member] joined your party.",
                "[member] joined. You must construct additional pylons!",
                "Ermagherd. [member] is here!",
                "Welcome, [member]. Stay awhile and listen.",
                "Welcome, [member]. We were expecting you ( ͡° ͜ʖ ͡°)",
                "Welcome, [member]. We hope you brought pizza.",
                "Welcome [member]. Leave your weapons by the door.",
                "A wild [member] appeared.",
                "Swoooosh. [member] just landed.",
                "Brace yourselves. [member] just joined the server.",
                "[member] just joined. Hide your bananas.",
                "[member] just arrived. Seems OP - please nerf.",
                "[member] just slid into the server.",
                "A [member] has spawned in the server.",
                "Big [member] showed up!",
                "Where’s [member]? In the server!",
                "[member] hopped into the server. Kangaroo!!",
                "[member] just showed up. Hold my beer.",
                "Never gonna give [member] up, never gonna let [member] down!",
                "It's dangerous to go alone, take [member].",
                "It's [member]! Praise the sun!. [T]/",
                "Ha! [member] has joined. You activated my trap card!",
                "Hello is it [member] you're looking for?"
        };

        if (!event.getMember().getUser().isBot()) {
            Long unixTime = System.currentTimeMillis();
            Database.connect();
            MongoCollection<Document> members = Database.getCollection("members");
            if (members.find(eq("memberID", event.getMember().getUser().getId())).first() == null) {
                Document items = new Document(
                        new BasicDBObject("ACE_IN_THE_HOLETheHole", 0)
                                .append("PHARMACY", 0)
                                .append("PLUNDERS_INSTINCT", 0)
                                .append("QUICK_AND_QUIET", 0)
                                .append("DEAD_HARD", 0 )
                                .append("DECISIVE_STRIKE", 0)
                                .append("WHISPERS" , 0)
                                .append("BARBEQUE_AND_CHILI", 0)
                                .append("SLOPPY_BUTCHER", 0)
                                .append("TENACITY", 0)
                                .append("TINKERER", 0)
                                .append("BORROWED_TIME", 0)
                                .append("BASIC_CHEST", 10)
                                .append("SAFETY_CHEST", 5)
                                .append("GLITCH_CHEST", 5)
                                .append("SHINY_CHEST", 1)
                                .append("EPIC_CHEST", 0)
                                .append("LEGENDARY_CHEST", 0)
                                .append("GODLY_CHEST", 0));
                Document chestsOpened = new Document(new BasicDBObject("BASIC_CHEST", 0).append("SAFETY_CHEST", 0).append("GLITCH_CHEST", 0).append("SHINY_CHEST", 0).append("EPIC_CHEST", 0).append("LEGENDARY_CHEST", 0).append("GODLY_CHEST", 0));
                Document memberInfo = new Document(new BasicDBObject("memberId", event.getMember().getUser().getId()).append("memberName", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator()).append("balance", 100000L).append("items", items).append("chestsOpened", chestsOpened).append("eventWins", 0).append("dailyCooldown", unixTime).append("freeBasicCooldown", unixTime).append("chaseCooldown", unixTime));
                members.insertOne(memberInfo);
            }
            Database.close();

            Random random = new Random();
            int message = random.nextInt(messages.length);
            eb.setDescription(messages[message].replace("[member]", event.getMember().getEffectiveName()));
            eb.setColor(color.getRandomColor());
            eb.setThumbnail(event.getMember().getUser().getEffectiveAvatarUrl());
            eb.setTimestamp(Instant.now());
            eb.setFooter(counter.getMemberCount(event) + " members", data.getSelfAvatar(event));

            data.getLogChannel(event).sendMessage(eb.build()).queue((message1) -> {
                data.getJoinChannel(event).sendMessage(eb.build()).queue();
                eb.clear();
            });
        }
    }
}