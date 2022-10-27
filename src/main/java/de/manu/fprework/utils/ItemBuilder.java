/* Copyright 2016 Acquized
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.manu.fprework.utils;

import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * ItemBuilder - An API class to create an
 * {@link org.bukkit.inventory.ItemStack} with just one line of code!
 *
 * @version 1.8.3
 * @author Acquized
 * @contributor Kev575
 * @contributor zManuu
 */
public class ItemBuilder {

    private final ItemStack item;
    private ItemMeta meta;
    private Material material = Material.STONE;
    private int amount = 1;
    private MaterialData data;
    private short damage = 0;
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private String displayName;
    private List<String> lore = new ArrayList<>();
    private List<ItemFlag> flags = new ArrayList<>();

    private boolean andSymbol = true;
    private boolean unsafeStackSize = false;

    public ItemBuilder(@NotNull Material material) {
        this.item = new ItemStack(material);
        this.material = material;
    }

    public ItemBuilder(@NotNull Material material, int amount) {
        if (amount > material.getMaxStackSize() || amount <= 0) {
            amount = 1;
        }
        this.amount = amount;
        this.item = new ItemStack(material, amount);
        this.material = material;
    }

    public ItemBuilder(@NotNull Material material, int amount, @NotNull String displayName) {
        this.item = new ItemStack(material, amount);
        this.material = material;
        if (((amount > material.getMaxStackSize()) || (amount <= 0)) && (!unsafeStackSize)) {
            amount = 1;
        }
        this.amount = amount;
        this.displayName = displayName;
    }

    public ItemBuilder(@NotNull Material material, @NotNull String displayName) {
        this.item = new ItemStack(material);
        this.material = material;
        this.displayName = displayName;
    }

    @NotNull
    public <T, Z> ItemBuilder setPDCValue(@NotNull NamespacedKey key, @NotNull PersistentDataType<T, Z> type, Z value) {
        getMeta().getPersistentDataContainer().set(key, type, value);
        return this;
    }

    @NotNull
    public ItemBuilder amount(int amount) {
        if (((amount > material.getMaxStackSize()) || (amount <= 0)) && (!unsafeStackSize))
            amount = 1;
        this.amount = amount;
        return this;
    }

    @NotNull
    @Deprecated
    public ItemBuilder damage(short damage) {
        this.damage = damage;
        return this;
    }

    @NotNull
    public ItemBuilder durability(short damage) {
        this.damage = damage;
        return this;
    }

    @NotNull
    public ItemBuilder material(@NotNull Material material) {
        this.material = material;
        return this;
    }

    @NotNull
    public ItemBuilder meta(@NotNull ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    @NotNull
    public ItemBuilder enchant(@NotNull Enchantment enchant, int level) {
        getMeta().addEnchant(enchant, level, true);
        return this;
    }

    @NotNull
    public ItemBuilder enchant(@NotNull Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    @NotNull
    public ItemBuilder displayname(@NotNull String displayName) {
        this.displayName = andSymbol ? ChatColor.translateAlternateColorCodes('&', displayName) : displayName;
        return this;
    }

    @NotNull
    public ItemBuilder lore(@NotNull String line) {
        lore.add(andSymbol ? ChatColor.translateAlternateColorCodes('&', line) : line);
        return this;
    }

    @NotNull
    public ItemBuilder lore(@NotNull List<String> lore) {
        this.lore = lore;
        return this;
    }

    @NotNull
    @Deprecated
    public ItemBuilder lores(@NotNull String... lines) {
        for (String line : lines) {
            lore(andSymbol ? ChatColor.translateAlternateColorCodes('&', line) : line);
        }
        return this;
    }

    @NotNull
    public ItemBuilder lore(@NotNull String... lines) {
        for (String line : lines) {
            lore(andSymbol ? ChatColor.translateAlternateColorCodes('&', line) : line);
        }
        return this;
    }

    @NotNull
    public ItemBuilder lore(@NotNull String line, int index) {
        lore.set(index, andSymbol ? ChatColor.translateAlternateColorCodes('&', line) : line);
        return this;
    }

    @NotNull
    public ItemBuilder flag(@NotNull ItemFlag flag) {
        flags.add(flag);
        return this;
    }

    @NotNull
    public ItemBuilder flag(@NotNull List<ItemFlag> flags) {
        this.flags = flags;
        return this;
    }

    @NotNull
    public ItemBuilder unbreakable(boolean unbreakable) {
        getMeta().setUnbreakable(unbreakable);
        return this;
    }

    @NotNull
    public ItemBuilder attributeModifier(@NotNull Attribute attribute, double amount, @NotNull AttributeModifier.Operation operation) {
        getMeta().addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), "___", amount, operation));
        return this;
    }

    @NotNull
    public ItemBuilder attributeModifier(@NotNull Attribute attribute, double amount) {
        return attributeModifier(attribute, amount, AttributeModifier.Operation.ADD_NUMBER);
    }

    @NotNull
    public ItemBuilder glow() {
        enchant(material != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK, 10);
        flag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    @NotNull
    @Deprecated
    public ItemBuilder replaceAndSymbol() {
        replaceAndSymbol(!andSymbol);
        return this;
    }

    @NotNull
    public ItemBuilder replaceAndSymbol(boolean replace) {
        andSymbol = replace;
        return this;
    }

    @NotNull
    public ItemBuilder toggleReplaceAndSymbol() {
        replaceAndSymbol(!andSymbol);
        return this;
    }

    @NotNull
    public ItemBuilder unsafeStackSize(boolean allow) {
        this.unsafeStackSize = allow;
        return this;
    }

    @NotNull
    public ItemBuilder toggleUnsafeStackSize() {
        unsafeStackSize(!unsafeStackSize);
        return this;
    }

    @Nullable
    public String getDisplayName() {
        return displayName;
    }

    public int getAmount() {
        return amount;
    }

    @Nullable
    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    @Deprecated
    public short getDamage() {
        return damage;
    }

    public short getDurability() {
        return damage;
    }

    @Nullable
    public List<String> getLores() {
        return lore;
    }

    public boolean getAndSymbol() {
        return andSymbol;
    }

    @Nullable
    public List<ItemFlag> getFlags() {
        return flags;
    }

    @NotNull
    public Material getMaterial() {
        return material;
    }

    @NotNull
    public ItemMeta getMeta() {
        if (this.meta == null)
            this.meta = item.getItemMeta();
        assert this.meta != null;
        return this.meta;
    }

    @Nullable
    public MaterialData getData() {
        return data;
    }

    @Nullable
    @Deprecated
    public List<String> getLore() {
        return lore;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    @NotNull
    public static ItemBuilder fromJson(String json) {
        return new Gson().fromJson(json, ItemBuilder.class);
    }

    @NotNull
    public ItemStack build() {
        item.setType(material);
        item.setAmount(amount);
        item.setDurability(damage);

        if (meta == null) meta = item.getItemMeta();
        assert meta != null;

        if (data != null)
            item.setData(data);

        if (enchantments.size() > 0)
            item.addUnsafeEnchantments(enchantments);

        if (displayName != null)
            meta.setDisplayName(displayName);

        if (lore.size() > 0)
            meta.setLore(lore);

        if (flags.size() > 0) {
            for (ItemFlag f : flags) {
                meta.addItemFlags(f);
            }
        }

        meta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_DYE,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE
        );

        item.setItemMeta(meta);
        return item;
    }
}
