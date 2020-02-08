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
                                "Basic Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "basic",1) + " You may claim 1 for free every 5 minutes.\n" +
                                "Safety Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "safety",1) + "\n" +
                                "Glitch Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "glitch",1) + "\n" +
                                "Shiny Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "shiny",1) + "\n" +
                                "Epic Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "epic",1) + "\n" +
                                "Legendary Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "legendary",1) + "\n" +
                                "Godly Chests: " + ecu.getItemCount(event.getMember().getUser().getId(), "godly",1));

                        eb.setColor(Color.errorRed);
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Insufficient Arguments -- Chest Game", data.getSelfAvatar(event));

                    } else {
                        int chestCount;
                        String item;
                        //Segment determines which chest to retrieve, if only !~chest has been specified, it will ALWAYS return a basic chest
                        if (args.length == 1){
                            chestCount = ecu.getItemCount(event.getMember().getUser().getId(), "basic", 1);
                            item = "BASIC";
                        }
                        else {
                            item = args[1];
                            chestCount = ecu.getItemCount(event.getMember().getUser().getId(), args[1], 1); // The amount of chests the person has
                        }


                        if (item.equalsIgnoreCase("basic") || RC.isDeveloper(event) || RC.isOwner(event)) {
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
                                if (ecu.isCooldownReady(event, event.getMember().getUser().getId(), "freeChest")) {
                                    eb.setDescription("You have redeemed your free chest!");
                                    ecu.openChest(event, eb, items, true, "basic", 10, false);
                                } else {
                                    eb.setDescription("You don't have a basic chest available and your cooldown is not ready yet.\nPlease wait: " + ecu.getCooldown(event.getMember().getId(), "freeBasicCooldown") + " or you can buy chests in the shop (!~shop)");
                                    eb.setColor(Color.red);
                                }
                            } else{ //Assumed to always be true
                                ecu.openChest(event, eb, items, false,"basic", 10, false);
                                eb.setDescription("You opened a basic chest! Remaining amount: " + (chestCount - 1));
                            }
                        } else if (item.equalsIgnoreCase("safety") || RC.isDeveloper(event) || RC.isOwner(event) && chestCount >= 1) {
                            items.addAll(Common);
                            items.addAll(UnCommon);
                            items.addAll(Rare);
                            items.addAll(VeryRare);
                            items.addAll(UltraRare);
                            items.addAll(Event);
                            ecu.openChest(event, eb, items,false, "safety", 10, false);
                            eb.setDescription("You opened a safety chest! Remaining amount: " + (chestCount - 1));
                        }
                         else if (item.equalsIgnoreCase("glitch") || RC.isDeveloper(event) || RC.isOwner(event)&& chestCount >= 1) {
                            items.addAll(Bad);
                            items.addAll(Useless);
                            items.addAll(Common);
                            items.addAll(UnCommon);
                            items.addAll(Rare);
                            items.addAll(VeryRare);
                            items.addAll(UltraRare);
                            items.addAll(Event);
                            items.addAll(Epic);
                            ecu.openChest(event, eb, items, false, "glitch", 60, false);
                            eb.setDescription("You opened a glitch chest! Remaining amount: " + (chestCount - 1));
                        } else if (item.equalsIgnoreCase("shiny") || RC.isDeveloper(event) || RC.isOwner(event)&& chestCount >= 1) {
                            items.addAll(Bad);
                            items.addAll(Useless);
                            items.addAll(Common);
                            items.addAll(UnCommon);
                            items.addAll(Rare);
                            items.addAll(VeryRare);
                            items.addAll(UltraRare);
                            items.addAll(Event);
                            items.addAll(Epic);
                            ecu.openChest(event, eb, items, false, "shiny", 20, true);
                            eb.setDescription("You opened a shiny chest! Remaining amount: " + (chestCount - 1));
                        } else if (item.equalsIgnoreCase("epic") || RC.isDeveloper(event) || RC.isOwner(event)&& chestCount >= 1) {
                            items.addAll(Event);
                            items.addAll(Epic);
                            items.addAll(Legendary);
                            ecu.openChest(event, eb, items, false, "epic", 15, false);
                            eb.setDescription("You opened an epic chest! Remaining amount: " + (chestCount - 1));
                        } else if (item.equalsIgnoreCase("legendary") || RC.isDeveloper(event) || RC.isOwner(event)&& chestCount >= 1) {
                            items.addAll(Epic);
                            items.addAll(Legendary);
                            ecu.openChest(event, eb, items, false,"legendary", 30, false);
                            eb.setDescription("You opened a legendary chest! Remaining amount: " + (chestCount - 1));
                        }
                    else if (item.equalsIgnoreCase("godly") || RC.isDeveloper(event) || RC.isOwner(event)&& chestCount >= 1) {
                        items.addAll(Legendary);
                        ecu.openChest(event, eb, items, false, "godly", 40, true);
                        eb.setDescription("You opened a godly chest! Remaining amount: " + (chestCount - 1));
                    }
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Chest Game | Free Basic Chests every 5 minutes !~chest or !~chest basic", Data.getSelfAvatar(event));
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

}