package catserver.server.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class NBTUtils {
    public static String itemStackToGiveCommand(ItemStack nmsItemStack) {
        ResourceLocation itemRl = Item.REGISTRY.getNameForObject(nmsItemStack.getItem());
        String itemNamespacedId = itemRl != null ? itemRl.toString() : "";
        if (itemNamespacedId.isBlank()) return "";

        int count = nmsItemStack.getCount();

        NBTTagCompound tag = nmsItemStack.getTagCompound();
        String nbtData = tag != null ? tag.toString() : "";

        StringBuilder command = new StringBuilder();
        command.append("/minecraft:give ").append("@s").append(" ");
        command.append(itemNamespacedId).append(" ").append(count);
        command.append(" ").append(nmsItemStack.getItemDamage());

        if (!nbtData.isEmpty()) {
            command.append(" ").append(nbtData);
        }

        return command.toString();
    }

    // Took from CraftTweaker
    public static String formatNbtToPrettyString(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderNonColor = new StringBuilder();
        int currentIndent = 0;

        boolean inQuotes = false;
        boolean isInValue = false;

        stringBuilder.append("\u00A7e\u251c");
        stringBuilderNonColor.append("\n\u251c");


        for (int i = 0; i < string.length(); i++) {
            char[] cArray = new char[1];
            string.getChars(i, i + 1, cArray, 0);
            char c = cArray[0];

            switch (c) {
                case '"':
                    inQuotes = !inQuotes;
                    stringBuilder.append("\u00A73" + '"');
                    stringBuilderNonColor.append(c);

                    break;
                case '{':
                case '[':
                    if (!inQuotes) {
                        currentIndent++;
                        isInValue = false;

                        stringBuilder.append("\u00A72").append(c);
                        addNewLine(stringBuilder, currentIndent);

                        stringBuilderNonColor.append(c);
                        addNewLineNoColor(stringBuilderNonColor, currentIndent);
                    } else {
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes) {
                        currentIndent--;
                        isInValue = false;
                        addNewLine(stringBuilder, currentIndent);
                        stringBuilder.append("\u00A72").append(c);

                        addNewLineNoColor(stringBuilderNonColor, currentIndent);
                        stringBuilderNonColor.append(c);
                    } else {
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case ',':
                    if (!inQuotes) {
                        isInValue = false;
                        stringBuilder.append("\u00A72,");
                        addNewLine(stringBuilder, currentIndent);

                        stringBuilderNonColor.append(c);
                        addNewLineNoColor(stringBuilderNonColor, currentIndent);

                    } else {
                        stringBuilder.append("\u00A7b,");
                        stringBuilderNonColor.append(c);

                    }
                    break;
                case ':':
                    if (!inQuotes) {
                        isInValue = true;
                        stringBuilder.append("\u00A72").append(c);
                        stringBuilderNonColor.append(c);

                    } else {
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    }
                    break;
                default:
                    if (inQuotes) {
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    } else if (isInValue) {
                        stringBuilder.append("\u00A7b").append(c);
                        stringBuilderNonColor.append(c);

                    } else {
                        stringBuilder.append(c);
                        stringBuilderNonColor.append(c);
                    }
            }

        }

        return stringBuilder.toString();
    }

    private static void addNewLine(StringBuilder s, int indent) {
        s.append("\n\u00A7e\u251c");
        s.append("\u00A7e    ".repeat(Math.max(0, indent)));
    }

    private static void addNewLineNoColor(StringBuilder s, int indent) {
        s.append("\n\u251c");
        s.append("    ".repeat(Math.max(0, indent)));
    }
}
