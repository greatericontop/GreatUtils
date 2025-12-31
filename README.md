# GreatUtils

GreatUtils is a bundle of admin utilities for Minecraft servers.

It is designed to:

- Implement features that are missing from many commonly used utility plugins
- Minimize bloat: unused features should not lag the server

# Features List

## Command Execution

These command execution utilities are convenient and also allow easier scripting (e.g., command blocks or `commands.yml`):

- `/execlater` - Execute a command after a delay
- `/execlaterrng` - Execute a command after a delay, randomly chosen between two values
- `/exectimer` - Execute a command a certain number of times at a certain interval
- `/mytasks` - Shows your later and timer tasks
- `/canceltask` - Lets you cancel tasks that you previously made
- `/sudo` - Send a chat message or run a command as another player

For example, you can make a basic duel world border auto-shrink with the following in your `commands.yml`:

```yaml
  startduel:
  - worldborder set 200
  # After 5 minutes, start shrinking the world border
  - execlater 5m worldborder set 20 300
```

## Kits

Most kit plugins are very complicated and confusing to use.
GreatUtils provides a simple kit system meant for friendly servers.
(If you're looking for a lot of features, you should use a more complex kit plugin.)

- `/kit <kit name>` - Give yourself a kit
- `/kit <kit name> everyone` - Give the kit to all players
- `/kit <kit name> update` - Create or update a kit with your current inventory contents
  - If you replace an existing kit, a hidden backup is created with the same name with a `~` suffix. For example, `/kit kit1 update` -> old kit backed up in `kit1~`.
- `/kit <kit name> delete` - Delete a kit (also creates a backup with `~` suffix)

## Warps

A no-nonsense simple warp system.

- `/warp <warp name>` - Give yourself a kit
- `/warp <warp name> set` - Set a warp at your current position
- `/warp <warp name> delete` - Delete a warp
