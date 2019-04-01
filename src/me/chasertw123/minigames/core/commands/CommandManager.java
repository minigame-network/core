package me.chasertw123.minigames.core.commands;

import me.chasertw123.minigames.core.commands.general.*;

/**
 * Created by Chase on 6/29/2017.
 */
public class CommandManager {

    public CommandManager() {
        new Cmd_AddNote();
        new Cmd_Discord();
        new Cmd_GameBooster();
        new Cmd_History();
        new Cmd_Hub();
        new Cmd_LocalPlay();
        new Cmd_Infraction();
        new Cmd_ModifyStat();
        new Cmd_Report();
        new Cmd_Coins();
    }
}
