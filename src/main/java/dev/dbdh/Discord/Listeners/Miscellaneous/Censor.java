package dev.dbdh.Discord.Listeners.Miscellaneous;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;

public class Censor extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        Gson g = new Gson();
        JSONParser parser = new JSONParser();
        db.connect();
        MongoCollection<Document> guild = db.getCollection("guild");
        Document guildDoc = guild.find().first();
        String curseWordsString = g.toJson(JSON.serialize(guildDoc));
        db.close();
        JSONObject curseWordObj;
        try{
            curseWordObj = (JSONObject) parser.parse(curseWordsString);
        } catch(ParseException e){
            e.printStackTrace();
        }
        JSONArray arr = new JSONArray(curseWordObj.get("blacklistedWords"));
        List<String> list = new ArrayList<>();
        for(int i = 0; i < arr.size(); i++){
            list.add(arr.get(i).toString());
        }
        String[] curseWords = list.toArray(new String[0]);
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        EmbedBuilder eb = new EmbedBuilder();
        for (String arg : args) {
            if(Arrays.stream(curseWords).anyMatch(arg::equalsIgnoreCase)){
                eb.setDescription("{member} has said a blacklisted word!\nChannel: {channel}\nMessage: {message}\nMessage Hyperlink: {link}".replace("{member}", event.getMember().getAsMention()).replace("{channel}", event.getChannel().getAsMention()).replace("{message}", event.getMessage().toString()).replace("{link}", event.getMessage().getJumpUrl()));
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Blacklisted Word Filter", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    data.getLogChannel(event).sendMessage(eb.build()).queue();
                });
            }
        }
    }
}
