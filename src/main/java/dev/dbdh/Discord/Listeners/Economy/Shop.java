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
        int loops = 0;
        String itemName;
        EmbedBuilder eb = new EmbedBuilder();

        //(prefix)shop [item] [buy/sell] [amount]
        if (Arrays.stream(al.shopAliases).anyMatch(args[0]::equalsIgnoreCase)) {
            Database.connect();
            int userNum = 0;
            MongoCollection<Document> shopItems = Database.getCollection("shopItems");
            Document shopItem;
            MongoCollection<Document> members = Database.getCollection("members");
            Document member = members.find(eq("memberId", event.getMember().getId())).first();
            Document itemsDoc = (Document) member.get("items");
            event.getChannel().sendMessage("went through").queue();
            long shopIndexes = shopItems.countDocuments();
            switch (args.length) {
                case 1:
                    eb.setTitle(event.getGuild().getName() + " shop");
                    eb.setDescription("Welcome to the " + event.getGuild().getName() + " shop!");
                    for (int i = 1; i <= shopIndexes; i++) {
                        shopItem = shopItems.find(eq("ID", i)).first();
                        itemName = shopItem.getString("name");
                        eb.appendDescription("\nID: " + i + " | Item: " + itemName + " | current level: " + itemsDoc.getInteger(itemName));
                    }
                    eb.setColor(Color.forestGreen);
                    eb.setFooter("Fatal error unknown item ~ " + Data.getSelfName(event), Data.getSelfAvatar(event));
                    break;
                case 2:
                    userNum = Integer.parseInt(args[1]);
                    if (userNum <= shopIndexes && userNum > 0) {
                        shopItem = shopItems.find(eq("ID", userNum)).first();
                        itemName = shopItem.getString("name");
                        //eb.setImage(Images custom Icon);
                        eb.setTitle(event.getGuild().getName() + " shop - " + itemName + " : " + shopItem.getInteger("ID"));
                        pay = (int) Math.pow(shopItem.getInteger("defaultPrice"), itemsDoc.getInteger(itemName) + 1); // divided by level?
                        eb.setDescription(shopItem.getString("description") + "\nCost: " + pay + "c");
                        eb.setColor(Color.darkSlateBlue);
                        eb.setFooter("Fatal error unknown item ~ " + Data.getSelfName(event), Data.getSelfAvatar(event));
                    } else {
                        eb.setTitle("__ERROR UNKNOWN ITEM ID__: " + args[1]);
                        eb.setDescription("Please enter a valid item, for a list of available items type: " + Data.getPrefix() + "shop. This server has: " + shopIndexes + " shop items.");
                        eb.setFooter("Fatal error unknown item ~ " + Data.getSelfName(event), Data.getSelfAvatar(event));
                        eb.setColor(Color.errorRed);
                    }
                    break;
                case 4:
                    userNum = Integer.parseInt(args[1]);
                    loops = Integer.parseInt(args[3]);
                case 3:
                    shopItem = shopItems.find(eq("ID", userNum)).first();
                    itemName = shopItem.getString("name");
                    long prvBal = member.getLong("balance");
                    long newBal;
                    int max;
                    int loopsCompleted = 0;
                    if (args[2].equalsIgnoreCase("buy")) {
                        max = shopItem.getInteger("max") - itemsDoc.getInteger(itemName);
                        if (loops >= max)  // checks to see if the entered number is too big
                            loops = max;
                        long balance;
                        for (; loopsCompleted < loops; loopsCompleted++) {
                            balance = member.getLong("balance");
                            pay = (int) Math.pow(shopItem.getInteger("defaultPrice"), itemsDoc.getInteger(itemName) + 1); //plus 1 is so we know if they can afford next level
                            if (balance >= pay) {
                                ecu.editCoins(event.getMember().getId(), -pay);
                                ecu.editItem(event.getMember().getId(), itemName, 1);
                            } else
                                break;
                        }
                        eb.setTitle(itemName + " purchased: " + loopsCompleted + " successfully!");
                        eb.setDescription("You purchased: " + loopsCompleted + "x " + itemName + "!");
                    } else if (args[2].equalsIgnoreCase("sell")) {
                        max = itemsDoc.getInteger(itemName);
                        if(loops > max)
                            loops = max;
                        for (; loopsCompleted < loops; loopsCompleted++) {
                            pay = (int) Math.pow(shopItem.getInteger("defaultPrice"), itemsDoc.getInteger(itemName)) * 3 / 4; // No +1 so the number can be zero,
                            ecu.editCoins(event.getMember().getId(), pay);
                            ecu.editItem(event.getMember().getId(), itemName, -1);
                        }
                        eb.setTitle(itemName + " sold: " + loopsCompleted + " successfully!");
                        eb.setDescription("You sold: " + loopsCompleted + "x " + itemName + "!");
                    }
                    newBal = member.getLong("balance");
                    eb.appendDescription("\nYour **previous balance was: " + prvBal + "**\nYour **new balance is: " + newBal + "**\n__Reasons for early termination__\n1. You hit the max or minimum limit\n2. You could not afford the upgrade\n3. an internal error occurred");
                    eb.setColor(Color.darkGreen);
                    eb.setFooter("Thank you for using the " + event.getGuild().getName() + " shop! Please report any errors to: " + event.getGuild().getOwner().getAsMention());
                    break;
            }
            Database.close();
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
