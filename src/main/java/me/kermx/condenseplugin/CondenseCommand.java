package me.kermx.condenseplugin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CondenseCommand implements CommandExecutor, TabCompleter {

    private Map<Material, Integer> recipes = new HashMap<>();
    private Map<Material, Integer> reversibleRecipes = new HashMap<>();

    private Map<Material, Material> giveBackEmptyMappings = new HashMap<>();


    public CondenseCommand(){
        recipes.put(Material.QUARTZ, 4);
        recipes.put(Material.COAL, 9);
        recipes.put(Material.RAW_IRON, 9);
        recipes.put(Material.CHARCOAL, 9);
        recipes.put(Material.RAW_COPPER, 9);
        recipes.put(Material.RAW_GOLD, 9);
        recipes.put(Material.EMERALD, 9);
        recipes.put(Material.LAPIS_LAZULI, 9);
        recipes.put(Material.DIAMOND, 9);
        recipes.put(Material.AMETHYST_SHARD, 4);
        recipes.put(Material.IRON_NUGGET, 9);
        recipes.put(Material.GOLD_NUGGET, 9);
        recipes.put(Material.IRON_INGOT, 9);
        recipes.put(Material.GOLD_INGOT, 9);
        recipes.put(Material.COPPER_INGOT, 9);
        recipes.put(Material.SLIME_BALL, 9);
        recipes.put(Material.HONEY_BOTTLE, 4);
        recipes.put(Material.HONEYCOMB, 4);
        recipes.put(Material.PRISMARINE_SHARD, 4);
        recipes.put(Material.NETHERITE_INGOT, 9);
        recipes.put(Material.CLAY_BALL, 4);
        recipes.put(Material.BRICK, 4);
        recipes.put(Material.NETHER_BRICK, 4);
        recipes.put(Material.NETHER_WART, 9);
        recipes.put(Material.GLOWSTONE_DUST, 4);
        recipes.put(Material.DRIED_KELP, 9);
        recipes.put(Material.REDSTONE, 9);
        recipes.put(Material.MELON_SLICE, 9);
        recipes.put(Material.STRING, 4);
        recipes.put(Material.SNOWBALL, 4);
        recipes.put(Material.BAMBOO, 9);
        recipes.put(Material.PACKED_ICE, 9);
        recipes.put(Material.ICE, 9);
        recipes.put(Material.POPPED_CHORUS_FRUIT, 4);
        recipes.put(Material.WHEAT, 9);
        recipes.put(Material.BONE_MEAL, 9);

        reversibleRecipes.put(Material.BONE_MEAL, 9);
        reversibleRecipes.put(Material.WHEAT, 9);
        reversibleRecipes.put(Material.COAL, 9);
        reversibleRecipes.put(Material.RAW_IRON, 9);
        reversibleRecipes.put(Material.CHARCOAL, 9);
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

        giveBackEmptyMappings.put(Material.HONEY_BOTTLE, Material.GLASS_BOTTLE);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            condenseInventory(player);
            return true;
        }
        if (args.length == 1) {
            String argument = args[0].toLowerCase();

            if (argument.equals("reversible")){
                condenseReversibleItems(player);
                return true;
            } else if ((argument.equals("inventory") || argument.equals("all") || argument.equals("inv"))){
                condenseInventory(player);
                //player.sendMessage("Condensed all eligible items in your inventory.");
                return true;
            } else {
                Material inputMaterial = Material.matchMaterial(args[0]);
                if (inputMaterial == null || !recipes.containsKey(inputMaterial)) {
                    player.sendMessage("Invalid item or no condensing recipe available for this item.");
                    return true;
                }

                // Check for special NBT tags on the input item
                ItemStack inputItem = new ItemStack(inputMaterial);
                if (!hasTags(inputItem)) {
                    int inputAmount = recipes.get(inputMaterial);
                    int inputCount = countItems(player.getInventory().getContents(), inputMaterial);

                    if (inputCount >= inputAmount) {
                        int condensedBlocks = inputCount / inputAmount;
                        int remainingItems = inputCount % inputAmount;

                        ItemStack inputStack = new ItemStack(inputMaterial, inputCount);
                        player.getInventory().removeItem(inputStack);

                        Material resultMaterial = getResultMaterial(inputMaterial);
                        player.getInventory().addItem(new ItemStack(resultMaterial, condensedBlocks));
                        player.getInventory().addItem(new ItemStack(inputMaterial, remainingItems));
                        // player.sendMessage("Condensed " + condensedBlocks + " " + resultMaterial + " blocks.");
                    } else {
                        player.sendMessage("You don't have enough to condense.");
                    }
                } else {
                    player.sendMessage("Item cannot be condensed.");
                }
            }
        }
        return true;
    }


    private int countItems(ItemStack[] items, Material material) {
        int count = 0;
        for (ItemStack item : items) {
            if (item != null && item.getType() == material) {
                if (!hasTags(item)){
                    count += item.getAmount();
                }
            }
        }
        return count;
    }

    private Material getResultMaterial(Material inputMaterial) {
        Map<Material, Material> materialMappings = new HashMap<>();
        materialMappings.put(Material.QUARTZ, Material.QUARTZ_BLOCK);
        materialMappings.put(Material.COAL, Material.COAL_BLOCK);
        materialMappings.put(Material.HONEYCOMB, Material.HONEYCOMB_BLOCK);
        materialMappings.put(Material.HONEY_BOTTLE, Material.HONEY_BLOCK);
        materialMappings.put(Material.CHARCOAL, Material.COAL_BLOCK);
        materialMappings.put(Material.RAW_GOLD, Material.RAW_GOLD_BLOCK);
        materialMappings.put(Material.COPPER_INGOT, Material.COPPER_BLOCK);
        materialMappings.put(Material.IRON_INGOT, Material.IRON_BLOCK);
        materialMappings.put(Material.LAPIS_LAZULI, Material.LAPIS_BLOCK);
        materialMappings.put(Material.AMETHYST_SHARD, Material.AMETHYST_BLOCK);
        materialMappings.put(Material.IRON_NUGGET, Material.IRON_INGOT);
        materialMappings.put(Material.REDSTONE, Material.REDSTONE_BLOCK);
        materialMappings.put(Material.SLIME_BALL, Material.SLIME_BLOCK);
        materialMappings.put(Material.PRISMARINE_SHARD, Material.PRISMARINE);
        materialMappings.put(Material.RAW_COPPER, Material.RAW_COPPER_BLOCK);
        materialMappings.put(Material.RAW_IRON, Material.RAW_IRON_BLOCK);
        materialMappings.put(Material.GOLD_INGOT, Material.GOLD_BLOCK);
        materialMappings.put(Material.EMERALD, Material.EMERALD_BLOCK);
        materialMappings.put(Material.DIAMOND, Material.DIAMOND_BLOCK);
        materialMappings.put(Material.GOLD_NUGGET, Material.GOLD_INGOT);
        materialMappings.put(Material.WHEAT, Material.HAY_BLOCK);
        materialMappings.put(Material.BONE_MEAL, Material.BONE_BLOCK);
        materialMappings.put(Material.DRIED_KELP, Material.DRIED_KELP_BLOCK);
        materialMappings.put(Material.GLOWSTONE_DUST, Material.GLOWSTONE);
        materialMappings.put(Material.NETHER_WART, Material.NETHER_WART_BLOCK);
        materialMappings.put(Material.NETHER_BRICK, Material.NETHER_BRICKS);
        materialMappings.put(Material.BRICK, Material.BRICKS);
        materialMappings.put(Material.CLAY_BALL, Material.CLAY);
        materialMappings.put(Material.NETHERITE_INGOT, Material.NETHERITE_BLOCK);
        materialMappings.put(Material.MELON_SLICE, Material.MELON);
        materialMappings.put(Material.STRING, Material.WHITE_WOOL);
        materialMappings.put(Material.SNOWBALL, Material.SNOW_BLOCK);
        materialMappings.put(Material.BAMBOO, Material.BAMBOO_BLOCK);
        materialMappings.put(Material.PACKED_ICE, Material.BLUE_ICE);
        materialMappings.put(Material.ICE, Material.PACKED_ICE);
        materialMappings.put(Material.POPPED_CHORUS_FRUIT, Material.PURPUR_BLOCK);


        return materialMappings.getOrDefault(inputMaterial, Material.AIR);
    }

    private void condenseInventory(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack[] contents = playerInventory.getContents();

        for (Material material : recipes.keySet()) {
            int inputAmount = recipes.get(material);
            int count = 0;

            for (ItemStack item : contents) {
                if (item != null && item.getType() == material && !hasTags(item)) {
                    count += item.getAmount();
                }
            }

            if (count >= inputAmount) {
                int condensedBlocks = count / inputAmount;
                int remainingItems = count % inputAmount;

                ItemStack inputStack = new ItemStack(material, count);
                playerInventory.removeItem(inputStack);

                Material resultMaterial = getResultMaterial(material);
                ItemStack resultStack = new ItemStack(resultMaterial, condensedBlocks);

                // Check if the condensed item has a corresponding empty bottle
                if (giveBackEmptyMappings.containsKey(material)) {
                    Material emptyItemMaterial = giveBackEmptyMappings.get(material);
                    int emptyItemAmount = count - remainingItems;
                    ItemStack emptyItemStack = new ItemStack(emptyItemMaterial, emptyItemAmount);
                    playerInventory.addItem(emptyItemStack);
                }

                playerInventory.addItem(resultStack);
                playerInventory.addItem(new ItemStack(material, remainingItems));
            }
        }
    }

    private void condenseReversibleItems(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack[] contents = playerInventory.getContents();

        for (Material material : reversibleRecipes.keySet()) {
            int inputAmount = reversibleRecipes.get(material);
            int count = 0;

            for (ItemStack item : contents) {
                if (item != null && item.getType() == material && !hasTags(item)) {
                    count += item.getAmount();
                }
            }

            if (count >= inputAmount) {
                int condensedBlocks = count / inputAmount;
                int remainingItems = count % inputAmount;

                ItemStack inputStack = new ItemStack(material, count);
                playerInventory.removeItem(inputStack);

                Material resultMaterial = getResultMaterial(material);
                playerInventory.addItem(new ItemStack(resultMaterial, condensedBlocks));
                playerInventory.addItem(new ItemStack(material, remainingItems));
            }
        }
    }

    private boolean hasTags(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        return meta != null && (meta.hasDisplayName() || meta.hasLore());
    }

    private List<String> getCondensableItems(ItemStack[] inventory) {
        Set<String> uniqueItems = new HashSet<>();

        for (ItemStack item : inventory) {
            if (item != null && recipes.containsKey(item.getType())) {
                uniqueItems.add(item.getType().name());
            }
        }
        return new ArrayList<>(uniqueItems);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            List<String> suggestions = new ArrayList<>();

            for (Material material : recipes.keySet()) {
                if (material.name().toLowerCase().startsWith(input)) {
                    suggestions.add("all");
                    suggestions.add("reversible");
                    suggestions.add(material.name());
                }
            }
            return suggestions;
        }
        return null; // Return null for default behavior
    }
}
