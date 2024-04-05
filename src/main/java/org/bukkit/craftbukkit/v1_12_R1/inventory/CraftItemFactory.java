package org.bukkit.craftbukkit.v1_12_R1.inventory;

import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.ImmutableSet;

public final class CraftItemFactory implements ItemFactory {
    static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(0xA06540);
    static final Collection<String> KNOWN_NBT_ATTRIBUTE_NAMES;
    private static final CraftItemFactory instance;

    static {
        instance = new CraftItemFactory();
        ConfigurationSerialization.registerClass(CraftMetaItem.SerializableMeta.class);
        KNOWN_NBT_ATTRIBUTE_NAMES = ImmutableSet.<String>builder()
            .add("generic.armor")
            .add("generic.armorToughness")
            .add("generic.attackDamage")
            .add("generic.followRange")
            .add("generic.knockbackResistance")
            .add("generic.maxHealth")
            .add("generic.movementSpeed")
            .add("generic.flyingSpeed")
            .add("generic.attackSpeed")
            .add("generic.luck")
            .add("horse.jumpStrength")
            .add("zombie.spawnReinforcements")
            .add("generic.reachDistance")
            .build();
    }

    private CraftItemFactory() {
    }

    public boolean isApplicable(ItemMeta meta, ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        return isApplicable(meta, itemstack.getType());
    }

    public boolean isApplicable(ItemMeta meta, Material type) {
        if (type == null || meta == null) {
            return false;
        }
        if (!(meta instanceof CraftMetaItem)) {
            throw new IllegalArgumentException("Meta of " + meta.getClass().toString() + " not created by " + CraftItemFactory.class.getName());
        }

        return ((CraftMetaItem) meta).applicableTo(type);
    }

    public ItemMeta getItemMeta(Material material) {
        Validate.notNull(material, "Material cannot be null");
        return getItemMeta(material, null);
    }

    private ItemMeta getItemMeta(Material material, CraftMetaItem meta) {
        if (Objects.requireNonNull(material) == Material.AIR) {
            return null;
        } else if (material == Material.WRITTEN_BOOK) {
            return meta instanceof CraftMetaBookSigned ? meta : new CraftMetaBookSigned(meta);
        } else if (material == Material.BOOK_AND_QUILL) {
            return meta != null && meta.getClass().equals(CraftMetaBook.class) ? meta : new CraftMetaBook(meta);
        } else if (material == Material.SKULL_ITEM) {
            return meta instanceof CraftMetaSkull ? meta : new CraftMetaSkull(meta);
        } else if (material == Material.LEATHER_HELMET || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_BOOTS) {
            return meta instanceof CraftMetaLeatherArmor ? meta : new CraftMetaLeatherArmor(meta);
        } else if (material == Material.POTION || material == Material.SPLASH_POTION || material == Material.LINGERING_POTION || material == Material.TIPPED_ARROW) {
            return meta instanceof CraftMetaPotion ? meta : new CraftMetaPotion(meta);
        } else if (material == Material.MAP) {
            return meta instanceof CraftMetaMap ? meta : new CraftMetaMap(meta);
        } else if (material == Material.FIREWORK) {
            return meta instanceof CraftMetaFirework ? meta : new CraftMetaFirework(meta);
        } else if (material == Material.FIREWORK_CHARGE) {
            return meta instanceof CraftMetaCharge ? meta : new CraftMetaCharge(meta);
        } else if (material == Material.ENCHANTED_BOOK) {
            return meta instanceof CraftMetaEnchantedBook ? meta : new CraftMetaEnchantedBook(meta);
        } else if (material == Material.BANNER) {
            return meta instanceof CraftMetaBanner ? meta : new CraftMetaBanner(meta);
        } else if (material == Material.MONSTER_EGG) {
            return meta instanceof CraftMetaSpawnEgg ? meta : new CraftMetaSpawnEgg(meta);
        } else if (material == Material.KNOWLEDGE_BOOK) {
            return meta instanceof CraftMetaKnowledgeBook ? meta : new CraftMetaKnowledgeBook(meta);
        } else if (material == Material.FURNACE || material == Material.CHEST || material == Material.TRAPPED_CHEST || material == Material.JUKEBOX || material == Material.DISPENSER || material == Material.DROPPER || material == Material.SIGN || material == Material.MOB_SPAWNER || material == Material.NOTE_BLOCK || material == Material.BREWING_STAND_ITEM || material == Material.ENCHANTMENT_TABLE || material == Material.COMMAND || material == Material.COMMAND_REPEATING || material == Material.COMMAND_CHAIN || material == Material.BEACON || material == Material.DAYLIGHT_DETECTOR || material == Material.DAYLIGHT_DETECTOR_INVERTED || material == Material.HOPPER || material == Material.REDSTONE_COMPARATOR || material == Material.FLOWER_POT_ITEM || material == Material.SHIELD || material == Material.STRUCTURE_BLOCK || material == Material.WHITE_SHULKER_BOX || material == Material.ORANGE_SHULKER_BOX || material == Material.MAGENTA_SHULKER_BOX || material == Material.LIGHT_BLUE_SHULKER_BOX || material == Material.YELLOW_SHULKER_BOX || material == Material.LIME_SHULKER_BOX || material == Material.PINK_SHULKER_BOX || material == Material.GRAY_SHULKER_BOX || material == Material.SILVER_SHULKER_BOX || material == Material.CYAN_SHULKER_BOX || material == Material.PURPLE_SHULKER_BOX || material == Material.BLUE_SHULKER_BOX || material == Material.BROWN_SHULKER_BOX || material == Material.GREEN_SHULKER_BOX || material == Material.RED_SHULKER_BOX || material == Material.BLACK_SHULKER_BOX || material == Material.ENDER_CHEST) {
            return new CraftMetaBlockState(meta, material);
        }
        return new CraftMetaItem(meta);
    }

