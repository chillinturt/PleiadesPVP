# PleiadesPVP

A kit PvP minigame plugin for Minecraft 1.21 (Spigot/Paper API, Java 21). Players pick a kit, spawn into a fresh world, and fight inside a shrinking world border until one is left.

It runs as the game server behind the Velocity proxy set up in [MinecraftVelocityProxyNetwork](https://github.com/chillinturt/MinecraftVelocityProxyNetwork), and players can return to the lobby server from in game.

## How a round works

1. Players join in Adventure mode and get a **kit selector** (chest) and a **return to hub** item (clock).
2. `/startgame` puts everyone in Survival, teleports them to the game world's spawn, and sets an 800-block world border.
3. After a delay, the border shrinks to 64 blocks over 15 minutes.
4. Dead players drop to Spectator mode, and anyone who joins mid-round spectates.
5. The game world is deleted when the server shuts down, so every round starts on a new map.

## Energy

Abilities cost **Energy**, shown on a sidebar scoreboard. Players build Energy by holding sneak, so charging up leaves you exposed.

## Kits

| Kit | Ability | Energy |
|---|---|---|
| Wizard | A wand: left-click switches spell, right-click casts a fireball or wind charge | 10 / 5 |
| Fireball Launcher | Shoots a fireball where you look | 50 |
| Wind Rider | Fires a burst of wind charges for speed and knockback | 10 |
| Grappler | A grappling hook that anchors to blocks and pulls you in | none |
| Beast Master | Summons a tamed horse (left-click) or a wolf (right-click) | 100 / 50 |
| Miner | Haste, and double drops from ores | 100 |

Snow Miser, Heat Miser and Berserker appear in the menu but don't have abilities yet.

## Code

| Path | What's there |
|---|---|
| [`PleiadesPVP.java`](src/main/java/me/Pleiades/pleiadesPVP/PleiadesPVP.java) | Plugin entry point: registers commands, listeners and the Velocity channel |
| [`handlers/GameStateChanger.java`](src/main/java/me/Pleiades/pleiadesPVP/handlers/GameStateChanger.java) | Pre-game and in-game states, `/startgame`, deaths and spectating |
| [`handlers/`](src/main/java/me/Pleiades/pleiadesPVP/handlers) | One listener per kit, plus the Energy charge loop |
| [`abilities/`](src/main/java/me/Pleiades/pleiadesPVP/abilities) | The abilities themselves (projectiles, grappling hook, summons, mining) |
| [`World/`](src/main/java/me/Pleiades/pleiadesPVP/World) | World border and world deletion |
| [`commands/Menu.java`](src/main/java/me/Pleiades/pleiadesPVP/commands/Menu.java) | The kit selector inventory menu |

## Building

```sh
mvn package
```

Copy the jar from `target/` into the game server's `plugins` folder. The plugin expects a world named `gameWorld`.

| Command | What it does |
|---|---|
| `/menu` | Opens the kit selector |
| `/startgame` | Starts the round |
| `/createenergyscore` | Creates the Energy scoreboard |
| `/fly` | Toggles flight (needs the `fly` permission) |
