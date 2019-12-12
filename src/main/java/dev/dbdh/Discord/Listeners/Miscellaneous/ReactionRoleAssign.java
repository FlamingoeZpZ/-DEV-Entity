package dev.dbdh.Discord.Listeners.Miscellaneous;

import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionRoleAssign extends ListenerAdapter {

    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event){
        if(!event.getMember().getUser().isBot()){
            if(event.getChannel().getId().equals("638558586645118997")){
                Database db = new Database();
                db.connect();
                if(event.getMessageId().equals(db.getCollection("guild").find().first().getString("roleAssignMessageID"))){
                    switch(event.getReactionEmote().getId()){
                        //Killer
                        case "575437440450560000": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("540744380416262185")).queue();
                            //Survivor
                        case "579132892370829333": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("540744386506653712")).queue();
                            //All the time
                        case "649444100982177792": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("540755651731783680")).queue();
                            //Occasionally
                        case "649444100864475138": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("540755676541222912")).queue();
                            //Rarely
                        case "649444100952817686": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("540755698255003699")).queue();
                            //NSFW
                        case "573230699885232140": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("543652286279319552")).queue();
                            //Game
                        case "573235478711500837": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("575326203901116474")).queue();
                            //Streamer Ann
                        case "583392850067324928": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("540759805481189379")).queue();
                            //Server Ann
                        case "575437537074479114": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("589450072488542219")).queue();
                            //Game Ann
                        case "577557877343125518": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("589450041542836243")).queue();
                            //Event Ann
                        case "575437440509280296": event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById("629135819826921493")).queue();
                    }
                }
            }
        }
    }

}
