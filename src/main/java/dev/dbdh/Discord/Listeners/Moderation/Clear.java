package dev.dbdh.Discord.Listeners.Moderation;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Clear extends ListenerAdapter {

    RoleCheck rc = new RoleCheck();
    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "clear")) {
            if (rc.isOwner(event) || rc.isDeveloper(event) || rc.isAdministrator(event) || rc.isHeadModerator(event) || rc.isModerator(event)){
                if (args.length < 2) {
                    eb.setDescription("You didn't specify enough arguments");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length == 2) {
                    try {
                        Integer messageCount = Integer.parseInt(args[1]);
                        if (messageCount < 2) {
                            eb.setDescription("Too few messages to delete. Minimum amount of messages I can delete is 2");
                            eb.setColor(color.getRandomColor());
                            eb.setFooter(event.getJDA().getSelfUser().getName() + ", Too Few Messages to Delete", data.getSelfAvatar(event));
                            eb.setTimestamp(Instant.now());

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                                event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                                eb.clear();
                            });
                        } else if (messageCount > 100) {
                            eb.setDescription("Too many messages to delete. Maximum amount of messages I can delete at a time is 100");
                            eb.setColor(color.getRandomColor());
                            eb.setFooter(event.getJDA().getSelfUser().getName() + ", Too Many Messages to Delete", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
                            eb.setTimestamp(Instant.now());

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                                event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                                eb.clear();
                            });
                        } else {
                            List<Message> messages = event.getChannel().getHistory().retrievePast(messageCount).complete();
                            event.getChannel().deleteMessages(messages).queue();

                            eb.setDescription("Deleted " + messageCount.toString() + " messages from " + event.getChannel().getAsMention());
                            eb.setColor(color.getRandomColor());
                            eb.setFooter(event.getJDA().getSelfUser().getName() + ", Clear Messages", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
                            eb.setTimestamp(Instant.now());

                            success.setDescription(event.getMember().getAsMention() + " deleted " + messageCount.toString() + " messages from: " + event.getChannel().getAsMention());
                            success.setColor(color.getRandomColor());
                            success.setFooter(event.getJDA().getSelfUser().getName() + ", Clear Messages", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
                            success.setTimestamp(Instant.now());

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(15, TimeUnit.SECONDS);
                                event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                                data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                                    success.clear();
                                });
                                eb.clear();
                            });
                        }
                    } catch (NumberFormatException nfe) {
                        eb.setDescription("`" + args[1] + "` is not a valid number. Please input a number between 2 and 100");
                        eb.setColor(color.getRandomColor());
                        eb.setFooter(event.getJDA().getSelfUser().getName() + ", Not a valid number", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
                        eb.setTimestamp(Instant.now());

                        event.getChannel().sendMessage(eb.build()).queue((message) -> {
                            message.delete().queueAfter(15, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                            eb.clear();
                        });
                    }

                }
            } else {
                eb.setDescription(event.getMember().getAsMention()
                        + ", You dont have the permission to manage messages for this guild.");
                eb.setColor(color.getRandomColor());
                eb.setFooter("Insufficient Permissions", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
                eb.setTimestamp(Instant.now());
                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                    eb.clear();
                });
            }
        }
    }

    public String getName() {
        return "Clear";
    }

    public String getDescription() {
        return "Clears the specified amount of messages 2 - 100 messages at a time";
    }

    public String getShortDescription() {
        return "Clears messages from a channel";
    }

    public String getRequiredRoles() {
        return "Owner, Developer, Administrator, Moderator";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "clear {2 - 100}\n```";
    }

    public boolean isDisabled() {
        return false;
    }
}
