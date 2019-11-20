package dev.dbdh.Discord.Listeners.Information;

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

import java.time.Instant;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

public class ListWarnings extends ListenerAdapter {

    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        Gson g = new Gson();
        JSONParser parser = new JSONParser();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "warnings")){
            if(args.length < 2){
                db.connect();
                MongoCollection<Document> members = db.getCollection("members");
                Document member = members.find(eq("memberId", event.getMember().getUser().getId())).first();
                String memberJSON = g.toJson(JSON.serialize(member));
                db.close();
                eb.setDescription("Warnings for " + event.getMember().getAsMention());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Warnings List", data.getSelfAvatar(event));
                try {
                    JSONObject memberObj = (JSONObject) parser.parse(memberJSON);
                    JSONArray warnings = (JSONArray) memberObj.get("warnings");
                    Iterator<JSONObject> iterator = warnings.iterator();
                    while(iterator.hasNext()){
                        JSONObject objt = iterator.next();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public String getName(){
        return "Warnings";
    }

    public String getDescription() {
        return "Lists the warnings of the specified member, If no member is specified it will retrieve the warnings of the command issuer";
    }

    public String getShortDescription() {
        return "Lists the warnings of a member";
    }

    public String getRequiredRoles() {
        return "Everyone";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "warnings [@member]\n```";
    }

    public boolean isDisabled() {
        return false;
    }


}
