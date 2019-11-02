package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EditCoins extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        RoleCheck rc = new RoleCheck();
        String[] addAliases = {"-a", "a", "add"};
        String[] removeAliases = {"-r", "r", "rm", "remove"};
        if (args[0].equalsIgnoreCase(data.getPrefix() + "coins")) {
            event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
            if (rc.isOwner(event) || rc.isDeveloper(event) || rc.isAdministrator(event)) {
                if (args.length < 4) {
                    eb.setDescription(event.getMember().getAsMention() + " you haven't specified enough arguments \n\n```\n" + data.getPrefix() + "coins <add/remove> <amount of coins to add/remove> <member>\n```");
                    eb.setColor(color.errorRed);
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Insufficient Arguments", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        eb.clear();
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                    });
                } else if (args.length > 3) {
                    if (Arrays.stream(removeAliases).anyMatch(args[1]::equals)) {
                        if (args[3].equalsIgnoreCase("everyone")) {
                            for (Member member : event.getGuild().getMembers()) {
                                if (!member.getUser().isBot()) {
                                    ecu.removeCoins(event, member.getUser().getId(), Integer.parseInt(args[2]));
                                }
                            }
                            eb.setDescription("Successfully removed " + args[2] + " coins from everyone's balance");
                            eb.setColor(color.getRandomColor());
                            eb.setTimestamp(Instant.now());
                            eb.setFooter("Entity Economy Update", data.getSelfAvatar(event));

                            success.setDescription(event.getMember().getAsMention() + " removed " + args[2] + " coins from everyone's balance");
                            success.setColor(color.getRandomColor());
                            success.setTimestamp(Instant.now());
                            success.setFooter("Entity Economy Logs");
                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                eb.clear();
                                data.getLogChannel(event).sendMessage(success.build()).queue();
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                            });
                        } else {
                            String name = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));
                            Member mentioned = event.getGuild().getMembersByName(name, true).get(0);
                            ecu.removeCoins(event, mentioned.getUser().getId(), Integer.parseInt(args[2]));
                            eb.setDescription("Successfully removed " + args[2] + " coins from " + mentioned.getAsMention() + "'s balance.\n\nNew balance: " + ecu.getCoins(event, mentioned.getUser().getId()));
                            eb.setColor(color.getRandomColor());
                            eb.setTimestamp(Instant.now());
                            eb.setFooter("Entity Economy Update", data.getSelfAvatar(event));
                            success.setDescription(event.getMember().getAsMention() + " removed " + args[2] + " coins from " + mentioned.getAsMention() + "'s balance.\n\nNew balance: " + ecu.getCoins(event, mentioned.getUser().getId()));
                            success.setColor(color.getRandomColor());
                            success.setTimestamp(Instant.now());
                            success.setFooter("Entity Economy Logs");
                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                eb.clear();
                                data.getLogChannel(event).sendMessage(success.build()).queue();
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                            });
                        }
                    } else if (Arrays.stream(addAliases).anyMatch(args[1]::equals)) {
                        if (args[3].equalsIgnoreCase("everyone")) {
                            for (Member member : event.getGuild().getMembers()) {
                                if (!member.getUser().isBot()) {
                                    ecu.addCoins(event, member.getUser().getId(), Integer.parseInt(args[2]));
                                }
                            }
                            eb.setDescription("Successfully added " + args[2] + " coins to everyone's balance");
                            eb.setColor(color.getRandomColor());
                            eb.setTimestamp(Instant.now());
                            eb.setFooter("Entity Economy Update", data.getSelfAvatar(event));

                            success.setDescription(event.getMember().getAsMention() + " added " + args[2] + " coins to everyone's balance");
                            success.setColor(color.getRandomColor());
                            success.setTimestamp(Instant.now());
                            success.setFooter("Entity Economy Logs");
                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                eb.clear();
                                data.getLogChannel(event).sendMessage(success.build()).queue();
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                            });
                        } else {
                            String name = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));
                            Member mentioned = event.getGuild().getMembersByName(name, true).get(0);
                            ecu.addCoins(event, mentioned.getUser().getId(), Integer.parseInt(args[2]));
                            eb.setDescription("Successfully added " + args[2] + " coins to " + mentioned.getAsMention() + "'s balance.\n\nNew balance: " + ecu.getCoins(event, mentioned.getUser().getId()));
                            eb.setColor(color.getRandomColor());
                            eb.setTimestamp(Instant.now());
                            eb.setFooter("Entity Economy Update", data.getSelfAvatar(event));

                            success.setDescription(event.getMember().getAsMention() + " added " + args[2] + " coins to " + mentioned.getAsMention() + "'s balance.\n\nNew balance: " + ecu.getCoins(event, mentioned.getUser().getId()));
                            success.setColor(color.getRandomColor());
                            success.setTimestamp(Instant.now());
                            success.setFooter("Entity Economy Logs");
                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                eb.clear();
                                data.getLogChannel(event).sendMessage(success.build()).queue();
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                            });
                        }
                    }
                }
            }  else {
                eb.setDescription(event.getMember().getAsMention() + " you don't have sufficient permissions to edit the balance of members");
                eb.setColor(color.errorRed);
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Insufficient Permissions", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    eb.clear();
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                });
            }
        }
    }
}