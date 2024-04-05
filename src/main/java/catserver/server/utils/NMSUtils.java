package catserver.server.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

public class NMSUtils {
    public static WorldServer toNMS(org.bukkit.World world) {
        return ((CraftWorld) world).getHandle();
    }

    public static EntityPlayerMP toNMS(org.bukkit.entity.Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static Entity toNMS(org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }
}
