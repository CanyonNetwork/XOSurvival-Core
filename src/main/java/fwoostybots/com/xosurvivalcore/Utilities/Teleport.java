package fwoostybots.com.xosurvivalcore.Utilities;

import fwoostybots.com.xosurvivalcore.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;

public class Teleport {

    // List of unsafe blocks
    public static HashSet<Material> badBlocks = new HashSet<>();

    static {
        badBlocks.add(Material.LAVA);
        badBlocks.add(Material.FIRE);
        badBlocks.add(Material.CACTUS);
        badBlocks.add(Material.WATER);
    }

    public static Location generateLocation(Player player, World world) {
        // Generate random location
        Random random = new Random();

        int x = 0;
        int z = 0;
        int y = 0;

        if(Main.getInstance().getConfig().getBoolean("world-border")) {
            x = random.nextInt(Main.getInstance().getConfig().getInt("border"));
            z = random.nextInt(Main.getInstance().getConfig().getInt("border"));
            y = 150;
        }
        else {
            x = random.nextInt(25000);
            z = random.nextInt(25000);
            y = 150;
        }

        Location randomLocation = new Location(world, x, y, z).toCenterLocation().toHighestLocation().add(0, 1, 0);

        return randomLocation;
    }

    public static Location findSafeLocation(Player player, World world) {
        Location randomLocation = generateLocation(player, world);

        while (!isLocationSafe(randomLocation)) {
            // Keep looking for a safe location
            randomLocation = generateLocation(player, world);
        }

        return randomLocation;
    }

    public static boolean isLocationSafe(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        // Get instances of the blocks around where the player would spawn
        Block block = location.getWorld().getBlockAt(x, y, z);
        Block below = location.getWorld().getBlockAt(x, y - 1, z);
        Block above = location.getWorld().getBlockAt(x, y + 1, z);

        return (!(badBlocks.contains(below.getType())) || (block.getType().isSolid()) || (above.getType().isSolid()));
    }

}
