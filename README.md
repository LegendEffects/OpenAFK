# OpenAFK
An open source AFK plugin for Spigot.

## Understanding the annotations.
* **Parameters with an "(optional)" next to them don't need to be provided unless wanted.**

* Actions that support multiline are compatible with YAML multiline syntax on the content.
Example:
```yaml
- action: "message"
  content: |
    First line
    Second line
    Third line
```

## Actions
Actions are OpenAFKs way to let you orchestrate what happens on events. (You can even register your own actions!)



### Actionbar
Displays a temporary or permanent actionbar message to everyone or the actioned player.
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
Teleports the actioned player to a defined location set with `/openafk set afkarea`.
```yaml
- action: "afkarea"
```

### Broadcast
**Supports Multiline**
Broadcasts a message to an entire server.
```yaml
- action: "broadcast"
  content: "&cContent to broadcast."
  permission: permission.node #(Optional) - Sends the broadcast only to people with this permission
  repeat: 2 #(Optional) - Amount of times to send the broadcast
```
### Look
Changes where a players head is, using yaw and pitch.

(Only one is required but both can be used.)
```yaml
- action: "look"
  yaw: 2
  pitch: 5
```

### Command
Issues a command by a player or by console.
```yaml
- action: "message"
  by: "player" #(Optional - defaults to player) accepts player or console
  command: "/help" # Console commands do NOT use the /
```

### Message
**Supports Multiline**
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

TO DOCUMENT: NametagAction

## Scripts
Scripts are lists of actions which are ran in order, these define what actions to do when certain events happen.
