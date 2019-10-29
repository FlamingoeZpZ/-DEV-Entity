package dev.dbdh.Discord.Listeners.Moderation;

import java.time.Instant;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.RoleCheck;
import dev.dbdh.Discord.Utilities.RoleCreate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Mute extends ListenerAdapter {

    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        RoleCheck rc = new RoleCheck();
        RoleCreate create = new RoleCreate();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder muted = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();

        if (args[0].equalsIgnoreCase(data.getPrefix() + "mute")) {
            if (rc.isOwner(event) || rc.isDeveloper(event) || rc.isAdministrator(event) || rc.isHeadModerator(event) || rc.isModerator(event)){
                if (args.length < 2) {
                    eb.setDescription("You didn't specify enough arguments \n" + data.getPrefix() + "mute @<member>");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length < 3) {

                    List<Role> roles = event.getGuild().getRolesByName("Muted", true);

                    if (roles.size() < 1) {

                        create.createMutedRole(event);

                        Member mentioned = event.getMessage().getMentionedMembers().get(0);
                        // Build Information Embed to be sent to muted user
                        muted.setDescription("You've been muted on: " + event.getGuild().getName()
                                + "\n\nReason: \n```\nThere was no reason specified\n```");
                        muted.setColor(color.getRandomColor());
                        muted.setFooter(event.getJDA().getSelfUser().getName() + " Muted",
                                data.getSelfAvatar(event));
                        muted.setTimestamp(Instant.now());

                        // Build Information Embed to be sent to server channel
                        eb.setDescription("You've muted: " + mentioned.getAsMention() + "\n\nReason:\n```\nNo reason specified\n```");
                        eb.setColor(color.getRandomColor());
                        eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                data.getSelfAvatar(event));
                        eb.setTimestamp(Instant.now());

                        success.setDescription(event.getMember().getAsMention() + " muted " + mentioned.getAsMention() + "\n\nReason: \n```No reason specified\n```");
                        success.setColor(color.getRandomColor());
                        success.setFooter(event.getJDA().getSelfUser().getName() + " Mute", data.getSelfAvatar(event));
                        success.setTimestamp(Instant.now());

                        event.getGuild().addRoleToMember(mentioned, event.getGuild().getRolesByName("Muted", true).get(0)).queue();
                        mentioned.getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessage(muted.build()).queue();
                            muted.clear();

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(20, TimeUnit.SECONDS);
                                event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                                eb.clear();
                                data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                                    success.clear();
                                });
                            });
                        });
                    } else {

                        Role muteRole = event.getGuild().getRolesByName("Muted", true).get(0);

                        for (TextChannel channel : event.getGuild().getTextChannels()) {
                            if(!channel.getParent().getId().equals("579392397189054465")){
                                channel.getManager().putPermissionOverride(muteRole, EnumSet.of(Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ), EnumSet.of(Permission.MESSAGE_WRITE)).queue();
                            }
                        }
                        Member mentioned = event.getMessage().getMentionedMembers().get(0);
                        if (!mentioned.getRoles().contains(event.getGuild().getRoleById(muteRole.getId()))) {

                            // Build Information Embed to be sent to kicked user
                            muted.setDescription("You've been muted on: " + event.getGuild().getName()
                                    + "\n\nReason: \n```\nThere was no reason specified\n```");
                            muted.setColor(color.getRandomColor());
                            muted.setFooter(event.getJDA().getSelfUser().getName() + " Muted",
                                    data.getSelfAvatar(event));
                            muted.setTimestamp(Instant.now());

                            // Build Information Embed to be to server channel
                            eb.setDescription(
                                    "You've muted: " + mentioned.getAsMention() + "\n\nReason:\n```\nNo reason specified\n```");
                            eb.setColor(color.getRandomColor());
                            eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                    data.getSelfAvatar(event));
                            eb.setTimestamp(Instant.now());

                            success.setDescription(event.getMember().getAsMention() + " muted " + mentioned.getAsMention() + "\n\nReason: \n```No reason specified\n```");
                            success.setColor(color.getRandomColor());
                            success.setFooter(event.getJDA().getSelfUser().getName() + " Mute", data.getSelfAvatar(event));
                            success.setTimestamp(Instant.now());
                            event.getGuild().addRoleToMember(mentioned, muteRole).queue();
                            mentioned.getUser().openPrivateChannel().queue((channel) -> {
                                channel.sendMessage(muted.build()).queue();
                                muted.clear();

                                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                    message.delete().queueAfter(20, TimeUnit.SECONDS);
                                    event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                                    eb.clear();
                                    data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                                        success.clear();
                                    });
                                });
                            });
                        } else {
                            eb.setDescription(mentioned.getAsMention() + " is already muted!");
                            eb.setColor(color.getRandomColor());
                            eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                    data.getSelfAvatar(event));
                            eb.setTimestamp(Instant.now());

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(20, TimeUnit.SECONDS);
                                eb.clear();
                            });
                        }

                    }
                } else if(args.length > 3){
                    if(args[2].equalsIgnoreCase("-s")){
                        List<Role> roles = event.getGuild().getRolesByName("Muted", true);
                        String reason = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));

                        if (roles.size() < 1) {

                            create.createMutedRole(event);

                            Member mentioned = event.getMessage().getMentionedMembers().get(0);
                            // Build Information Embed to be sent to muted user
                            muted.setDescription("You've been muted on: " + event.getGuild().getName()
                                    + "\n\nReason: \n```\n" + reason + "\n```");
                            muted.setColor(color.getRandomColor());
                            muted.setFooter(event.getJDA().getSelfUser().getName() + " Muted",
                                    data.getSelfAvatar(event));
                            muted.setTimestamp(Instant.now());

                            // Build Information Embed to be sent to server channel
                            eb.setDescription("You've muted: " + mentioned.getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                            eb.setColor(color.getRandomColor());
                            eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                    data.getSelfAvatar(event));
                            eb.setTimestamp(Instant.now());

                            event.getGuild().addRoleToMember(mentioned, event.getGuild().getRolesByName("Muted", true).get(0)).queue();
                            mentioned.getUser().openPrivateChannel().queue((channel) -> {
                                channel.sendMessage(muted.build()).queue();
                                muted.clear();
                                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                    message.delete().queueAfter(20, TimeUnit.SECONDS);
                                    event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                                    eb.clear();
                                });
                            });
                        } else {

                            Role muteRole = event.getGuild().getRolesByName("Muted", true).get(0);

                            for (TextChannel channel : event.getGuild().getTextChannels()) {
                                if(!channel.getParent().getId().equals("579392397189054465")){
                                    channel.getManager().putPermissionOverride(muteRole, EnumSet.of(Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ), EnumSet.of(Permission.MESSAGE_WRITE)).queue();
                                }
                            }
                            Member mentioned = event.getMessage().getMentionedMembers().get(0);
                            if (!mentioned.getRoles().contains(event.getGuild().getRoleById(muteRole.getId()))) {

                                // Build Information Embed to be sent to kicked user
                                muted.setDescription("You've been muted on: " + event.getGuild().getName()
                                        + "\n\nReason: \n```\n" + reason + "\n```");
                                muted.setColor(color.getRandomColor());
                                muted.setFooter(event.getJDA().getSelfUser().getName() + " Muted",
                                        data.getSelfAvatar(event));
                                muted.setTimestamp(Instant.now());

                                // Build Information Embed to be to server channel
                                eb.setDescription(
                                        "You've muted: " + mentioned.getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                                eb.setColor(color.getRandomColor());
                                eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                        data.getSelfAvatar(event));
                                eb.setTimestamp(Instant.now());

                                event.getGuild().addRoleToMember(mentioned, muteRole).queue();
                                mentioned.getUser().openPrivateChannel().queue((channel) -> {
                                    channel.sendMessage(muted.build()).queue();
                                    muted.clear();

                                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                        message.delete().queueAfter(20, TimeUnit.SECONDS);
                                        event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                                        eb.clear();
                                    });
                                });
                            } else {
                                eb.setDescription(mentioned.getAsMention() + " is already muted!");
                                eb.setColor(color.getRandomColor());
                                eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                        data.getSelfAvatar(event));
                                eb.setTimestamp(Instant.now());

                                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                    message.delete().queueAfter(20, TimeUnit.SECONDS);
                                    event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                                    eb.clear();
                                });
                            }

                        }

                    }
                    List<Role> roles = event.getGuild().getRolesByName("Muted", true);
                    String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));

                    if (roles.size() < 1) {

                        create.createMutedRole(event);

                        Member mentioned = event.getMessage().getMentionedMembers().get(0);
                        // Build Information Embed to be sent to muted user
                        muted.setDescription("You've been muted on: " + event.getGuild().getName()
                                + "\n\nReason: \n```\n" + reason + "\n```");
                        muted.setColor(color.getRandomColor());
                        muted.setFooter(event.getJDA().getSelfUser().getName() + " Muted",
                                data.getSelfAvatar(event));
                        muted.setTimestamp(Instant.now());

                        // Build Information Embed to be sent to server channel
                        eb.setDescription("You've muted: " + mentioned.getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                        eb.setColor(color.getRandomColor());
                        eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                data.getSelfAvatar(event));
                        eb.setTimestamp(Instant.now());

                        success.setDescription(event.getMember().getAsMention() + " muted " + mentioned.getAsMention() + "\n\nReason: \n```" + reason + "\n```");
                        success.setColor(color.getRandomColor());
                        success.setFooter(event.getJDA().getSelfUser().getName() + " Mute", data.getSelfAvatar(event));
                        success.setTimestamp(Instant.now());

                        event.getGuild().addRoleToMember(mentioned, event.getGuild().getRolesByName("Muted", true).get(0)).queue();
                        mentioned.getUser().openPrivateChannel().queue((channel) -> {
                            channel.sendMessage(muted.build()).queue();
                            muted.clear();

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(20, TimeUnit.SECONDS);
                                event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                                eb.clear();
                                data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                                    success.clear();
                                });
                            });
                        });
                    } else {

                        Role muteRole = event.getGuild().getRolesByName("Muted", true).get(0);

                        for (TextChannel channel : event.getGuild().getTextChannels()) {
                            if(!channel.getParent().getId().equals("579392397189054465")){
                                channel.getManager().putPermissionOverride(muteRole, EnumSet.of(Permission.MESSAGE_HISTORY, Permission.MESSAGE_READ), EnumSet.of(Permission.MESSAGE_WRITE)).queue();
                            }
                        }
                        Member mentioned = event.getMessage().getMentionedMembers().get(0);
                        if (!mentioned.getRoles().contains(event.getGuild().getRoleById(muteRole.getId()))) {

                            // Build Information Embed to be sent to kicked user
                            muted.setDescription("You've been muted on: " + event.getGuild().getName()
                                    + "\n\nReason: \n```\n" + reason + "\n```");
                            muted.setColor(color.getRandomColor());
                            muted.setFooter(event.getJDA().getSelfUser().getName() + " Muted",
                                    data.getSelfAvatar(event));
                            muted.setTimestamp(Instant.now());

                            // Build Information Embed to be to server channel
                            eb.setDescription(
                                    "You've muted: " + mentioned.getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                            eb.setColor(color.getRandomColor());
                            eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                    data.getSelfAvatar(event));
                            eb.setTimestamp(Instant.now());

                            success.setDescription(event.getMember().getAsMention() + " muted " + mentioned.getAsMention() + "\n\nReason: \n```" + reason + "\n```");
                            success.setColor(color.getRandomColor());
                            success.setFooter(event.getJDA().getSelfUser().getName() + " Mute", data.getSelfAvatar(event));
                            success.setTimestamp(Instant.now());

                            event.getGuild().addRoleToMember(mentioned, muteRole).queue();
                            mentioned.getUser().openPrivateChannel().queue((channel) -> {
                                channel.sendMessage(muted.build()).queue();
                                muted.clear();

                                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                    message.delete().queueAfter(20, TimeUnit.SECONDS);
                                    event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                                    eb.clear();
                                    data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                                        success.clear();
                                    });
                                });
                            });
                        } else {
                            eb.setDescription(mentioned.getAsMention() + " is already muted!");
                            eb.setColor(color.getRandomColor());
                            eb.setFooter(event.getJDA().getSelfUser().getName() + " Mute",
                                    data.getSelfAvatar(event));
                            eb.setTimestamp(Instant.now());

                            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                                message.delete().queueAfter(20, TimeUnit.SECONDS);
                                event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                                eb.clear();
                            });
                        }

                    }
                }
            } else {
                eb.setDescription(event.getMember().getAsMention()
                        + ", You dont have the permission to mute members on this guild.");
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
        return "Mute";
    }

    public String getDescription(){
        return "Mutes the specified member for the specified reason if no reason is specified then the member is muted for \"No Reason Specified\".";
    }

    public String getShortDescription() {
        return "Mutes the specified member";
    }

    public String getRequiredRoles() {
        return "Owner, Developer, Administrator, Moderator";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "mute {@member} [reason]\n```";
    }

    public boolean isDisabled() {
        return false;
    }
}


