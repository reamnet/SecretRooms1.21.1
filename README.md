# Secret Rooms — NeoForge 1.21.1

Secret Rooms adds camouflage blocks, hidden doors and trapdoors, switches, redstone,
chests, one-way glass, the Switch Probe, and True Vision Goggles.

This branch targets **Minecraft 1.21.1**, **NeoForge 21.1.259**, and **Java 21**.
A full JDK is required. The checked-in wrapper supplies Gradle 9.2.1.

## Development

Run these commands from the repository root:

```sh
./gradlew build       # compile, run the tests, and package the mod
./gradlew test        # run the six persistence and resource-loading tests
./gradlew runData     # regenerate recipes, advancements, tags, and loot tables
./gradlew runClient   # launch the development client; requires a graphical display
./gradlew runServer   # launch the development server
```

On Windows, use `gradlew.bat`. Server startup uses Minecraft's normal EULA flow.
Generated data belongs in `src/generated/resources`; commit changes after running
`runData`. Its `.cache` directory is ignored. Runtime files are kept in `run/`.

The distributable is `build/libs/secretrooms-1.21.1-1.2.0.jar`. Install it in the
`mods` directory of a Minecraft 1.21.1 NeoForge installation.

## Port validation

The tests load the mod and its data with NeoForge's test runtime. They exercise
camouflage block-state and block-entity data, recursive mirror protection, chest
inventory saves, probe data components, recipes/tags/loot, and goggles properties.
The graphical client is a separate smoke check for models, textures, tooltips,
and rendering.
