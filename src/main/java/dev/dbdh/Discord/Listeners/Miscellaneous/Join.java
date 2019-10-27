package dev.dbdh.Discord.Listeners.Miscellaneous;


import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Counter;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Join extends ListenerAdapter {
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Counter counter = new Counter();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        if (!event.getMember().getUser().isBot()) {
            db.connect();
            MongoCollection<Document> members = db.getCollection("members");
            if (members.find(eq("memberID", event.getMember().getUser().getId())).first() == null) {
                Document perksActive = new Document(new BasicDBObject("aceInTheHole", 0).append("pharmacy", 0).append("plunderers", 0));
                Document memberInfo = new Document(new BasicDBObject("memberId", event.getMember().getUser().getId()).append("memberName", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator()).append("balance", 0).append("perksActive", perksActive).append("eventWins", 0));
                members.insertOne(memberInfo);
            }
            db.close();

            String[] URL = {
                    //Survivor
                    "https://entity.nestedvar.dev/images/survivor/1.jpg",
                    "https://entity.nestedvar.dev/images/survivor/2.jpg",
                    "https://entity.nestedvar.dev/images/survivor/3.jpeg",
                    "https://entity.nestedvar.dev/images/survivor/4.jpeg",
                    "https://entity.nestedvar.dev/images/survivor/5.png",
                    "https://entity.nestedvar.dev/images/survivor/6.jpg",
                    "https://entity.nestedvar.dev/images/survivor/7.png",
                    "https://entity.nestedvar.dev/images/survivor/8.gif",
                    "https://entity.nestedvar.dev/images/survivor/9.gif",
                    "https://entity.nestedvar.dev/images/survivor/10.png",
                    //broken "https://scontent-iad3-1.cdninstagram.com/vp/c5da5e362c3fdb06129f62c3adf91a90/5D9E72E0/t51.2885-15/sh0.08/e35/c85.0.725.725a/s640x640/56331123_405441466939574_4999243448575151304_n.jpg?_nc_ht=scontent-iad3-1.cdninstagram.com",
                    "https://entity.nestedvar.dev/images/survivor/12.jpeg",
                    //Killer
                    "https://entity.nestedvar.dev/images/killer/1.png",
                    "https://entity.nestedvar.dev/images/killer/2.png",
                    "https://entity.nestedvar.dev/images/killer/3.jpeg",
                    "https://entity.nestedvar.dev/images/killer/4.png",
                    "https://entity.nestedvar.dev/images/killer/5.gif",
                    "https://entity.nestedvar.dev/images/killer/6.jpg",
                    "https://entity.nestedvar.dev/images/killer/7.png",
                    "https://entity.nestedvar.dev/images/killer/8.gif",
                    "https://entity.nestedvar.dev/images/killer/9.gif",
                    "https://entity.nestedvar.dev/images/killer/10.jpg",
                    "https://entity.nestedvar.dev/images/killer/11.jpg",
                    "https://entity.nestedvar.dev/images/killer/12.jpg"

            };
            String[] FollowText = {
                    //Survivor
                    "But I need my perks Behaviour ",
                    "Because Altruism is for psychos ",
                    "Maybe we shouldn't do cross country SWF? ",
                    "Typical white ward action :sob: ",
                    "There is no coming back from that one chief... ",
                    "Altrusim too gud ",
                    "Jake is cuter but it's whatever ",
                    "Epic... Gamer... Moment ",
                    "Don't hurt Meg pls ",
                    "Dwight being smooth As FUCK ",
                    "Ma Puppet :sob: ",
                    "What will you survivors do now? ",
                    //Killer
                    "This pic speaks for itself ",
                    "Maybe you should be worried? ",
                    "RIP hex totem... I woulda told a joke, but it'd be too dull ",
                    "You won't be able to ",
                    "Bing Bong, Welcome our newest Member! ",
                    event.getGuild().getMemberById("383771414290890773").getNickname() + " is a filthy Nurse main please hate him ",
                    "CARRRRL ",
                    "Legion at it again! Hittin up em stabs ",
                    "Hello, " + event.getMember().getAsMention() + " I'm always watching ",
                    "The light burns me too ",
                    "Permanent pain for poor micheal ",
                    "No wonder the survivors say we have tunnel vision, we just can't see "
            };

        }
    }
}
