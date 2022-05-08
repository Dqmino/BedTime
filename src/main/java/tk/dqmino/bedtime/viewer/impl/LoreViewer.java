package tk.dqmino.bedtime.viewer.impl;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import tk.dqmino.bedtime.viewer.Viewer;

import java.util.ArrayList;

public class LoreViewer implements Viewer<ItemStack, ArrayList<String>> {

    private static LoreViewer instance;

    private LoreViewer() {
    }

    public static LoreViewer getLoreViewer() {
        if (instance == null) {
            instance = new LoreViewer();
        }
        return instance;
    }

    @Override
    public ArrayList<String> view(ItemStack itemStack) {
        ArrayList<String> lore = new ArrayList<String>();
        if (itemStack == null) return lore;
        NBTTagCompound tag = itemStack.getTagCompound();
        if (tag != null) {
            NBTTagCompound display = tag.getCompoundTag("display");
            if (display.hasKey("Lore", 9)) {
                NBTTagList list = display.getTagList("Lore", 8);
                for (int i = 0; i < list.tagCount(); i++) {
                    lore.add(list.getStringTagAt(i));
                }
            }
        }
        return lore;
    }
}