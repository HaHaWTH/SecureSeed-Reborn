# SecureSeed-Reborn

State-of-the-art cryptography to protect your world seed against seed cracking tools.

## Description

This mod is a partial port of the SecureSeed mod for Minecraft 1.16, it changes vanilla 64-bit seed to a 1024-bit seed, making it impossible to crack the seed. Not only that, there are a lot of things changed related to how random generation works.

There is no link between the generation of different features. For example, a link between diamonds and clay.

The terrain and biomes stays the same. But all the ores, structures, villages, strongholds, spawners, clay patches, geodes, lava pools, slime chunks — would appear in different places from the original seed. Because they are generated with all the fancy technology from the Secure Seed mod. Meaning there is no way that players can abuse the seed to find ores or structures.

If you installed the mod with an existed save, only new chunks will be generated with SecureSeed.

## Supported versions

Minecraft 1.21 ~ 1.21.3 (Fabric)

## Credits

- [SecureSeed (Original Project)](https://github.com/Earthcomputer/SecureSeed)
- [Matter (Porting SecuredSeed to Paper)](https://github.com/plasmoapp/matter)
- [Leaf (Porting to Paper 1.21+)](https://github.com/Winds-Studio/Leaf)

## License

Original SecureSeed files(BLAKE2b hashing, Globals) are licensed under the MIT license.
The rest of the SecureSeed-Reborn is licensed under the GNU General Public License v3.