package dev.dbdh.Discord.Listeners.Moderation;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.RoleCheck;
import dev.dbdh.Discord.Utilities.Time;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class RoleRemove extends ListenerAdapter {

    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        EmbedBuilder grantee = new EmbedBuilder();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "removerole")) {
            if (rc.isOwner(event) || rc.isDeveloper(event) || rc.isAdministrator(event)) {
                if (args.length < 3) {
                    eb.setDescription("You didn't specify enough arguments. Please refer to " + data.getPrefix() + "help removerole.");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length > 2 && args.length < 4) {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    Role role = event.getGuild().getRolesByName(args[1], true).get(0);

                    eb.setDescription("Successfully removed the role " + role.getAsMention() + " from the member " + mentioned.getAsMention());
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Role Remove", data.getSelfAvatar(event));

                    success.setDescription(event.getMember().getAsMention() + " removed the role " + role.getAsMention() + " from the member " + mentioned.getAsMention());
                    success.setColor(color.getRandomColor());
                    success.setTimestamp(Instant.now());
                    success.setFooter("Entity Role Remove Log", data.getSelfAvatar(event));

                    grantee.setDescription("You've been removed from the role " + role.getAsMention() + " by " + event.getMember().getAsMention());
                    grantee.setColor(color.getRandomColor());
                    grantee.setTimestamp(Instant.now());
                    grantee.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                    mentioned.getUser().openPrivateChannel().complete().sendMessage(grantee.build()).queue((message) -> {
                        grantee.clear();
                        event.getChannel().sendMessage(eb.build()).queue((message1) -> {
                            message1.delete().queueAfter(15, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                            eb.clear();
                            data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                                success.clear();
                            });
                        });
                    });

                    removeRole(event, role, mentioned);
                } else if (args.length > 3) {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    Role role = event.getGuild().getRolesByName(args[1], true).get(0);

                    eb.setDescription("Successfully removed the role " + role.getAsMention() + " from the member " + mentioned.getAsMention() + "\n**Expires in:** " + Integer.parseInt(args[3].substring(0, args[3].length() - 1)) + " " + Time.getTime(args[3]).name());
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Role Remove", data.getSelfAvatar(event));

                    success.setDescription(event.getMember().getAsMention() + " removed the role " + role.getAsMention() + " from the member " + mentioned.getAsMention() + "\n**Will expire in:** " + Integer.parseInt(args[3].substring(0, args[3].length() - 1)) + " " + Time.getTime(args[3]).name());
                    success.setColor(color.getRandomColor());
                    success.setTimestamp(Instant.now());
                    success.setFooter("Entity Role Remove Log", data.getSelfAvatar(event));

                    grantee.setDescription("You've been removed from the role " + role.getAsMention() + " by " + event.getMember().getAsMention() + "\nThis role will be readded in " + Integer.parseInt(args[3].substring(0, args[3].length() - 1)) + " " + Time.getTime(args[3]).name());
                    grantee.setColor(color.getRandomColor());
                    grantee.setTimestamp(Instant.now());
                    grantee.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                    mentioned.getUser().openPrivateChannel().complete().sendMessage(grantee.build()).queue((message) -> {
                        grantee.clear();
                        event.getChannel().sendMessage(eb.build()).queue((message1) -> {
                            message1.delete().queueAfter(15, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                            eb.clear();
                            data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                                success.clear();
                            });
                        });
                    });
                    removeRole(event, role, mentioned);
                    addRoleAfterTimesUp(event, role, mentioned, args[3]);
                }
            } else {
                eb.setDescription(event.getMember().getAsMention() + " you don't have the permissions to edit roles on this guild");
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Insufficient Permissions", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                    eb.clear();
                });
            }
        }
    }

    private static void removeRole(GuildMessageReceivedEvent event, Role role, Member mentioned) {
        event.getGuild().removeRoleFromMember(mentioned, role).queue();
    }

    private static void addRoleAfterTimesUp(GuildMessageReceivedEvent event, Role role, Member mentioned, String args) {
        mentioned.getUser().openPrivateChannel().complete().sendMessage("The " + role.getAsMention() + "has been removed from you.").queueAfter(Integer.parseInt(args.substring(0, args.length() - 1)), Time.getTime(args));
        event.getGuild().addRoleToMember(mentioned, event.getGuild().getRolesByName(role.getName(), true).get(0)).queueAfter(Integer.parseInt(args.substring(0, args.length() - 1)), Time.getTime(args));
    }

    public String getName() {
        return "Removerole";
    }

    public String getDescription() {
        return "Removes the specified role from the mentioned member. If you specify a time length the role will be readded after the specified amount of time.";
    }

    public String getShortDescription() {
        return "Removes a role from a mentioned member";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "removerole {role} {@member} [time](time multiplier)\n```";
    }

    public String getRequiredRoles() {
        return "Owner, Developer, Administrator";
    }

    public boolean isDisabled() {
        return false;
    }
}
