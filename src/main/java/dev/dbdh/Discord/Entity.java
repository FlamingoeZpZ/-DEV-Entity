package dev.dbdh.Discord;

import dev.dbdh.Discord.Listeners.Economy.AddEveryoneToDatabase;
import dev.dbdh.Discord.Listeners.Economy.Balance;
import dev.dbdh.Discord.Listeners.Economy.EditCoins;
import dev.dbdh.Discord.Listeners.Fun.LookingToPlay;
import dev.dbdh.Discord.Listeners.Fun.Screenshare;
import dev.dbdh.Discord.Listeners.Information.Help;
import dev.dbdh.Discord.Listeners.Information.ReactionRoleAssign;
import dev.dbdh.Discord.Listeners.Miscellaneous.*;
import dev.dbdh.Discord.Listeners.Moderation.*;
import dev.dbdh.Discord.Listeners.Settings.SetPrefix;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.net.UnknownHostException;

public class Entity {
    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException, UnknownHostException {
        JDABuilder entity = new JDABuilder(AccountType.BOT).setToken(System.getenv("ENTITYDEVTOKEN"));

        entity.setStatus(OnlineStatus.DO_NOT_DISTURB);
        entity.setActivity(Activity.watching("Loading Bars"));

        entity.addEventListeners(
                //Misc Listeners
                //new Censor(),
                new Join(),
                new Ready(),
                new Leave(),
                new NameUpdate(),

                //Fun Listeners
                new LookingToPlay(),
                new Screenshare(),

                //Economy Listeners
                new AddEveryoneToDatabase(),
                new Balance(),
                new EditCoins(),

                //Information Listeners
                new ReactionRoleAssign(),
                new Help(),

                //Moderation Listeners
                new Ban(),
                new Clear(),
                new Kick(),
                new Mute(),
                new RoleAdd(),
                new RoleRemove(),
                new Softban(),
                new Tempmute(),
                new Unmute(),

                // Settings Listeners
                new SetPrefix()
        );

        entity.build();
    }

}
