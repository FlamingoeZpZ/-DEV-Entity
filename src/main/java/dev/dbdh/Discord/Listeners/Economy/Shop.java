package dev.dbdh.Discord.Listeners.Economy;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class Shop extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");
        Aliases al = new Aliases();
        Color color = new Color(); // Why don't we have these public, then make them into a string kinda thing like if you do args[?].length it give the size of that term
        EconomyUtilities ecu = new EconomyUtilities();
        int pay = 0;
        EmbedBuilder eb = new EmbedBuilder();

        if (Arrays.stream(al.shopAliases).anyMatch(args[0]::equalsIgnoreCase)) {
            Database.connect();
            MongoCollection<Document> members = Database.getCollection("members");
            MongoCollection<Document> shopItems = Database.getCollection("members");
            Document shopItem;
            Data data = new Data();
            Document member = members.find(eq("memberId", event.getMember().getId())).first();

            event.getChannel().sendMessage("went through").queue();
            //(prefix)shop [item] [buy/sell] [amount]
                if(args.length > 1){ //Searching item description

                    shopItem = shopItems.find(eq("ID", args[1])).first();

                    //eb.setImage(Images custom Icon);
                    eb.setTitle(event.getGuild().getName() + " shop - " + shopItem.getString("name"));
                    pay = (int)Math.pow(shopItem.getInteger("defaultPrice"),  (int)member.get("items." + shopItem.toString()) + 1); // divided by level

                    eb.setDescription(shopItem.getString("description") + " Cost: " + pay);
                     eb.setColor(color.darkSlateBlue);
                    event.getChannel().sendMessage("2 args" ).queue();
                }
                if(args.length >= 3 && args.length <=4 && args[2].equalsIgnoreCase("buy")) { // No arg check, IT will be 3 args to get here there for we set amount to 0
                    event.getChannel().sendMessage("4 args").queue();

                    shopItem = shopItems.find(eq("ID", args[1])).first();

                    int i;
                    int k;
                    int total = 0;
                    try {
                        i = Integer.parseInt(args[3]);
                    } catch (Exception e) {
                        i = 1;
                    }
                    for (k = 1; i > 0; i--) {
                        pay = (int)Math.pow(shopItem.getInteger("defaultPrice"),  (int)member.get("items." + shopItem.toString()) + ++k); //One is replaced by amount wanted
                        if(member.getInteger("balance") <= pay) { //add check for max level
                            break;
                        }
                        total += pay;

                        ecu.removeCoins(event, event.getMember().getId(), pay);
                        ecu.addItem(event, event.getMember().getId(), shopItem.toString());
                    }
                    //eb.setImage(); ADD
                    if(k > 0) {
                        eb.setColor(color.successGreen);
                        eb.setDescription(shopItem.toString() + " has been purchased, " + k + " times for " + total + ".");
                    }
                    else {
                        eb.setColor(color.errorRed);
                        eb.setDescription(shopItem.toString() + " could not be purchased due to a lack of money.\nYour Balance: " + ecu.getCoins(event, event.getMember().getId()) + "\t Cost: " + pay + ".");
                    }
                }
                else if( args.length > 2 && args[2].equalsIgnoreCase("sell") && pay != 0){
                    //Fill
                }
            else { // NOT FULLY COMPLETE
                    event.getChannel().sendMessage(Data.getPrefix() + "args were improper").queue();
                //What about a VIP upgrade that affects ALL Vips?
                    eb.setDescription(event.getGuild().getName() + " \"test\" shop usage:\n**" +
                     Data.getPrefix() + "shop [itemID] [buy/sell] [amount]**\n NOTICE: SELL DOES NOT CURRENTLY WORK" +
                            "to learn more about an item, simply do: " + Data.getPrefix() + "shop [itemID]\n\n" +
                            "Usage\tName\tID\tCost\tCurrent\tMax\n" + // ADD PAGE TURNING SYSTEM
                            "Chest System\tBasic Chest\t1\tCOST\tCURRENT\tMAX\n");

                    for (int itemID = 1; itemID <= shopItems.countDocuments(); itemID++) { // For every item in the shop, it'll take the players current status, and the shop's current item
                        shopItem = shopItems.find(eq("ID", itemID)).first();
                        eb.setDescription(shopItem.getString("usage") + "\t" + shopItem.getString("name") + "\t" + shopItem.getInteger("ID") + "\t" + shopItem.getInteger("defaultPrice")/*Make function for calculation cost changes per lvl*/ + "\t" + member.get("item." + shopItem.getString("name")) + "\t" + shopItem.getInteger("max") + "\n");
                    }
                    eb.setColor(color.yellow);
                }
                Database.close();
                eb.setFooter(data.getSelfAvatar(event) + " Shop for: " + event.getGuild().getName());
                event.getChannel().sendMessage(eb.build()).queue();

        }
    }
}
/*
 * Shop.setDescription("How to use the " + event.getGuild().getName() + " shop"
 * + data.getPrefix() + "shop <Type> <ID> || <> means required\n **Types:**" +
 * "\nChest: Contains perks used for increasing luck and prizes in the ~chest command\n"
 * + "```\nID: 1 = Pharmacy (Increases coins earned)\n" +
 * "ID: 2 = Ace in the Hole (Increases XP Gained)\n" +
 * "ID: 3 = Plunder's Instinct (Increases amount of chests opened at a time)\n```"
 * +
 * "\nChase: Contains perks and boosters used for increasing luck and rewards in the ~chase command\n"
 * + "``ID: 1 = Quick and Quiet (Allows for extra mistakes)``\n" +
 * "``ID: 2 = Dead Hard (Increases XP Gain)``\n" +
 * "``ID: 3 = Decisive Strike(Increases Gold Gain)``"
 */
