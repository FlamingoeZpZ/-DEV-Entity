package dev.dbdh.Discord.Listeners.Moderation;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Lines 42 and 72 need special attention in this class

public class Softban extends ListenerAdapter {
    Data data = new Data();
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder banned = new EmbedBuilder();

        if (args[0].equalsIgnoreCase(data.getPrefix() + "softban")) {
            if (rc.isOwner(event) || rc.isDeveloper(event)) {
                if (args.length < 2) {
                    eb.setDescription("You didn't specify enough arguments \n" + data.getPrefix() + "softban @<member>");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length < 3) {
                    Invite inviteLink = event.getGuild().getTextChannelById("540943108142268426").createInvite().setMaxUses(1).setMaxAge(null).complete();
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);

                    banned.setDescription("You've been softbanned from: " + event.getGuild().getName()
                            + "\n\n Reason:\n```\nThere was no reason specified\n```\nHere is an invite link to get back on the server: \n[" + event.getGuild().getName() + " Invite](https://discord.gg/" + inviteLink.toString().replace("Invite(", "").replace(")", "") + " \"Invite Link for " + event.getGuild().getName() + "\")");
                    banned.setColor(color.getRandomColor());
                    banned.setFooter(event.getJDA().getSelfUser().getName() + " Softban",
                            data.getSelfAvatar(event));
                    banned.setTimestamp(Instant.now());

                    eb.setDescription("You've softbanned: " + mentioned.getAsMention() + " \n\nReason: \n```\nNo reason specified\n```");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter(event.getJDA().getSelfUser().getName() + " Softban",
                            data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    mentioned.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(banned.build()).queue();
                        banned.clear();

                        event.getChannel().sendMessage(eb.build()).queue((message) -> {
                            message.delete().queueAfter(20, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                            eb.clear();
                            event.getGuild().ban(mentioned, 7, "No Reason Specified").queue();
                            event.getGuild().unban(mentioned.getUser().getId()).queueAfter(2, TimeUnit.SECONDS);
                        });
                    });

                } else {
                    Invite inviteLink = event.getGuild().getTextChannelById("540943108142268426").createInvite().setMaxUses(1).setMaxAge(null).complete();
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));

                    banned.setDescription("You've been softbanned from: " + event.getGuild().getName() + " \n\nReason: \n```\n" + reason + "\n```\nHere is an invite link to get back on the server: \n[" + event.getGuild().getName().toString() + " Link](https://discord.gg/" + inviteLink.toString().replace("Invite(", "").replace(")", "") + " \"Invite Link for " + event.getGuild().getName() + "\")");
                    banned.setColor(color.getRandomColor());
                    banned.setFooter(event.getJDA().getSelfUser().getName() + " Softbanned",
                            data.getSelfAvatar(event));
                    banned.setTimestamp(Instant.now());

                    eb.setDescription("You've softbanned: " + mentioned.getAsMention() + " \n\nReason:\n```\n" + reason + "\n```");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter(event.getJDA().getSelfUser().getName() + " Softban",
                            data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    mentioned.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(banned.build()).queue();
                        banned.clear();

                        event.getChannel().sendMessage(eb.build()).queue((message) -> {
                            message.delete().queueAfter(20, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                            eb.clear();
                            event.getGuild().ban(mentioned, 7, reason).queue();
                            event.getGuild().unban(mentioned.getUser().getId()).queueAfter(2, TimeUnit.SECONDS);
                        });
                    });

                }
            } else {
                eb.setDescription(event.getMember().getAsMention()
                        + ", You don't have the permission to ban members from this guild.");
                eb.setColor(color.getRandomColor());
                eb.setFooter("Insufficient Permissions", data.getSelfAvatar(event));
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
        return "Softban";
    }

    public String getDescription() {
        return "Bans the specified member to delete messages from the member then sends them a invite link to rejoin the server. If no reason is specified then the member is banned for \"No Reason Specified\"";
    }

    public String getShortDescription() {
        return "Deletes messages from a member";
    }

    public String getRequiredRoles() {
        return "Owner, Developer, Administrator";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "softban {@member} [reason]\n```";
    }

    public boolean isDisabled() {
        return false;
    }
}
