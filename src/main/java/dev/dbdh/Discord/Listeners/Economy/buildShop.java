package dev.dbdh.Discord.Listeners.Economy;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;


public class buildShop extends ListenerAdapter{

        public void onGuildMessageReceived(GuildMessageReceivedEvent event){
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            Color color = new Color();
            Data data = new Data();
            EmbedBuilder eb = new EmbedBuilder();
            RoleCheck rc = new RoleCheck();
            if(args[0].equalsIgnoreCase("Rebuild_Shop")){
                Database.connect();
                MongoCollection<Document> shopItems = Database.getCollection("shopItems");
                for(int i = 0; i < shopItems.countDocuments(); i++) {
                    shopItems.findOneAndDelete(eq("ID", i)); // Literally just deletes them all one by one
                }
                List<Document> items = new ArrayList<Document>();
                items.add(new Document("ID" , 1).append("name", "ACE_IN_THE_HOLE").append("defaultPrice", 12000).append("description", "Increases XP gained from opening chests.").append("usage", "chest_perk"));
                items.add(new Document("ID" , 2).append("name", "PHARMACY").append("defaultPrice", 20000).append("description", "Increases GOLD gained from opening chests.").append("usage", "chest_perk"));
                items.add(new Document("ID" , 3).append("name", "PLUNDERS_INSTINCT").append("defaultPrice", 50000).append("description", "Increases chance to OPEN MULTIPLE CHESTS.").append("usage", "chest_perk"));

                items.add(new Document("ID" , 4).append("name", "QUICK_AND_QUIET").append("defaultPrice", 7000).append("description", "Allows for EXTRA ERRORS during chases.").append("usage", "chase_perk"));
                items.add(new Document("ID" , 5).append("name", "DEAD_HARD").append("defaultPrice", 18500).append("description", "Increases XP gained from chases.").append("usage", "chase_perk"));
                items.add(new Document("ID" , 6).append("name", "DECISIVE_STRIKE").append("defaultPrice", 17500).append("description", "Increases GOLD gained from opening chests.").append("usage", "chase_perk"));

                items.add(new Document("ID" , 7).append("name", "WHISPERS").append("defaultPrice", 34500).append("description", "DECREASE chance of survivors ESCAPING").append("usage", "FGS_perk"));
                items.add(new Document("ID" , 8).append("name", "BARBEQUE_AND_CHILI").append("defaultPrice", 21000).append("description", "Increases GOLD and XP gained from survivors DYING.").append("usage", "FGS_perk"));
                items.add(new Document("ID" , 9).append("name", "SLOPPY_BUTCHER").append("defaultPrice", 18000).append("description", "Increases LIFE SPAN of survivors.").append("usage", "FGS_perk"));

                items.add(new Document("ID" , 10).append("name", "TENACITY").append("defaultPrice", 48500).append("description", "Increases XP gained.").append("usage", "VIP_perk"));
                items.add(new Document("ID" , 11).append("name", "TINKERER").append("defaultPrice", 54000).append("description", "Increases GOLD gained.").append("usage", "VIP_perk"));
                items.add(new Document("ID" , 12).append("name", "BORROWED_TIME").append("defaultPrice", 120500).append("description", "Decreases ALL cool-downs. Increases survivor LIFE SPAN.").append("usage", "VIP_perk"));

                items.add(new Document("ID" , 13).append("name", "BASIC_CHEST").append("defaultPrice", 200).append("description", "A basic chest. Is also given for free every 5 minutes. Can get any item.").append("usage", "chest_item"));
                items.add(new Document("ID" , 14).append("name", "SAFETY_CHEST").append("defaultPrice", 300).append("description", "A basic chest, but can never give bad events. Is also given for free every 5 minutes. Can get any item common or higher.").append("usage", "chest_type"));
                items.add(new Document("ID" , 15).append("name", "GLITCH_CHEST").append("defaultPrice", 400).append("description", "A basic chest that has increased odds to be opened multiple times. Can get any item below EPIC including losses. Has 5% worse polish").append("usage", "chest_type"));
                items.add(new Document("ID" , 16).append("name", "SHINY_CHEST").append("defaultPrice", 1200).append("description", "A basic chest that forces the item to be shiny (unless item is bad). Can get any item. Has 5% worse polish").append("usage", "chest_type"));
                items.add(new Document("ID" , 17).append("name", "EPIC_CHEST").append("defaultPrice", 2400).append("description", "An epic chest that forces the item to be epic or higher. Has 10% worse polish").append("usage", "chest_type"));
                items.add(new Document("ID" , 18).append("name", "LEGENDARY_CHEST").append("defaultPrice", 4800).append("description", "A legendary chest that forces the item to be legendary. Has 15% worse polish").append("usage", "chest_type"));
                items.add(new Document("ID" , 19).append("name", "GODLY_CHEST").append("defaultPrice", 96000).append("description", "A chest forged by gods. Will always be legendary. Will always be shiny. Has increased duplication chances, but Has 25% worse polish").append("usage", "chest_type"));

                shopItems.insertMany(items);
                eb.setDescription("Rebuilding shop via code-side");
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Data Harvester", data.getSelfAvatar(event));
                event.getChannel().sendMessage(eb.build()).queue();
                Database.close();
            }
        }
    }
