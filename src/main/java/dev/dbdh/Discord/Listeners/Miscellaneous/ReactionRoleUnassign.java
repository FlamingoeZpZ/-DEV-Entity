package dev.dbdh.Discord.Listeners.Miscellaneous;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class ReactionRoleUnassign extends ListenerAdapter {
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event){
        if(!event.getMember().getUser().isBot()){
            if(event.getChannel().getId().equals("638558586645118997") || event.getChannel().getId().equals("640329778280267826")){
                Color color = new Color();
                Data data = new Data();
                Database db = new Database();
                EmbedBuilder eb = new EmbedBuilder();
                db.connect();
                if(event.getMessageId().equals(db.getCollection("guild").find().first().getString("roleAssignMessageID"))){
                    if(event.getReactionEmote().getId().equals("575437440450560000")){
                        //Killer
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("540744380416262185")).queue();
                        eb.setDescription("Successfully removed the Killer role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if(event.getReactionEmote().getId().equals("579132892370829333")){
                        //Survivor
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("540744386506653712")).queue();
                        eb.setDescription("Successfully removed the Survivor role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if(event.getReactionEmote().getId().equals("649444100982177792")){
                        //All the time
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("540755651731783680")).queue();
                        eb.setDescription("Successfully removed the All the time player role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if(event.getReactionEmote().getId().equals("649444100864475138")){
                        //Occasionally
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("540755676541222912")).queue();
                        eb.setDescription("Successfully removed the Occasional player role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if(event.getReactionEmote().getId().equals("649444100952817686")){
                        //Rarely
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("540755698255003699")).queue();
                        eb.setDescription("Successfully removed the Rare player role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if(event.getReactionEmote().getId().equals("573230699885232140")){
                        //NSFW
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("543652286279319552")).queue();
                        eb.setDescription("Successfully removed the 18+ role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if(event.getReactionEmote().getId().equals("573235478711500837")){
                        //Game
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("575326203901116474")).queue();
                        eb.setDescription("Successfully removed the Games role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if (event.getReactionEmote().getId().equals("583392850067324928")) {
                        //Streamer Announcements
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("540759805481189379")).queue();
                        eb.setDescription("Successfully removed the Viewer role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if (event.getReactionEmote().getId().equals("575437537074479114")) {
                        //Server Announcements
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("589450072488542219")).queue();
                        eb.setDescription("Successfully removed the Server Announcements role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if (event.getReactionEmote().getId().equals("577557877343125518")) {
                        //Game Announcements
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("589450041542836243")).queue();
                        eb.setDescription("Successfully removed the Game Announcements role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    } else if (event.getReactionEmote().getId().equals("575437440509280296")) {
                        //Event Announcements
                        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById("629135819826921493")).queue();
                        eb.setDescription("Successfully removed the Interested in Game Events role");
                        eb.setColor(color.getRandomColor());
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Entity Role Removed", data.getSelfAvatar(event));

                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(eb.build()).queue((message) -> {
                            eb.clear();
                        });
                    }
                }
            }
        }
    }
}
