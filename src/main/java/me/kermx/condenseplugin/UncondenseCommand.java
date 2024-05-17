package me.kermx.condenseplugin;

import org.bukkit.Bukkit;
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

public class UncondenseCommand implements CommandExecutor, TabCompleter {

    private MaterialMapping materialMapping;

    public UncondenseCommand() {
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
            uncondenseInventory(player);
            player.sendMessage("Uncondense all reversible recipes items inventory.");
            return true;
        }

        // TODO handle argument for specific item uncondense
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
                player.getInventory().addItem(new ItemStack(resultMaterial, condensedBlocks));
                player.getInventory().addItem(new ItemStack(inputMaterial, remainingItems));
                // player.sendMessage("Condensed " + condensedBlocks + " " + resultMaterial + " blocks.");
            } else {
                player.sendMessage("You don't have enough to condense.");
            }
        } else {
            player.sendMessage("Item cannot be condensed.");
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

    private void uncondenseInventory(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack[] contents = playerInventory.getContents();

        Map<Material, Material> reversibleMaterialMappings = materialMapping.getReversibleMaterialMappings(true);
        Map<Material, Integer> reversibleRecipes = materialMapping.getReversibleRecipes();

        for (Material material : reversibleMaterialMappings.keySet()) {
            Material resultMaterial = reversibleMaterialMappings.get(material);

            // Check if the resultMaterial exists in reversibleRecipes
            if (resultMaterial != null && reversibleRecipes.containsKey(resultMaterial)) {
                int outputAmount = reversibleRecipes.get(resultMaterial);
                int count = 0;

                for (ItemStack item : contents) {
                    if (item != null && item.getType() == material && !hasTags(item)) {
                        count += item.getAmount();
                    }
                }

                if (count >= 1) {
                    int uncondensedItems = count * outputAmount;

                    ItemStack inputStack = new ItemStack(material, count);
                    playerInventory.removeItem(inputStack);

                    ItemStack resultStack = new ItemStack(resultMaterial, uncondensedItems);
                    playerInventory.addItem(resultStack);
                    // TODO handle full inventory drop on the ground
                }
            } else {
                // Log or handle the case where the resultMaterial is not found in reversibleRecipes
                Bukkit.getLogger().warning("Material " + material + " does not have a corresponding recipe.");
            }
        }
    }

    private boolean hasTags(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && (meta.hasDisplayName() || meta.hasLore());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // TODO Fix tab completer
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
