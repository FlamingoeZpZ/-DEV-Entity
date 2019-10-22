package dev.dbdh.Discord.Listeners.Miscellaneous;

import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Ready extends ListenerAdapter {

    public void onReady(ReadyEvent event){
        Data data = new Data();
        event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
        Activity[] games = { Activity.watching("the github repository"), Activity.watching("the clown suck fingers"), Activity.watching("you get destroyed in Dead By Daylight"), Activity.watching("you getting torn apart by the hag"), Activity.playing("hide and seek with wraith"), Activity.playing("with my flashlight"), Activity.playing("with some pallets"), Activity.watching("survivors run"), Activity.playing("claudette simulator"), Activity.playing("with your suggestions"), Activity.watching("Dwight hide in a locker") };
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> {
            Random random = new Random();
            int game = random.nextInt(games.length);
            event.getJDA().getPresence().setActivity(games[game]);
        }, 0, 2, TimeUnit.MINUTES);
    }

}
