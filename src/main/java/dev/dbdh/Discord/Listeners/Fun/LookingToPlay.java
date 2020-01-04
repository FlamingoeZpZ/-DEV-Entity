package dev.dbdh.Discord.Listeners.Fun;

import dev.dbdh.Discord.Utilities.Aliases;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LookingToPlay extends ListenerAdapter {
    //Jesus ExZi i didn't know you also looked through the chats xD
    Data data = new Data();
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();
        Aliases al = new Aliases();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "match")) {
            if (args[0].equalsIgnoreCase(data.getPrefix() + "match")) {
                if (args.length != 2) {
                    eb.setDescription(event.getMember().getAsMention() + " What platform are you looking for people to play with on?\n`XBOX | PS4 | PC | MOBILE | SWITCH `\n\n```\n" + data.getPrefix() + "match <platform>\n```\n\n```\n<> | REQUIRED\nNotice: If you are in a voice channel, the channel will be added\n```");
                    eb.setColor(color.errorRed);
                    eb.setFooter("**Entity Matchmaking Command Error**", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        eb.clear();
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                    });
                } else if (args.length == 2) {
                    if (event.getMember().getVoiceState().inVoiceChannel()) {
                        String voiceChannel = event.getMember().getVoiceState().getChannel().getName();
                        if (Arrays.stream(al.xboxAliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on Xbox " + event.getGuild().getRoleById("606527228074786851").getAsMention() + "\nThey are in the **" + voiceChannel + "** voice channel").queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("606527228074786851")).queue();
                        } else if (Arrays.stream(al.ps4Aliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on Playstation " + event.getGuild().getRoleById("606527283582468106").getAsMention() + "\nThey are in the **" + voiceChannel + "** voice channel").queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("606527283582468106")).queue();
                        } else if (Arrays.stream(al.pcAliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on PC " + event.getGuild().getRoleById("541091501908951041").getAsMention() + "\nThey are in the **" + voiceChannel + "** voice channel").queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("541091501908951041")).queue();
                        } else if (Arrays.stream(al.mobileAliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on Mobile " + event.getGuild().getRoleById("606531352690688035").getAsMention() + "\nThey are in the **" + voiceChannel + "** voice channel").queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("606531352690688035")).queue();
                        } else if (Arrays.stream(al.switchAliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on Nintendo Switch " + event.getGuild().getRoleById("634878800185786378").getAsMention() + "\nThey are in the **" + voiceChannel + "** voice channel").queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("634878800185786378")).queue();
                        } else if (args[1].equalsIgnoreCase("stop")) {
                            event.getChannel().sendMessage("Stopped matchmaking").queue();
                            //XBOX
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("606527228074786851")).queue();
                            //PS4
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("606527283582468106")).queue();
                            //PC
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("541091501908951041")).queue();
                            //Mobile
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("606531352690688035")).queue();
                            //Switch
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("634878800185786378")).queue();

                        }
                    } else {
                        if (Arrays.stream(al.xboxAliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on Xbox " + event.getGuild().getRoleById("606527228074786851").getAsMention()).queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("606527228074786851")).queue();
                        } else if (Arrays.stream(al.ps4Aliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on Playstation " + event.getGuild().getRoleById("606527283582468106").getAsMention()).queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("606527283582468106")).queue();
                        } else if (Arrays.stream(al.pcAliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on PC " + event.getGuild().getRoleById("541091501908951041").getAsMention()).queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("541091501908951041")).queue();
                        } else if (Arrays.stream(al.mobileAliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on Mobile " + event.getGuild().getRoleById("606531352690688035").getAsMention()).queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("606531352690688035")).queue();
                        } else if (Arrays.stream(al.switchAliases).anyMatch(args[1]::equalsIgnoreCase)) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " is looking to play on Nintendo Switch " + event.getGuild().getRoleById("634878800185786378").getAsMention()).queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("634878800185786378")).queue();
                        } else if (args[1].equalsIgnoreCase("stop")) {
                            event.getChannel().sendMessage("Stopped matchmaking").queue();
                            //XBOX
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("606527228074786851")).queue();
                            //PS4
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("606527283582468106")).queue();
                            //PC
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("541091501908951041")).queue();
                            //Mobile
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("606531352690688035")).queue();
                            //Switch
                            event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("634878800185786378")).queue();

                        }
                    }
                }
            }

        }
    }

    public String getName(){
        return "Match";
    }

    public String getDescription() {
        return "Pings the other members of the server also looking to play on the specified platform";
    }

    public String getShortDescription() {
        return "Pings other members to create a match";
    }

    public String getRequiredRoles() {
        return "Everyone";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "match {platform}\n```";
    }

    public boolean isDisabled() {
        return false;
    }

}