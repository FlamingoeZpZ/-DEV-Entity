package dev.dbdh.Discord;

import dev.dbdh.Discord.Listeners.Backend.AddEveryoneToDatabase;
import dev.dbdh.Discord.Listeners.Backend.NameUpdate;
import dev.dbdh.Discord.Listeners.Backend.Nonexistant;
import dev.dbdh.Discord.Listeners.Economy.*;
import dev.dbdh.Discord.Listeners.Fun.*;
import dev.dbdh.Discord.Listeners.Fun.ChestGame.Chest;
import dev.dbdh.Discord.Listeners.Fun.Custom.AddToRole;
import dev.dbdh.Discord.Listeners.Information.*;
import dev.dbdh.Discord.Listeners.Miscellaneous.*;
import dev.dbdh.Discord.Listeners.Moderation.*;
import dev.dbdh.Discord.Listeners.Settings.*;
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
                //Backend Listeners
                new AddEveryoneToDatabase(),
                new NameUpdate(),
                new Nonexistant(),

                // Economy Listeners
                new buildShop(),
                new Shop(),
                new Balance(),
                new Daily(),
                new EditCoins(),
                //new SuccessfulBump(),

                //Fun Listeners
                new AddToRole(),
                new Chest(),
                new Cleverbot(),
                new LookingToPlay(),
                new Screenshare(),

                //Information Listeners
                new AliasesHelp(),
                new Help(),
                new ListWarnings(),
                new ReactionRoleAssign(),
                new ReactionRoleUnassign(),
                new Report(),
                new TODO(),

                //Misc Listeners
                //new Censor(),
                new Join(),
                new Leave(),
                new Ready(),
                new ReactionMessage(),
                new Suggest(),
                new SuggestReactAdd(),

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
                new Warn(),

                // Party Listeners


                // Settings Listeners
                new SetPrefix()
        );
        entity.build();
    }

}
