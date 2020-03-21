package dev.dbdh.Discord.Listeners.Fun.ChestGame;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Data data = new Data();
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
        
        if (args[0].equalsIgnoreCase(Data.getPrefix() + "chest")) {
            if (ecu.isMemberInDB(event.getMember().getId())) {
                if (event.getMessage().getChannel().equals(event.getGuild().getTextChannelById("632350945891581992"))) {
                    if(args.length > 2) // Added to remove issue w/ java where code in an if statement is all considered instead of finding 1 false and immediately being false
                    if (args.length > 3 || args[1].equalsIgnoreCase("help") || args[1].equalsIgnoreCase("inv") || args[1].equalsIgnoreCase("inventory") ) {
                        eb.setDescription("**You need to be more specific. These are the possible chests with usage:**\n`Basic | Safety | Glitch | Shiny | Epic | Legendary | Godly`\n [Required Parameters] (Optional Parameters)\n" + Data.getPrefix() + "chest [chestname] (amount defaults to 1)\n for a bio of each chest, do " + Data.getPrefix() + "shop and find your chests ID\n" +
                                "chests owned:\n\n" +
                                ">**Basic Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "BASIC_CHEST") + " (You may claim 1 for free every 5 minutes.)\n" +
                                ">Safety Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "SAFETY_CHEST") + "\n" +
                                ">Glitch Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "GLITCH_CHEST") + "\n" +
                                ">Shiny Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "SHINY_CHEST") + "\n" +
                                ">Epic Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "EPIC_CHEST") + "\n" +
                                ">Legendary Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "LEGENDARY_CHEST") + "\n" +
                                ">Godly Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "GODLY_CHEST") + "**");

                        eb.setColor(Color.errorRed);
                        eb.setTimestamp(Instant.now());
                        eb.setFooter(data.RandomTip());

                    } else {
                        int chestCount;
                        String item;
                        //Segment determines which chest to retrieve, if only !~chest has been specified, it will ALWAYS return a basic chest
                        if (args.length == 1) {
                            chestCount = ecu.getItemCount(event.getMember().getUser().getId(), "BASIC_CHEST");
                            item = "BASIC";
                        } else {
                            try {
                                item = args[1].toUpperCase();
                                chestCount = ecu.getItemCount(event.getMember().getUser().getId(), item + "_CHEST"); // The amount of chests the person has
                            }
                            catch (Exception E){
                                item = "null";
                                chestCount = 0;
                            }
                        }
                        if (chestCount > 0 || item.equals("BASIC") && args[0].equalsIgnoreCase(Data.getPrefix() + "chest")) {
                            switch (item) {
                                case "BASIC":
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
                                        //Make the free chest a random chest between like 5 different options w/ exponential odds
                                        if (ecu.isCooldownReady(event.getMember().getUser().getId(), "freeChest")) {
                                            ecu.resetCooldown(event, event.getMember().getUser().getId(), "daily");
                                            eb.setDescription("You have redeemed your free chest!");
                                            ecu.openChest(event, eb, items, true, "basic", 10, false);
                                        } else {
                                            eb.setDescription("You don't have a basic chest available and your cooldown is not ready yet.\nPlease wait: " + data.intToTime(ecu.getCooldown(event.getMember().getId(), "freeBasicCooldown")) + " or you can buy chests in the shop: " + Data.getPrefix() + "shop)");
                                            eb.setColor(Color.red);
                                        }
                                    } else { //Assumed to always be true
                                        eb.setDescription("You opened a basic chest! Remaining amount: " + (chestCount - 1));
                                        ecu.openChest(event, eb, items, false, "basic", 10, false);
                                    }
                                    break;
                                case "SAFETY":
                                    items.addAll(Common);
                                    items.addAll(UnCommon);
                                    items.addAll(Rare);
                                    items.addAll(VeryRare);
                                    items.addAll(UltraRare);
                                    items.addAll(Event);
                                    eb.setDescription("You opened a safety chest! Remaining amount: " + (chestCount - 1));
                                    ecu.openChest(event, eb, items, false, "safety", 10, false);
                                    break;
                                case "GLITCH":
                                    items.addAll(Bad);
                                    items.addAll(Useless);
                                    items.addAll(Common);
                                    items.addAll(UnCommon);
                                    items.addAll(Rare);
                                    items.addAll(VeryRare);
                                    items.addAll(UltraRare);
                                    items.addAll(Event);
                                    items.addAll(Epic);
                                    eb.setDescription("You opened a glitch chest! Remaining amount: " + (chestCount - 1));
                                    ecu.openChest(event, eb, items, false, "glitch", 60, false);
                                    break;
                                case "SHINY":
                                    items.addAll(Bad);
                                    items.addAll(Useless);
                                    items.addAll(Common);
                                    items.addAll(UnCommon);
                                    items.addAll(Rare);
                                    items.addAll(VeryRare);
                                    items.addAll(UltraRare);
                                    items.addAll(Event);
                                    items.addAll(Epic);
                                    eb.setDescription("You opened a shiny chest! Remaining amount: " + (chestCount - 1));
                                    ecu.openChest(event, eb, items, false, "shiny", 20, true);
                                    break;
                                case "EPIC":
                                    items.addAll(Event);
                                    items.addAll(Epic);
                                    items.addAll(Legendary);
                                    eb.setDescription("You opened an epic chest! Remaining amount: " + (chestCount - 1));
                                    ecu.openChest(event, eb, items, false, "epic", 15, false);
                                    break;
                                case "LEGENDARY":
                                    items.addAll(Epic);
                                    items.addAll(Legendary);
                                    eb.setDescription("You opened a legendary chest! Remaining amount: " + (chestCount - 1));
                                    ecu.openChest(event, eb, items, false, "legendary", 30, false);
                                    break;
                                case "GODLY":
                                    items.addAll(Legendary);
                                    eb.setDescription("You opened a godly chest! Remaining amount: " + (chestCount - 1));

                                    ecu.openChest(event, eb, items, false, "godly", 40, true);
                                    break;
                                case "CRATE": // Exception
                                    Database.connect();
                                    String chestGained = "";
                                    int randomOpensRNG = rng.nextInt(1000);
                                    int [] CG = {0,0,0,0,0,0,0};
                                    int loops = 0;
                                    if (randomOpensRNG < 300)
                                        loops = 1;
                                    else if (randomOpensRNG < 500)
                                        loops = 2;
                                    else if (randomOpensRNG < 700)
                                        loops = 3;
                                    else if (randomOpensRNG < 900)
                                        loops = 4;
                                    else if (randomOpensRNG < 975)
                                        loops = 5;
                                    else if (randomOpensRNG < 999)
                                        loops = 10;
                                    else
                                        loops = 25;
                                    eb.setTitle("Opening Chest Crate! " + event.getGuild().getEmoteById("573235478711500837").getAsMention());
                                    eb.setDescription("You found :"+ loops + " inside this crate!");
                                    for (; loops > 0; loops--) {
                                        int randomChestRNG = rng.nextInt(100);
                                        if (randomChestRNG < 30) {
                                            chestGained = "BASIC_CHEST";
                                            CG[0]++;
                                        }
                                        else if (randomChestRNG < 55) {
                                            chestGained = "SAFETY_CHEST";
                                            CG[1]++;
                                        }
                                        else if (randomChestRNG < 75){
                                            chestGained = "GLITCH_CHEST";
                                            CG[2]++;
                                        }
                                        else if (randomChestRNG < 90) {
                                            chestGained = "SHINY_CHEST";
                                            CG[3]++;
                                        }
                                        else if (randomChestRNG < 96){
                                            chestGained = "EPIC_CHEST";
                                            CG[4]++;
                                        }
                                        else if (randomChestRNG < 99) {
                                            chestGained = "LEGENDARY_CHEST";
                                            CG[5]++;
                                        }
                                        else {
                                            chestGained = "GODLY_CHEST";
                                            CG[6]++;
                                        }
                                        ecu.editItem(event.getMember().getId(), chestGained, 1);
                                    }
                                    ecu.editItem(event.getMember().getId(), "CRATE_CHEST", -1);
                                    ecu.editHistoryItem(event.getMember().getId(), "CRATE_CHEST", 1);
                                    Database.close();
                                    eb.appendDescription("\n__Chests gained:__\nBasic: " + CG[0] + "\nSafety: " + CG[1] + "\nGlitch: " + CG[2] + "\nShiny: " + CG[3] + "\nEpic: " + CG[4] + "\nLegendary: " + CG[5] + "\nGodly: " + CG[6]);
                                    eb.setColor(Color.successGreen);
                                    eb.setFooter(data.RandomTip(), Data.getSelfAvatar(event));
                                    event.getChannel().sendMessage(eb.build()).queue();
                                    break;
                                default:
                                    eb.setDescription("**You need to be more specific. These are the possible chests with usage:**\n`Basic | Safety | Glitch | Shiny | Epic | Legendary | Godly`\n [Required Parameters] (Optional Parameters)\n" + Data.getPrefix() + "chest [chestname] (amount defaults to 1)\n for a bio of each chest, do " + Data.getPrefix() + "shop and find your chests ID\n" +
                                            "chests owned:\n\n" +
                                            "**>Basic Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "BASIC_CHEST") + " (You may claim 1 for free every 5 minutes.)\n" +
                                            ">Safety Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "SAFETY_CHEST") + "\n" +
                                            ">Glitch Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "GLITCH_CHEST") + "\n" +
                                            ">Shiny Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "SHINY_CHEST") + "\n" +
                                            ">Epic Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "EPIC_CHEST") + "\n" +
                                            ">Legendary Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "LEGENDARY_CHEST") + "\n" +
                                            ">Godly Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "GODLY_CHEST" + "**"));
                                    eb.setColor(Color.errorRed);
                                    eb.setTimestamp(Instant.now());
                                    eb.setFooter(data.RandomTip(), Data.getSelfAvatar(event));
                                    event.getChannel().sendMessage(eb.build()).queue();
                                    break;
                            }
                        } else {
                            if(item.equals("null"))
                                eb.setDescription("Please select an item that actually exists.");
                            else
                                eb.setDescription("You didn't have enough of " + args[1] + " chests!");
                            eb.setTimestamp(Instant.now());
                            eb.setFooter(data.RandomTip(), Data.getSelfAvatar(event));
                            eb.setColor(Color.errorRed);
                            event.getChannel().sendMessage(eb.build()).queue();
                        }
                    }
                }
            } else {
                eb.setTitle("UH OH! DATABASE ERROR!");
                eb.setDescription("There was an error and we could not find you in our data base!\n We have now added you to the database... Sorry for the inconvenience! Please take this meme as compensation...");
                eb.setColor(Color.errorRed);
                eb.setImage("https://us.v-cdn.net/6030815/uploads/editor/n5/xf83dvvsxoh5.png");
                eb.setFooter("Please don't kill us... Love " + Data.getSelfName(event), Data.getSelfAvatar(event));
                event.getChannel().sendMessage(eb.build()).queue();
            }
        }
    }

}