package dev.dbdh.Discord.Listeners.Miscellaneous;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;

public class Ready extends ListenerAdapter {

    public void onReady(ReadyEvent event){
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
        Activity[] games = { Activity.watching("the github repository"), Activity.watching("the clown suck fingers"), Activity.watching("you get destroyed in Dead By Daylight"), Activity.watching("you getting torn apart by the hag"), Activity.playing("hide and seek with wraith"), Activity.playing("with my flashlight"), Activity.playing("with some pallets"), Activity.watching("survivors run"), Activity.playing("claudette simulator"), Activity.playing("with your suggestions"), Activity.watching("Dwight hide in a locker") };
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> {
            Random random = new Random();
            int game = random.nextInt(games.length);
            event.getJDA().getPresence().setActivity(games[game]);
        }, 0, 2, TimeUnit.MINUTES);

        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Long unixTime = System.currentTimeMillis();
        eb.setDescription("The following members joined while the bot was down/broken. I have added them to the database. If nothing is here then no members have joined while the bot was down.\n\n");
        eb.setColor(color.getRandomColor());
        eb.setTimestamp(Instant.now());
        eb.setFooter("Non-existant member", data.getSelfAvatar(event));
        for(Member member: event.getJDA().getGuildById("537736395268161537").getMembers()){
            if(members.find(eq("memberId", member.getId())).first() == null){
                if(!member.getUser().isBot() || member.getUser().isFake()){
                    Document items = new Document(new BasicDBObject("ACE_IN_THE_HOLETheHole", 0).append("PHARMACY", 0).append("PLUNDERS_INSTINCT", 0).append("QUICK_AND_QUIET", 0).append("DEAD_HARD", 0).append("DECISIVE_STRIKE", 0).append("WHISPERS" , 0).append("BARBEQUE_AND_CHILI", 0).append("SLOPPY_BUTCHER", 0).append("TENACITY", 0).append("TINKERER", 0).append("BORROWED_TIME", 0).append("BASIC_CHEST", 10).append("SAFETY_CHEST", 5).append("GLITCH_CHEST", 5).append("SHINY_CHEST", 1).append("EPIC_CHEST", 0).append("LEGENDARY_CHEST", 0).append("GODLY_CHEST", 0));
                    Document chestsOpened = new Document(new BasicDBObject("BASIC_CHEST", 0).append("SAFETY_CHEST", 0).append("GLITCH_CHEST", 0).append("SHINY_CHEST", 0).append("EPIC_CHEST", 0).append("LEGENDARY_CHEST", 0).append("GODLY_CHEST", 0));
                    Document memberInfo = new Document(new BasicDBObject("memberId", member.getUser().getId()).append("memberName", member.getUser().getName() + "#" + member.getUser().getDiscriminator()).append("level", 1).append("balance", 100000L).append("experience", 0L).append("items", items).append("chestsOpened", chestsOpened).append("eventWins", 0).append("dailyCooldown", unixTime).append("freeBasicCooldown", unixTime).append("chaseCooldown", unixTime));
                    members.insertOne(memberInfo);
                    eb.appendDescription(member.getAsMention() + "\n");
                }
            }
        }
        db.close();
        data.getLogChannel(event).sendMessage(eb.build()).queue();
    }

}
