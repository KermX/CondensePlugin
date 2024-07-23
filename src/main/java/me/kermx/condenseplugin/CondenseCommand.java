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

import static me.kermx.condenseplugin.MaterialMapping.getMainInventory;
import static me.kermx.condenseplugin.MaterialMapping.giveItem;

public class CondenseCommand implements CommandExecutor, TabCompleter {

    private MaterialMapping materialMapping;

    public CondenseCommand() {
        materialMapping = new MaterialMapping();
    }

    // Todo send message if nothing to condense
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            condenseReversibleItems(player);
            player.sendMessage("Condensed all reversible recipes items inventory.");
            return true;
        }
        if (args.length == 1) {
            String argument = args[0].toLowerCase();

            if ((argument.equals("inventory") || argument.equals("all") || argument.equals("inv"))) {
                condenseInventory(player);
                player.sendMessage("Condensed all eligible items in your inventory.");
                return true;
            } else {
                Material inputMaterial = Material.matchMaterial(args[0]);
                if (inputMaterial == null || !materialMapping.getRecipes().containsKey(inputMaterial)) {
                    player.sendMessage("Invalid item or no condensing recipe available for this item.");
                    return true;
                }

                // Check for special NBT tags on the input item
                ItemStack inputItem = new ItemStack(inputMaterial);
                if (!hasTags(inputItem)) {
                    int inputAmount = materialMapping.getRecipes().get(inputMaterial);
                    int inputCount = countItems(player.getInventory().getContents(), inputMaterial);

                    if (inputCount >= inputAmount) {
                        int condensedBlocks = inputCount / inputAmount;
                        int remainingItems = inputCount % inputAmount;

                        ItemStack inputStack = new ItemStack(inputMaterial, inputCount);
                        player.getInventory().removeItem(inputStack);

                        Material resultMaterial = materialMapping.getResultMaterial(inputMaterial, false);
                        giveItem(player, new ItemStack(resultMaterial, condensedBlocks));
                        giveItem(player, new ItemStack(inputMaterial, remainingItems));
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
                if (!hasTags(item)) {
                    count += item.getAmount();
                }
            }
        }
        return count;
    }

    private void condenseInventory(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack[] contents = getMainInventory(player);


        for (Material material : materialMapping.getRecipes().keySet()) {
            int inputAmount = materialMapping.getRecipes().get(material);
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

                Material resultMaterial = materialMapping.getResultMaterial(material, false);
                ItemStack resultStack = new ItemStack(resultMaterial, condensedBlocks);

                // Check if the condensed item has a corresponding empty bottle
                if (materialMapping.getGiveBackEmptyMappings().containsKey(material)) {
                    Material emptyItemMaterial = materialMapping.getGiveBackEmptyMappings().get(material);
                    int emptyItemAmount = count - remainingItems;
                    ItemStack emptyItemStack = new ItemStack(emptyItemMaterial, emptyItemAmount);
                    giveItem(player, emptyItemStack);
                }

                giveItem(player, resultStack);
                giveItem(player, new ItemStack(material, remainingItems));
            }
        }
    }

    private void condenseReversibleItems(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack[] contents = getMainInventory(player);

        for (Material material : materialMapping.getReversibleRecipes().keySet()) {
            int requiredAmount = materialMapping.getReversibleRecipes().get(material);
            int amountInInventory = 0;

            for (ItemStack item : contents) {
                if (item != null && item.getType() == material && !hasTags(item)) {
                    amountInInventory += item.getAmount();
                }
            }

            if (amountInInventory >= requiredAmount) {
                int condensedBlocks = amountInInventory / requiredAmount;
                int remainingItems = amountInInventory % requiredAmount;

                ItemStack inputStack = new ItemStack(material, amountInInventory);
                playerInventory.removeItem(inputStack);

                Material resultMaterial = materialMapping.getResultMaterial(material, false);
                giveItem(player, new ItemStack(resultMaterial, condensedBlocks));
                giveItem(player, new ItemStack(material, remainingItems));
            }
        }
    }

    private boolean hasTags(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && (meta.hasDisplayName() || meta.hasLore());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            List<String> suggestions = new ArrayList<>();

            for (Material material : materialMapping.getRecipes().keySet()) {
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
