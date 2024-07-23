package me.kermx.condenseplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class MaterialMapping {

    public Map<Material, Integer> recipes = new HashMap<>();
    public Map<Material, Integer> irreversibleRecipes = new HashMap<>();
    public Map<Material, Integer> reversibleRecipes = new HashMap<>();
    public Map<Material, Material> giveBackEmptyMappings = new HashMap<>();
    public Map<Material, Material> materialMappings = new HashMap<>();
    public Map<Material, Material> irreversibleMaterialMappings = new HashMap<>();
    public Map<Material, Material> reversibleMaterialMappings = new HashMap<>();


    public MaterialMapping() {
        irreversibleRecipes.put(Material.QUARTZ, 4);
        irreversibleRecipes.put(Material.AMETHYST_SHARD, 4);
        irreversibleRecipes.put(Material.HONEY_BOTTLE, 4);
        irreversibleRecipes.put(Material.HONEYCOMB, 4);
        irreversibleRecipes.put(Material.PRISMARINE_SHARD, 4);
        irreversibleRecipes.put(Material.CLAY_BALL, 4);
        irreversibleRecipes.put(Material.BRICK, 4);
        irreversibleRecipes.put(Material.NETHER_BRICK, 4);
        irreversibleRecipes.put(Material.NETHER_WART, 9);
        irreversibleRecipes.put(Material.GLOWSTONE_DUST, 4);
        irreversibleRecipes.put(Material.STRING, 4);
        irreversibleRecipes.put(Material.SNOWBALL, 4);
        irreversibleRecipes.put(Material.BAMBOO, 9);
        irreversibleRecipes.put(Material.PACKED_ICE, 9);
        irreversibleRecipes.put(Material.ICE, 9);
        irreversibleRecipes.put(Material.POPPED_CHORUS_FRUIT, 4);
        irreversibleRecipes.put(Material.CHARCOAL, 9);

        reversibleRecipes.put(Material.BONE_MEAL, 9);
        reversibleRecipes.put(Material.WHEAT, 9);
        reversibleRecipes.put(Material.COAL, 9);
        reversibleRecipes.put(Material.RAW_IRON, 9);
        reversibleRecipes.put(Material.RAW_COPPER, 9);
        reversibleRecipes.put(Material.RAW_GOLD, 9);
        reversibleRecipes.put(Material.EMERALD, 9);
        reversibleRecipes.put(Material.LAPIS_LAZULI, 9);
        reversibleRecipes.put(Material.DIAMOND, 9);
        reversibleRecipes.put(Material.IRON_NUGGET, 9);
        reversibleRecipes.put(Material.GOLD_NUGGET, 9);
        reversibleRecipes.put(Material.IRON_INGOT, 9);
        reversibleRecipes.put(Material.GOLD_INGOT, 9);
        reversibleRecipes.put(Material.COPPER_INGOT, 9);
        reversibleRecipes.put(Material.SLIME_BALL, 9);
        reversibleRecipes.put(Material.NETHERITE_INGOT, 9);
        reversibleRecipes.put(Material.REDSTONE, 9);
        reversibleRecipes.put(Material.MELON_SLICE, 9);
        reversibleRecipes.put(Material.DRIED_KELP, 9);

        recipes.putAll(reversibleRecipes);
        recipes.putAll(irreversibleRecipes);

        giveBackEmptyMappings.put(Material.HONEY_BOTTLE, Material.GLASS_BOTTLE);

        irreversibleMaterialMappings.put(Material.QUARTZ, Material.QUARTZ_BLOCK);
        irreversibleMaterialMappings.put(Material.HONEYCOMB, Material.HONEYCOMB_BLOCK);
        irreversibleMaterialMappings.put(Material.HONEY_BOTTLE, Material.HONEY_BLOCK);
        irreversibleMaterialMappings.put(Material.CHARCOAL, Material.COAL_BLOCK);
        irreversibleMaterialMappings.put(Material.AMETHYST_SHARD, Material.AMETHYST_BLOCK);
        irreversibleMaterialMappings.put(Material.PRISMARINE_SHARD, Material.PRISMARINE);
        irreversibleMaterialMappings.put(Material.GLOWSTONE_DUST, Material.GLOWSTONE);
        irreversibleMaterialMappings.put(Material.NETHER_WART, Material.NETHER_WART_BLOCK);
        irreversibleMaterialMappings.put(Material.NETHER_BRICK, Material.NETHER_BRICKS);
        irreversibleMaterialMappings.put(Material.CLAY_BALL, Material.CLAY);
        irreversibleMaterialMappings.put(Material.BRICK, Material.BRICKS);
        irreversibleMaterialMappings.put(Material.SNOWBALL, Material.SNOW_BLOCK);
        irreversibleMaterialMappings.put(Material.STRING, Material.WHITE_WOOL);
        irreversibleMaterialMappings.put(Material.BAMBOO, Material.BAMBOO_BLOCK);
        irreversibleMaterialMappings.put(Material.PACKED_ICE, Material.BLUE_ICE);
        irreversibleMaterialMappings.put(Material.ICE, Material.PACKED_ICE);
        irreversibleMaterialMappings.put(Material.POPPED_CHORUS_FRUIT, Material.PURPUR_BLOCK);

        reversibleMaterialMappings.put(Material.COAL, Material.COAL_BLOCK);
        reversibleMaterialMappings.put(Material.RAW_GOLD, Material.RAW_GOLD_BLOCK);
        reversibleMaterialMappings.put(Material.COPPER_INGOT, Material.COPPER_BLOCK);
        reversibleMaterialMappings.put(Material.IRON_INGOT, Material.IRON_BLOCK);
        reversibleMaterialMappings.put(Material.LAPIS_LAZULI, Material.LAPIS_BLOCK);
        reversibleMaterialMappings.put(Material.IRON_NUGGET, Material.IRON_INGOT);
        reversibleMaterialMappings.put(Material.REDSTONE, Material.REDSTONE_BLOCK);
        reversibleMaterialMappings.put(Material.SLIME_BALL, Material.SLIME_BLOCK);
        reversibleMaterialMappings.put(Material.RAW_COPPER, Material.RAW_COPPER_BLOCK);
        reversibleMaterialMappings.put(Material.RAW_IRON, Material.RAW_IRON_BLOCK);
        reversibleMaterialMappings.put(Material.GOLD_INGOT, Material.GOLD_BLOCK);
        reversibleMaterialMappings.put(Material.EMERALD, Material.EMERALD_BLOCK);
        reversibleMaterialMappings.put(Material.DIAMOND, Material.DIAMOND_BLOCK);
        reversibleMaterialMappings.put(Material.GOLD_NUGGET, Material.GOLD_INGOT);
        reversibleMaterialMappings.put(Material.WHEAT, Material.HAY_BLOCK);
        reversibleMaterialMappings.put(Material.BONE_MEAL, Material.BONE_BLOCK);
        reversibleMaterialMappings.put(Material.DRIED_KELP, Material.DRIED_KELP_BLOCK);
        reversibleMaterialMappings.put(Material.NETHERITE_INGOT, Material.NETHERITE_BLOCK);
        reversibleMaterialMappings.put(Material.MELON_SLICE, Material.MELON);

        materialMappings.putAll(irreversibleMaterialMappings);
        materialMappings.putAll(reversibleMaterialMappings);
    }

    public Map<Material, Integer> getRecipes() {
        return recipes;
    }

    public Map<Material, Integer> getIrreversibleRecipes() {
        return irreversibleRecipes;
    }

    public Map<Material, Integer> getReversibleRecipes() {
        return reversibleRecipes;
    }

    public Map<Material, Material> getGiveBackEmptyMappings() {
        return giveBackEmptyMappings;
    }

    public Map<Material, Material> getMaterialMappings() {
        return materialMappings;
    }

    public Map<Material, Material> getIrreversibleMaterialMappings() {
        return irreversibleMaterialMappings;
    }

    public Map<Material, Material> getReversibleMaterialMappings(Boolean uncondense) {
        if (uncondense) {
            return invertedMap(reversibleMaterialMappings);
        }
        return reversibleMaterialMappings;
    }

    public Material getResultMaterial(Material inputMaterial, Boolean uncondense) {
        if (uncondense) {
            // TODO implement if we really need it and be careful of the air??
        }
        // TODO Test commented return
        // return materialMappings.get(inputMaterial);
        return materialMappings.getOrDefault(inputMaterial, Material.AIR);
    }

    private static <V, K> Map<V, K> invertedMap(Map<K, V> map) {
        Map<V, K> inversedMap = new HashMap<V, K>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            inversedMap.put(entry.getValue(), entry.getKey());
        }
        return inversedMap;
    }

    public static ItemStack[] getMainInventory(Player player) {
        PlayerInventory inventory = player.getInventory();

        // The main inventory is from slot 0 to 35 (inclusive)
        ItemStack[] mainInventory = new ItemStack[36];
        for (int i = 0; i < 36; i++) {
            mainInventory[i] = inventory.getItem(i);
        }

        return mainInventory;
    }

    /**
     * Tries to give an item to a player. If the player's inventory is full,
     * the remaining items are dropped on the ground at the player's location.
     *
     * @param player The player to give the item to.
     * @param itemStack The item stack to give to the player.
     */
    public static void giveItem(Player player, ItemStack itemStack) {
        // Try to add the item to the player's inventory
        HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(itemStack);

        // If there are remaining items, drop them on the ground
        if (!remainingItems.isEmpty()) {
            Location location = player.getLocation();
            for (ItemStack remainingItem : remainingItems.values()) {
                if (remainingItem != null) {
                    Item droppedItem = player.getWorld().dropItem(location, remainingItem);
                    droppedItem.setPickupDelay(0); // Optional: allow the player to pick up the item immediately
                }
            }
        }
    }

}
