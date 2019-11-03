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
import java.time.LocalDateTime;
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
            db.connect();
            MongoCollection<Document> members = db.getCollection("members");
            if (members.find(eq("memberID", event.getMember().getUser().getId())).first() == null) {
                Document perksActive = new Document(new BasicDBObject("aceInTheHole", 0).append("pharmacy", 0).append("plunderers", 0).append("timeWizard", 0).append("quickAndQuiet", 0 ).append("deadHard", 0).append("devisive" , 0));
                Document chestsOwned = new Document(new BasicDBObject("basicChest", 0).append("shinyChest", 0).append("legendaryChest", 0).append("mythicChest", 0));
                Document chestsOpened = new Document(new BasicDBObject("basicChest", 0).append("shinyChest", 0).append("legendaryChest", 0).append("mythicChest", 0));
                Document cooldowns = new Document(new BasicDBObject("daily", unixTime).append("freeBasic", unixTime).append("chase", unixTime));
                Document memberInfo = new Document(new BasicDBObject("memberId", event.getMember().getUser().getId()).append("memberName", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator()).append("balance", 2500L).append("perksActive", perksActive).append("chestsOwned", chestsOwned).append("chestsOpened", chestsOpened).append("eventWins", 0).append("cooldowns", cooldowns));
                members.insertOne(memberInfo);
            }
            db.close();

            Random random = new Random();
            int message = random.nextInt(messages.length);
            eb.setDescription(messages[message].replace("[member]", event.getMember().getAsMention()));
            eb.setColor(color.getRandomColor());
            eb.setTimestamp(Instant.now());
            eb.setFooter(counter.getMemberCount(event) + " members", data.getSelfAvatar(event));

            data.getLogChannel(event).sendMessage(eb.build()).queue((message1) -> {
                eb.clear();
            });
        }
    }
}