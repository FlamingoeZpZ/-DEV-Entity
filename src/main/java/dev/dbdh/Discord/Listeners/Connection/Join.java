package dev.dbdh.Discord.Listeners.Connection;

import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class Join extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Data data = new Data();
        String [] URL = {
                //Survivor
                "https://steamuserimages-a.akamaihd.net/ugc/977729722245075235/7DC1276808049996DD89A63DB70CA67E5459E130/",
                "https://pbs.twimg.com/media/Dtyx0-jXgAA1rgL.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEfQqkVFx5HbVInRGqHwhih4oE6P7kIHJX06-UpT-1WFX4Y6S5sA",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBLZae5U_TfYDjVLoDiAcg26i1oHD7BKDWVM8QU7TPrw5bdSRn",
                "https://steamuserimages-a.akamaihd.net/ugc/842585513742504143/FC74B577B896049B913DA14884CD2966067C5E54/",
                "https://media.discordapp.net/attachments/571524975815819274/596361024786137120/54447396_2345371965695569_4207205876172668763_n.jpg",
                "https://cdn.discordapp.com/attachments/571524975815819274/596330894478802954/tumblr_pgs2qoXykp1s5e19v_540.png",
                "https://steamuserimages-a.akamaihd.net/ugc/260468244966576315/692397BAADEF1165E062438CD29ED523D31A6554/",
                "https://i.redd.it/wmnz6e056rz01.gif",
                "data:image/jpeg;base64,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",
                "https://scontent-iad3-1.cdninstagram.com/vp/c5da5e362c3fdb06129f62c3adf91a90/5D9E72E0/t51.2885-15/sh0.08/e35/c85.0.725.725a/s640x640/56331123_405441466939574_4999243448575151304_n.jpg?_nc_ht=scontent-iad3-1.cdninstagram.com",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSx0QdC6oIqTmT2V6UIPIrIAPQKFB66CfJT0nTbvDRGR-Puj-cr",
                //Killer
                "https://cdn.discordapp.com/attachments/551547549870325770/580090242309161029/sp0noi7e90b01.png",
                "https://memestatic1.fjcdn.com/comments/Ah+yes+dead+by+daylight+memes+_53980ef55bbc5fddaf4cdc26f1d26be4.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVhzc6wTdx2vvssesPSMkb8YInMdHJVZwk0SmiPWKuMqWmfFE6DA",
                "https://66.media.tumblr.com/fae95054658dcb05c1fa8dbabf66e0ba/tumblr_p61yt9QHLt1vkq9jso1_400.png",
                "https://media.tenor.com/images/2e9bdb6a6a3ed501ea13d625c0bb094c/tenor.gif",
                "https://i.imgur.com/onqxL3n.jpg",
                "https://cdn.discordapp.com/attachments/571524975815819274/596330812409118740/1482177123_dbdmemes.png",
                "https://media.giphy.com/media/ZgSBJhZEVbJJIdREN6/giphy.gif",
                "https://media1.tenor.com/images/b1475188ecbcfc891dfe0de93d8278dd/tenor.gif?itemid=14236107",
                "http://pm1.narvii.com/7064/5fe6d294d3431567d4b8dd14daa51232e3068ae8r1-600-596v2_uhq.jpg",
                "https://scontent-frx5-1.cdninstagram.com/vp/adb0c370b15727de40120b5d199c0bfd/5DB5BA44/t51.2885-15/e35/51861373_392831551509827_4587269349164960049_n.jpg?_nc_ht=scontent-frx5-1.cdninstagram.com&se=7&ig_cache_key=MTk5MDA4MTAzODMxNjQ2NzAzOA%3D%3D.2",
                "https://66.media.tumblr.com/cd91d9b5461df3def4585e37179a819b/tumblr_pgpisaQtC31wpfv35_540.jpg"

        };
        String [] FollowText = {
                //Survivor
                "But I need my perks Behaviour ",
                "Because Altruism is for psychos ",
                "Maybe we shouldn't do cross country SWF? ",
                "Typical white ward action :sob: ",
                "There is no coming back from that one chief... ",
                "Altrusim too gud ",
                "Jake is cuter but it's whatever ",
                "Epic... Gamer... Moment ",
                "Don't hurt Meg pls ",
                "Dwight being smooth As FUCK ",
                "Ma Puppet :sob: ",
                "What will you survivors do now? ",
                //Killer
                "This pic speaks for itself ",
                "Maybe you should be worried? ",
                "RIP hex totem... I woulda told a joke, but it'd be too dull ",
                "You won't be able to ",
                "Bing Bong, Welcome our newest Member! ",
                event.getGuild().getMemberById("383771414290890773").getNickname() +" is a filthy Nurse main please hate him ",
                "CARRRRL ",
                "Legion at it again! Hittin up em stabs ",
                "Hello, " + event.getMember().getAsMention() + " I'm always watching ",
                "The light burns me too ",
                "Permanent pain for poor micheal ",
                "No wonder the survivors say we have tunnel vision, we just can't see "
        };

    }
}
