package dev.dbdh.Discord.Listeners.Fun.ChestGame;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;

public class Chest extends ListenerAdapter {

    private static Random rng = new Random();
    final int TYPE_BAD = 0;
    final int TYPE_TOOLBOX = 1;
    final int TYPE_MEDKIT = 2;
    final int TYPE_FLASHLIGHT = 3;
    final int TYPE_MAP = 4;
    final int TYPE_KEY = 5;
    final int TYPE_EVENT = 6;
    final int TYPE_EPIC = 7;
    final int TYPE_LEGENDARY = 8;
    final int TYPE_MISC = 9;

    final int RARITY_BAD = 0;
    final int RARITY_BROWN = 1;
    final int RARITY_YELLOW = 2;
    final int RARITY_GREEN = 3;
    final int RARITY_PURPLE = 4;
    final int RARITY_PINK = 5;
    final int RARITY_EVENT = 6;
    final int RARITY_EPIC = 7;
    final int RARITY_LEGENDARY = 8;
    public static boolean isShiny;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        RoleCheck RC = new RoleCheck();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();

        List<Item> items = new ArrayList<>();
        List<Item> Bad = new ArrayList<>();
        List<Item> Useless = new ArrayList<>();
        List<Item> Common = new ArrayList<>();
        List<Item> UnCommon = new ArrayList<>();
        List<Item> Rare = new ArrayList<>();
        List<Item> VeryRare = new ArrayList<>();
        List<Item> UltraRare = new ArrayList<>();
        List<Item> Event = new ArrayList<>();
        List<Item> Epic = new ArrayList<>();
        List<Item> Legendary = new ArrayList<>();

        Bad.add(new Item("The chest was trapped", "https://cdn.discordapp.com/attachments/540754912011878400/632349969055088681/images.jpg", TYPE_BAD, RARITY_BAD, 0));
        Bad.add(new Item("Your Hand Got Stuck", "https://cdn.discordapp.com/attachments/540754912011878400/632349969055088681/images.jpg", TYPE_BAD, RARITY_BAD, 0));
        Bad.add(new Item("Pulled Off Chest", "https://cdn.discordapp.com/attachments/540754912011878400/632349969055088681/images.jpg", TYPE_BAD, RARITY_BAD, 0));

        Useless.add(new Item("Because the chest was empty!", "https://i.imgur.com/vVB7YDS.png", TYPE_MISC, RARITY_BAD, 0));
        Useless.add(new Item("But Meg started twerking!", "https://cdn.discordapp.com/attachments/635558839147823115/655948362570268673/itemmegtwerk.png", TYPE_MISC, RARITY_BAD, 0));
        Useless.add(new Item("but pictures of Jake!", "https://cdn.discordapp.com/attachments/635558839147823115/655954846192631808/itemjakepics.png", TYPE_MISC, RARITY_BAD, 0));

