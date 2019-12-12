# OpenAFK
An open source AFK plugin for Spigot.

## Understanding the annotations.
**Parameters with an "(optional)" next to them don't need to be provided unless wanted.**

## Actions
Actions are OpenAFKs way to let you orchestrate what happens on events. (You can even register your own actions!)

Current Actions:
* Actionbar - Displays an actionbar message
* AfkArea - Sends the player to a set location
* Look - Changes the players yaw and pitch
* Message - Sends the player or everyone a message
* Title - Sends the player a title



### Actionbar
Displays a temporary or permanent actionbar message to everyone or the actioned player
```yaml
- action: "actionbar"
  to: "player" #(Optional) - defaults to player, can be player or all
  content: "&cHello!"
  permanent: true #(Optional)
```

Resetting
```yaml
- action: "actionbar"
  reset: true
  to: "player" #(Optional) - defaults to player, can be player or all
```

### AfkArea
Teleports the actioned player to a defined location set with `/openafk set afkarea`
```yaml
- action: "afkarea"
```

### Look
Changes where a players head is, using yaw and pitch.

(Only one is required but both can be used.)
```yaml
- action: "look"
  yaw: 2
  pitch: 5
```

### Message
Messages the actioned player with given content once or multiple times.
```yaml
- action: "message"
  content: "&cHello!"
  repeat: 2 #(Optional)
```

### Title
Shows a temporary or permanent title to the actioned player.
```yaml
- action: "title"
  title: "&cHello!"
  subtitle: "&aWelcome back."
  fadeIn: 10
  fadeOut: 10
  stay: 500 # Valid values: integer, "permanent"
```

Resetting
```yaml
- action: "title"
  reset: true
```

## Scripts
Scripts are lists of actions which are ran in order, these define what actions to do when certain events happen.