    public boolean equals(ItemMeta meta1, ItemMeta meta2) {
        if (meta1 == meta2) {
            return true;
        }
        if (meta1 != null && !(meta1 instanceof CraftMetaItem)) {
            throw new IllegalArgumentException("First meta of " + meta1.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
        }
        if (meta2 != null && !(meta2 instanceof CraftMetaItem)) {
            throw new IllegalArgumentException("Second meta " + meta2.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
        }
        if (meta1 == null) {
            return ((CraftMetaItem) meta2).isEmpty();
        }
        if (meta2 == null) {
            return ((CraftMetaItem) meta1).isEmpty();
        }

        return equals((CraftMetaItem) meta1, (CraftMetaItem) meta2);
    }

    boolean equals(CraftMetaItem meta1, CraftMetaItem meta2) {
        /*
         * This couldn't be done inside of the objects themselves, else force recursion.
         * This is a fairly clean way of implementing it, by dividing the methods into purposes and letting each method perform its own function.
         *
         * The common and uncommon were split, as both could have variables not applicable to the other, like a skull and book.
         * Each object needs its chance to say "hey wait a minute, we're not equal," but without the redundancy of using the 1.equals(2) && 2.equals(1) checking the 'commons' twice.
         *
         * Doing it this way fills all conditions of the .equals() method.
         */
        return meta1.equalsCommon(meta2) && meta1.notUncommon(meta2) && meta2.notUncommon(meta1);
    }

    public static CraftItemFactory instance() {
        return instance;
    }

    public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) {
        Validate.notNull(stack, "Stack cannot be null");
        return asMetaFor(meta, stack.getType());
    }

    public ItemMeta asMetaFor(ItemMeta meta, Material material) {
        Validate.notNull(material, "Material cannot be null");
        if (!(meta instanceof CraftMetaItem)) {
            throw new IllegalArgumentException("Meta of " + (meta != null ? meta.getClass().toString() : "null") + " not created by " + CraftItemFactory.class.getName());
        }
        return getItemMeta(material, (CraftMetaItem) meta);
    }

    public Color getDefaultLeatherColor() {
        return DEFAULT_LEATHER_COLOR;
    }
}