        Common.add(new Item("Worn-Out Tools", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/9/92/FulliconItems_worn-outTools.png", TYPE_TOOLBOX, RARITY_BROWN, 0));
        Common.add(new Item("Camping Aid Kit", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/6/62/FulliconItems_campingAidKit.png", TYPE_MEDKIT, RARITY_BROWN, 0));

        UnCommon.add(new Item("Toolbox", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/c/ce/FulliconItems_toolboxItem.png", TYPE_TOOLBOX, RARITY_YELLOW, 0));
        UnCommon.add(new Item("MedKit", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/9/97/FulliconItems_firstAidKit.png", TYPE_MEDKIT, RARITY_YELLOW, 0));
        UnCommon.add(new Item("Flashlight", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/7/76/FulliconItems_flashlightItem.png", TYPE_FLASHLIGHT, RARITY_YELLOW, rng.nextInt(10)));

        Rare.add(new Item("Mechanic's Toolbox", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/2/22/FulliconItems_mechanicsToolbox.png", TYPE_TOOLBOX, RARITY_GREEN, rng.nextInt(10)));
        Rare.add(new Item("Commodious Toolbox", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/6/6a/FulliconItems_commodiousToolbox.png", TYPE_TOOLBOX, RARITY_GREEN, 0));
        Rare.add(new Item("Map", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/2/26/FulliconItems_mapItem.png", TYPE_MAP, RARITY_GREEN, 0));
        Rare.add(new Item("Sport Flashlight", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/0/03/FulliconItems_sportFlashlight.png", TYPE_FLASHLIGHT, RARITY_GREEN, rng.nextInt(10) * -1));
        Rare.add(new Item("Broken Key", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/2/29/FulliconItems_brokenKey.png", TYPE_KEY, RARITY_GREEN, 0));
        Rare.add(new Item("Emergency MedKit", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/c/c9/FulliconItems_emergencyMed-Kit.png", TYPE_MEDKIT, RARITY_GREEN, 0));

        VeryRare.add(new Item("Engineer's Toolbox", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/7/7d/FulliconItems_engineersToolbox.png", TYPE_TOOLBOX, RARITY_PURPLE, 0));
        VeryRare.add(new Item("Alex's Toolbox", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/5/58/FulliconItems_alexsToolbox.png", TYPE_TOOLBOX, RARITY_PURPLE, rng.nextInt(10)));
        VeryRare.add(new Item("Ranger MedKit", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/e/ea/FulliconItems_rangerMed-Kit.png", TYPE_MEDKIT, RARITY_PURPLE, rng.nextInt(10) * -1));
        VeryRare.add(new Item("Dull Key", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/a/af/FulliconItems_dullKey.png", TYPE_KEY, RARITY_PURPLE, rng.nextInt(10) * -1));
        VeryRare.add(new Item("Utility Flashlight", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/2/2f/FulliconItems_utilityFlashlight.png", TYPE_FLASHLIGHT, RARITY_PURPLE, 0));

        UltraRare.add(new Item("Skeleton Key", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/5/5a/FulliconItems_skeletonKey.png", TYPE_KEY, RARITY_PINK, rng.nextInt(10) * -1));
        UltraRare.add(new Item("Rainbow Map", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/e/e3/FulliconItems_rainbowMap.png", TYPE_MAP, RARITY_PINK, rng.nextInt(5)));

        Event.add(new Item("Chinese Firecracker", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/1/15/FulliconItems_chineseFirecracker.png", TYPE_EVENT, RARITY_EVENT, rng.nextInt(10) * -1));
        Event.add(new Item("Winter Party Starter", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/d/d5/FulliconItems_winterPartyStarter.png", TYPE_EVENT, RARITY_EVENT, rng.nextInt(10) * -1));
        Event.add(new Item("New Year Party Popper", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/6/63/IconEventItem_partyPopper.png", TYPE_EVENT, RARITY_EVENT, rng.nextInt(10) * -1));
        Event.add(new Item("Will o' Wisp", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/0/03/FulliconItems_willOWisp.png", TYPE_EVENT, RARITY_EVENT, rng.nextInt(10) * -1));
        Event.add(new Item("All Hallows' eve Lunchbox", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/a/a3/FulliconItems_allHallowsEveLunchbox.png", TYPE_EVENT, RARITY_EVENT, rng.nextInt(10) * -1));

        Epic.add(new Item("Ally's Cat???", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/d/d5/FulliconItems_winterPartyStarter.png", TYPE_EPIC, RARITY_EPIC, rng.nextInt(20) * -1));
        Epic.add(new Item("[̶͖̬̺̪̌̓́͑͠ŕ̵̨͋̓ę̶͈͇͉͖̭̱̈́́̆͝d̵̞̈̑̕͝a̴̭̲̱̯͗́͂̉c̷̨̠̱͆͐̏̊͘t̷̛̮̗̬͗̈́̕͠ͅě̸̡̯̗͚̜̂͜͝ḋ̷̳̎ Images̫", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/6/63/IconEventItem_partyPopper.png", TYPE_EPIC, RARITY_EPIC, rng.nextInt(10) * -1));
        Epic.add(new Item("Diamond Encrusted Claudette Skulls", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/0/03/FulliconItems_willOWisp.png", TYPE_EPIC, RARITY_EPIC, rng.nextInt(10)));
        Epic.add(new Item("Call Me For A Good Time", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/a/a3/FulliconItems_allHallowsEveLunchbox.png", TYPE_EPIC, RARITY_EPIC, 0));

        Legendary.add(new Item("Swarm of Flamingos̫", "https://imgur.com/a/J6WUmnj", TYPE_LEGENDARY, RARITY_LEGENDARY, 0));
        Legendary.add(new Item("ExZiBytes Legendary", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/0/03/FulliconItems_willOWisp.png", TYPE_LEGENDARY, RARITY_LEGENDARY, 0));
        Legendary.add(new Item("Handsome Jack's Mask?", "https://gamepedia.cursecdn.com/deadbydaylight_gamepedia_en/a/a3/FulliconItems_allHallowsEveLunchbox.png", TYPE_LEGENDARY, RARITY_LEGENDARY, 0));

        if (args[0].equalsIgnoreCase(data.getPrefix() + "chest")) {
            Database.connect();
            if(ecu.isMemberInDB(event.getMember().getId())) {
                if (event.getMessage().getChannel().equals(event.getGuild().getTextChannelById("632350945891581992"))) {
                    if (args.length > 3) {
                        eb.setDescription("You need to be more specific these are the possible chests\n`Basic | Safety | Glitch | Shiny | Epic | Legendary | Godly`\n [Required Parameter] (Optional Parameter) !~chest [chestname] (amount defaults to 1)\n for a bio of each chest, do !~shop and find your chests ID\n" +
                                "chests owned:\n" +
                                "Basic Chests: " + ecu.getItemCount(event.getMember().getId(), "BASIC_CHEST") + " You may claim 1 for free every 5 minutes.\n" +
                                "Safety Chests: " + ecu.getItemCount(event.getMember().getId(), "SAFETY_CHEST") + "\n" +
                                "Glitch Chests: " + ecu.getItemCount(event.getMember().getId(), "GLITCH_CHEST") + "\n" +
                                "Shiny Chests: " + ecu.getItemCount(event.getMember().getId(), "SHINY_CHEST") + "\n" +
                                "Epic Chests: " + ecu.getItemCount(event.getMember().getId(), "EPIC_CHEST") + "\n" +
                                "Legendary Chests: " + ecu.getItemCount(event.getMember().getId(), "LEGENDARY_CHEST") + "\n" +
                                "Godly Chests: " + ecu.getItemCount(event.getMember().getId(), "GODLY_CHEST"));

                        eb.setColor(Color.errorRed);
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Insufficient Arguments -- Chest Game", data.getSelfAvatar(event));

                    } else {
                        int chestCount;
                        if(args.length == 1)
                            chestCount = ecu.getItemCount(event.getMember().getUser().getId(), "basic");
                        else
                            chestCount = ecu.getItemCount(event.getMember().getUser().getId(), args[1]); // The amount of chests the person has

                        if (args[1].equalsIgnoreCase("basic")) {
                            items.addAll(Bad);
                            items.addAll(Useless);
                            items.addAll(Common);
                            items.addAll(UnCommon);
                            items.addAll(Rare);
                            items.addAll(VeryRare);
                            items.addAll(UltraRare);
                            items.addAll(Event);
                            items.addAll(Epic);
                            items.addAll(Legendary);
                            if (chestCount == 0) {
                                if (ecu.isCooldownReady(event, event.getMember().getUser().getId(), "freeChest")) {
                                    eb.setDescription("You didn't have a basic chest available but your cooldown has ran out so here is a free chest");
                                    openChest(event, eb, items);
                                } else {
                                    eb.setDescription("You don't have a basic chest available and your cooldown is not ready yet.\nPlease wait: " + ecu.getCooldown(event.getMember().getId(), "freeBasicCooldown") + " or you can buy chests in the shop (!~shop)");
                                    eb.setColor(color.getRandomColor());
                                    eb.setTimestamp(Instant.now());
                                    eb.setFooter("Chest not available", Data.getSelfAvatar(event));
                                }
                            } else if (ecu.getItemCount(event.getMember().getUser().getId(), args[1]) >= 1) {
                                eb.setTimestamp(Instant.now());
                                eb.setDescription("You opened a basic chest");
                                eb.setFooter("Entity Chest Game | Free Basic Chests every 5 minutes !~chest or !~chest basic", Data.getSelfAvatar(event));
                            }

                        } else if (args[1].equalsIgnoreCase("glitch") || RC.isDeveloper(event) || RC.isOwner(event)) {
                            items.addAll(Bad);
                            items.addAll(Useless);
                            items.addAll(Common);
                            items.addAll(UnCommon);
                            items.addAll(Rare);
                            items.addAll(VeryRare);
                            items.addAll(UltraRare);
                            items.addAll(Event);
                            items.addAll(Epic);
                            openChest(event, eb, items);
                        } else if (args[1].equalsIgnoreCase("shiny") || RC.isDeveloper(event) || RC.isOwner(event)) {
                            items.addAll(Bad);
                            items.addAll(Useless);
                            items.addAll(Common);
                            items.addAll(UnCommon);
                            items.addAll(Rare);
                            items.addAll(VeryRare);
                            items.addAll(UltraRare);
                            items.addAll(Event);
                            items.addAll(Epic);
                            isShiny = true;
                            openChest(event, eb, items);
                        } else if (args[1].equalsIgnoreCase("epic") || RC.isDeveloper(event) || RC.isOwner(event)) {
                            items.addAll(Event);
                            items.addAll(Epic);
                            items.addAll(Legendary);
                            openChest(event, eb, items);
                        } else if (args[1].equalsIgnoreCase("legendary") || RC.isDeveloper(event) || RC.isOwner(event)) {
                            items.addAll(Epic);
                            items.addAll(Legendary);
                            openChest(event, eb, items);
                        }
                    }
                }
            }
            else
                {
                eb.setTitle("UH OH! DATABASE ERROR!");
                eb.setDescription("There was an error and we could not find you in our data base!\n We have now added you to the database... Sorry for the inconvenience! Please take this meme as compensation...");
                eb.setColor(Color.errorRed);
                eb.setImage("https://us.v-cdn.net/6030815/uploads/editor/n5/xf83dvvsxoh5.png");
                eb.setFooter("Please don't kill us... Love " + Data.getSelfName(event), Data.getSelfAvatar(event));
            }
            Database.close();
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    public static void openChest(GuildMessageReceivedEvent event, EmbedBuilder eb, List<Item> items) {
        List<Item> sortedItems = new ArrayList<>();
        Color c = new Color();
        boolean ItemFound;
        int pos;
        int GennedNum;
        int maxRange = 1;
        int minRange = 0;
        int count;
        //sorts the items
        for (Item item : items) {
            if (item.posOrNeg) {
                maxRange += item.drawChance;
            } else {
                minRange += item.drawChance;
            }
            if (!sortedItems.isEmpty()) { // If the list is not empty
                ItemFound = false;
                pos = 0;
                for (Item sI : sortedItems) {
                    if (item.drawChance < sI.drawChance) {
                        ItemFound = true;
                        break;
                    } // If it's smaller we break at the index and add it
                    else if (item.drawChance > sortedItems.get(sortedItems.size() - 1).drawChance) { // if it's bigger than the last index, we skip all the steps
                        break;
                    }
                    pos++;
                }
                if (ItemFound) sortedItems.add(pos, item);
                else sortedItems.add(item);
            } else {
                sortedItems.add(item);
            }
        }
        GennedNum = rng.nextInt(maxRange - minRange) + minRange;
        if(!isShiny)
            isShiny = GennedNum == rng.nextInt(maxRange);
        count = minRange;
        //Gets the range and spits out a random number
        for (Item sortedItem : sortedItems) {
            count += Math.abs(sortedItem.drawChance); // 9 + 8 + 4 + 6 + 11 + 12
            if (count >= GennedNum) { // -17 33
                eb.setColor(c.deepRed);
                if(isShiny && sortedItem.posOrNeg)
                {
                    eb.setColor(c.darkGreen);
                    sortedItem.goldGain *=2;
                    sortedItem.xpGain *= 2;
                    eb.appendDescription("\n\n***" + event.getAuthor().getAsMention() + " FOUND " + sortedItem.rarityString + "SHINY" + sortedItem.name + event.getAuthor().getAsMention() + " earned " + sortedItem.goldGain + "c and " + sortedItem.xpGain + "XP***");
                }
                if(sortedItem.posOrNeg)
                eb.setColor(c.darkGreen);
                eb.appendDescription("\n\n" + event.getAuthor().getAsMention() + " found " + sortedItem.rarityString + sortedItem.name + event.getAuthor().getAsMention() + " earned " + sortedItem.goldGain + "c and " + sortedItem.xpGain + "XP");
                eb.setImage(sortedItem.URL);
                event.getChannel().sendMessage(eb.build()).queue();
                break;
            }
        }
    }
}