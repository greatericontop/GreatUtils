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