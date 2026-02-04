# Fast Server Start Plugin

A small Paper 1.21.1 plugin that speeds up server startup by disabling
spawn chunk retention during the initial boot. Optionally, the plugin can
re-enable spawn chunk retention after a short delay once the server is up.

## How it works
- Disables `keepSpawnInMemory` on all loaded worlds at startup.
- Optionally re-enables `keepSpawnInMemory` after a configurable delay.

## Configuration
Edit `config.yml` after first run:

```yaml
startup:
  disable-spawn-keep-loaded: true
  reenable-keep-spawn-after-delay: false
  deferred-keep-spawn-delay-ticks: 100
```

## Build

```bash
./gradlew build
```

The jar will be in `build/libs/`.
