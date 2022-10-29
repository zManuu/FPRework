package de.manu.fprework.handler;

import de.manu.fprework.utils.Constants;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class PedHandler {

    @NotNull
    public static NPC createVillagerPed(@NotNull Location location, @NotNull String name) {
        var skin = Constants.NPC_VILLAGER_SKINS[ThreadLocalRandom.current().nextInt(Constants.NPC_VILLAGER_SKINS.length)];
        var npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
        npc.setProtected(true);
        npc.setUseMinecraftAI(false);
        npc.getOrAddTrait(SkinTrait.class).setSkinName(skin);
        npc.spawn(location);
        return npc;
    }

}
