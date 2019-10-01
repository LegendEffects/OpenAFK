# OpenAFK
An open source AFK plugin for Spigot.

## Actions
Actions are OpenAFKs way to let you orchestrate what happens on events. (You can even register your own actions!)

Current Actions:
* ActionBar (`actionbar|("constant", "REMOVE")("constant", (message, ""), "all")(message, "all")(message)`) - Displays a message on the action bar
* AFKArea (`afkarea`) - Teleports the player to the AFKArea or away from the AFKArea
* Broadcast (`broadcast|(?repeatCount, message)("PERM", permission, message1, message2, ...)`) - Broadcasts a message to all players
* Command (`command|("player", command)("console", command)`) - Runs a command as the player or as the console
* Invisibility (`invisibility|(true)(false)`) - Makes the player invisible to others or not
* Message (`message|(?repeatCount, message)(message1,message2, ...)`) - Sends the player a message
* SetPitch (`setpitch|(pitch)`) - Sets the players pitch
* Title - (`title|("reset")(title, subtitle, fadeIn, fadeOut, stay)`) Displays a title and subtitle on the players screen