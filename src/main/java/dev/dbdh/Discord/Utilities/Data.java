package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Listeners.Miscellaneous.Ready;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Random;

public class Data {
    Random RNJesus = new Random();
    private static final Database db = new Database();

    public static String getPrefix() {
        String prefix = "!~";
        db.connect();
        MongoCollection<Document> guild = db.getCollection("guild");
        prefix = guild.find().first().getString("prefix");
        db.close();
        return prefix;
    }

    public static void setPrefix(String prefix) {
        db.connect();
        MongoCollection<Document> guild = db.getCollection("guild");
        String oldPrefix = guild.find().first().getString("prefix");
        Bson filter = new Document("prefix", oldPrefix);
        Bson newPrefix = new Document("prefix", prefix);
        Bson updatePrefix = new Document("$set", newPrefix);
        guild.updateOne(filter, updatePrefix);
        db.close();
    }
    public int getJoinNumber(){
        int i = RNJesus.nextInt(23);
        return i;
    }

    public static TextChannel getJoinChannel(GuildMemberJoinEvent event){
        return event.getGuild().getTextChannelById("640005609760227371");
    }

    public static String getSelfAvatar(GuildMemberJoinEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public static String getSelfAvatar(GuildMemberLeaveEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public static String getSelfAvatar(GuildMessageReceivedEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public static String getSelfAvatar(UserUpdateNameEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public static String getSelfAvatar(GuildMessageReactionAddEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public static String getSelfAvatar(GuildMessageReactionRemoveEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public static String getSelfAvatar(GuildMessageEmbedEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public static String getSelfAvatar(ReadyEvent event) {
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }
    public String intToTime(long milliInt){
        String phrase = "";
        long s = milliInt;
        milliInt /= 60;
        long m = milliInt;
        milliInt /= 60;
        long h = milliInt;
        milliInt /= 24;
        if(milliInt > 0)
        phrase += milliInt + " days, ";
        if(h % 24 > 0)
        phrase += (h % 24) + " hours, ";
        if(s % 60 > 0)
        phrase += (m % 60) + " minutes and ";
        phrase += (s % 60) + " seconds";
        return phrase;
    }

    public static String getSelfName(GuildMemberJoinEvent event){
        return event.getJDA().getSelfUser().getName();
    }
    public static String getSelfName(GuildMessageReceivedEvent event){
        return event.getJDA().getSelfUser().getName();
    }

    public static TextChannel getLogChannel(GuildMessageReceivedEvent event){
        return event.getGuild().getTextChannelById("635607577794969644");
    }

    public static TextChannel getLogChannel(GuildMemberJoinEvent event){
        return event.getGuild().getTextChannelById("635607577794969644");
    }

    public static TextChannel getLogChannel(GuildMemberLeaveEvent event){
        return event.getGuild().getTextChannelById("635607577794969644");
    }
    public static TextChannel getLogChannel(GuildMessageEmbedEvent event){
        return event.getGuild().getTextChannelById("635607577794969644");
    }

    public static TextChannel getLogChannel(ReadyEvent event){
        return event.getJDA().getGuildById("537736395268161537").getTextChannelById("635607577794969644");
    }
}
