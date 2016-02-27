#MassiveCore Forge Fixes
This simple plugin fixes game-breaking issues with Forge and MassiveCore.

Fixes include:
* Sender assigning issues with MassiveCore
* Double "Wilderness --> Faction" messages (by @DraksterAU)

##Downloading & Installation 
You can download MassiveCore Forge Fixes from the releases section:
https://github.com/MarkehMe/MassiveCoreForgeFixes/releases

To install, simply place in your plugins folder, along with the latest version of MassiveCore and your other MassiveCraft plugins. Always use the latest versions! 

##What it doesn't fix
### ! ParticleEffect Library error codes
This is because your Bukkit version is out of date with the latest version of Minecraft. This bug is not game-breaking, and you should instead disable title messages. 

To do this, you should be able to change ```territoryInfoTitlesDefault``` in your mconf to false. (Currently waiting on MassiveCraft to implement a fix to check for configuration option earlier) 