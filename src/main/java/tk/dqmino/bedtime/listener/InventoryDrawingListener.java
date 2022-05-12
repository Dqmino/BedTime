package tk.dqmino.bedtime.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tk.dqmino.bedtime.config.Configuration;
import tk.dqmino.bedtime.viewer.impl.LoreViewer;

import java.util.ArrayList;

public class InventoryDrawingListener {

    private final Configuration config;

    public InventoryDrawingListener(Configuration config) {
        this.config = config;
    }

    @SubscribeEvent
    public void onGuiDraw(final GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!config.isModEnabled()) return;
        if (!(event.gui instanceof GuiChest)) return;

        final ContainerChest container = (ContainerChest) ((GuiChest) event.gui).inventorySlots;
        final String guiName = container.getLowerChestInventory().getName();

        if (!guiName.contains("BIN Auction View")) return;

        final ItemStack auctionedItem = ((GuiChest) event.gui).inventorySlots.getSlot(13).getStack();

        final LoreViewer viewer = LoreViewer.getLoreViewer();

        if (viewer == null || auctionedItem == null) return;

        final ArrayList<String> lore = viewer.view(auctionedItem);

        if (lore.isEmpty()) return;

        String lineIndicatingAuctionState = lore.get(lore.size() - 1);

        if (lineIndicatingAuctionState == null || lineIndicatingAuctionState.isEmpty()) return;

        if (!lineIndicatingAuctionState.contains("Can buy")) {
            lineIndicatingAuctionState = lore.get(lore.size() - 3);
        }

        if (lineIndicatingAuctionState == null || !lineIndicatingAuctionState.contains("Can buy"))
            return;

        final double size = config.getSize();
        final int x = config.getX();
        final int y = config.getY();
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

        if (lineIndicatingAuctionState.contains("Soon")) {
            write(EnumChatFormatting.YELLOW + "Soon!!", size, x, y, event.gui, fontRenderer);
            return;
        }

        final int timeLeftToBuy = Integer.parseInt(lineIndicatingAuctionState
                .replaceAll("[\\D]", ""));

        String whatToWrite;

        final EnumChatFormatting color = timeLeftToBuy > 6 ? EnumChatFormatting.GREEN : EnumChatFormatting.DARK_RED;

        if (String.valueOf(timeLeftToBuy).length() >= 3) {
            whatToWrite = color + String.valueOf(timeLeftToBuy).substring(1, 3) + "s";
        } else {
            whatToWrite = color + String.valueOf(timeLeftToBuy) + "s";
        }

        write(whatToWrite, size, x, y, event.gui, fontRenderer);
    }

    private void write(String whatToWrite, double size,
                       int x, int y, GuiScreen gui, FontRenderer fontRenderer) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(size, size, 1.0);
        gui.drawString(fontRenderer, whatToWrite, x, y, Integer.MAX_VALUE);
        GlStateManager.popMatrix();
    }
}