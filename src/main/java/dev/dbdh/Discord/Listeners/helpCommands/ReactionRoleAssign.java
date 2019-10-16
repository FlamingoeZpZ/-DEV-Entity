package dev.dbdh.Discord.Listeners.helpCommands;

import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionRoleAssign extends ListenerAdapter {
    Data data = new Data();
    public static int ID;
    //----//
    //TODO Set reaction adder to only work in specific channel
    //---//
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event){
        Role KillerMain = event.getGuild().getRoleById("540744380416262185"); // 1
        Role SurvivorMain = event.getGuild().getRoleById("540744386506653712"); // 3

        Role Rarely = event.getGuild().getRoleById("540755698255003699");
        Role Occasional = event.getGuild().getRoleById("540755676541222912");
        Role AllTheTimes = event.getGuild().getRoleById("540755651731783680");

        Role Gross = event.getGuild().getRoleById("543652286279319552");
        Role Games = event.getGuild().getRoleById("575326203901116474");
        Role Viewer = event.getGuild().getRoleById("540759805481189379");

        Role ServerAnnouncement = event.getGuild().getRoleById("589450072488542219");
        Role GameAnnouncement = event.getGuild().getRoleById("589450041542836243");
        Role Publisher = event.getGuild().getRoleById("540748208083697665");
        Role PingForTournaments = event.getGuild().getRoleById("629135819826921493");

        if(!event.getMember().getUser().equals(event.getJDA().getSelfUser()) && // the person who added the reaction IS NOT the bot
                event.getChannel().equals(event.getGuild().getTextChannelById("629510813518004244"))){

            if(event.getReactionEmote().getId().equals("575437440450560000")){ //CheckMark
                if(event.getMember().getRoles().contains(KillerMain))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), KillerMain).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), KillerMain).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("579132892370829333")){ // X
                if(event.getMember().getRoles().contains(SurvivorMain))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), SurvivorMain).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), SurvivorMain).queue(); // 2
                }

            }
            else if(event.getReactionEmote().getId().equals("629126364477390848")){
                if(event.getMember().getRoles().contains(Rarely))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), Rarely).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), Rarely).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("629126364083257355")){
                if(event.getMember().getRoles().contains(Occasional))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), Occasional).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), Occasional).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("629126364460744714")){
                if(event.getMember().getRoles().contains(AllTheTimes))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), AllTheTimes).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), AllTheTimes).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("573230699885232140")){
                if(event.getMember().getRoles().contains(Gross))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), Gross).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), Gross).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("573235478711500837")){
                if(event.getMember().getRoles().contains(Games))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), Games).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), Games).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("583392850067324928")){
                if(event.getMember().getRoles().contains(Viewer))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), Viewer).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), Viewer).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("575437537074479114")){
                if(event.getMember().getRoles().contains(ServerAnnouncement))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), ServerAnnouncement).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), ServerAnnouncement).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("577557877343125518")){
                if(event.getMember().getRoles().contains(GameAnnouncement))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), GameAnnouncement).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), GameAnnouncement).queue(); // 2
                }
            }
            else if(event.getReactionEmote().getId().equals("577557950315626526")){
                event.getUser().openPrivateChannel().queue((channel) ->
                {
                    if(event.getMember().getRoles().contains(Publisher))
                    {
                        channel.sendMessage("You already have this role! If you'd like to get it removed message, " + event.getGuild().getOwner().getAsMention()).queue();
                    }
                    else
                    {
                        channel.sendMessage("Please DM " + event.getGuild().getOwner().getAsMention() +" With your streaming link to start streaming with us today!").queue();
                    }
                });
            }
            else if(event.getReactionEmote().getId().equals("575437440509280296")){

                if(event.getMember().getRoles().contains(PingForTournaments))
                {
                    event.getGuild().removeRoleFromMember(event.getMember(), PingForTournaments).queue();
                }
                else
                {
                    event.getGuild().addRoleToMember(event.getMember(), PingForTournaments).queue(); // 2
                    event.getUser().openPrivateChannel().queue((channel) ->
                    {
                        channel.sendMessage("This role means you are okay with being pinged for events").queue();
                    });
                }
            }
            else {
                //EDIT
                event.getGuild().getTextChannelById("629510813518004244").sendMessage("Error Role not found please DM " + event.getGuild().getOwner().getAsMention());
            }
            event.getReaction().removeReaction(event.getUser()).queue();
        }
    }
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String [] args = event.getMessage().getContentRaw().split("\\s+");

        Emote KillerE = event.getGuild().getEmoteById("575437440450560000");
        Emote SurvivorE = event.getGuild().getEmoteById("579132892370829333");

        Emote RarelyE = event.getGuild().getEmoteById("629126364477390848");
        Emote OccasionalE = event.getGuild().getEmoteById("629126364083257355");
        Emote AllTheTimesE = event.getGuild().getEmoteById("629126364460744714");

        Emote GrossE = event.getGuild().getEmoteById("573230699885232140");
        Emote GamesE = event.getGuild().getEmoteById("573235478711500837");
        Emote ViewerE = event.getGuild().getEmoteById("583392850067324928");

        Emote ServerAnnouncementE = event.getGuild().getEmoteById("575437537074479114");
        Emote GameAnnouncementE = event.getGuild().getEmoteById("577557877343125518");
        Emote PublisherE = event.getGuild().getEmoteById("577557950315626526");
        Emote PingForTournamentsE = event.getGuild().getEmoteById("575437440509280296");

        EmbedBuilder Embed = new EmbedBuilder();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "test") && event.getMember().isOwner()){



            ID  = Integer.parseInt(args[1]);
            if(ID == 1){
                Embed.setTitle("Who do you play as");
                Embed.setDescription("Are you a survivor of my trials, or perhaps one of my killers? To assign yourself as KILLER press on the TRAPPER ("+KillerE.getAsMention()+")|To assign yourself as SURVIVOR press on the FENG MIN ("+SurvivorE.getAsMention()+")");
                Embed.setColor(0x0066ff);
                Embed.setImage("https://i.imgur.com/ubft50j.png");
                event.getChannel().sendMessage(Embed.build()).queue();
            }

            else if(ID == 2){
                Embed.setTitle("Playtime frequency");
                Embed.setDescription("Next, How often do you play dead by daylight? To assign yourself as RARELY press RED ("+RarelyE.getAsMention()+")|To assing yourself as OCCASIONAL press YELLOW ("+OccasionalE.getAsMention()+")|To assign yourself as ALLTHETIME press GREEN ("+AllTheTimesE.getAsMention()+")");
                Embed.setColor(0x00ff00);
                Embed.setImage("https://media.tenor.com/images/d612804a84f07d939ea3d08ad49dba1a/tenor.gif");
                event.getChannel().sendMessage(Embed.build()).queue();
            }
            else if(ID == 3){
                Embed.setTitle("Games and Media");
                Embed.setDescription("Nearly done! Now, if you'd like to recieve notifications from streamers, go into the NSFW chat or see our games section Press for the following: for NSFW press the SPIRIT ("+GrossE.getAsMention()+")|for GAMES press the TRAPPER ("+GamesE.getAsMention()+")|for VIEWER notifications press the MATHIEU COTE ("+ViewerE.getAsMention()+")");
                Embed.setColor(0xff66ff);
                Embed.setImage("http://www.acceltex.com/wp-content/uploads/2019/02/WiFiLogoGIF2.gif");
                event.getChannel().sendMessage(Embed.build()).queue();
            }
            else if(ID == 4){
                Embed.setTitle("Connect with us!");
                Embed.setDescription("Finally, if you want announcements from us, others, or want to become a publisher use these! SERVER ANNOUNCEMENTS press the MATHIEU COTE ("+ServerAnnouncementE.getAsMention()+")|GAME ANNOUNCEMENTS press the LAURIE ("+GameAnnouncementE.getAsMention()+")|PUBLISHER press the ACE ("+PublisherE.getAsMention()+")|Ping for Event notifications press the LEGION ("+PingForTournamentsE.getAsMention()+")");
                Embed.setColor(0x009999);
                Embed.setImage("https://media.giphy.com/media/SA0h8Tc1iK7tYUP2Bf/giphy.gif");
                event.getChannel().sendMessage(Embed.build()).queue();
            }
            else {
                Embed.setTitle("Introduction");
                Embed.setDescription("Welcome to our server! As of 2019/10/02 the DBD hub made assign roles easier. To assign roles simply press on the corrosponding emojis! If you no longer want to be in a roll anymore, press the role again!");
                Embed.setColor(0xb30000);
                Embed.setImage("https://media.tenor.com/images/51b8e98ef2e43e0ee95748ea47261d11/tenor.gif");
                event.getChannel().sendMessage(Embed.build()).queue();
            }
            Embed.clear();
            event.getMessage().delete().queue();
        }
        if(event.getAuthor().isBot()) {
            if(ID == 1) {
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), KillerE).queue();
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), SurvivorE).queue();
            }
            else if(ID == 2) {
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), RarelyE).queue();
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), OccasionalE).queue();
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), AllTheTimesE).queue();
            }
            else if(ID == 3) {
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), GrossE).queue();
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), GamesE).queue();
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), ViewerE).queue();
            }
            else if(ID == 4) {
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), ServerAnnouncementE).queue();
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), GameAnnouncementE).queue();
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), PublisherE).queue();
                event.getChannel().addReactionById(event.getChannel().getLatestMessageId(), PingForTournamentsE).queue();
            }
            ID = 0;
        }
    }
}
