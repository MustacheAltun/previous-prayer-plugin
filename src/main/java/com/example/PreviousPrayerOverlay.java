package com.example;

import net.runelite.api.HeadIcon;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ImageComponent;

import javax.inject.Inject;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class PreviousPrayerOverlay extends OverlayPanel {
    private final PreviousPrayerPlugin plugin;
    private final PreviousPrayerConfig config;
    private final SpriteManager spriteManager;

    @Inject
    private PreviousPrayerOverlay(PreviousPrayerPlugin plugin, PreviousPrayerConfig config, SpriteManager spriteManager) {
        this.plugin = plugin;
        this.config = config;
        this.spriteManager = spriteManager;

        setPosition(OverlayPosition.TOP_LEFT);
        setClearChildren(true);

        // Allows Alt + Right-Click drag-to-resize natively
        setResizable(true);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        HeadIcon lastIcon = plugin.getPrayerHistory().peekFirst();
        if (lastIcon == null) {
            return null;
        }

        int spriteId = getSpriteIdForHeadIcon(lastIcon);
        if (spriteId == -1) {
            return null;
        }

        BufferedImage sprite = spriteManager.getSprite(spriteId, 0);
        if (sprite == null) {
            return null;
        }

        // Use manually dragged size if available, otherwise fall back to config slider
        Dimension preferredSize = getPreferredSize();
        int size;
        if (preferredSize != null && preferredSize.width > 0) {
            size = Math.min(preferredSize.width, preferredSize.height > 0 ? preferredSize.height : preferredSize.width);
        } else {
            size = Math.max(16, config.iconSize().getPixelSize());
        }

        Image scaledImage = sprite.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedScaled.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        panelComponent.getChildren().add(new ImageComponent(bufferedScaled));

        return super.render(graphics);
    }

    private int getSpriteIdForHeadIcon(HeadIcon icon) {
        switch (icon) {
            case MELEE:
                return SpriteID.PRAYER_PROTECT_FROM_MELEE;
            case RANGED:
                return SpriteID.PRAYER_PROTECT_FROM_MISSILES;
            case MAGIC:
                return SpriteID.PRAYER_PROTECT_FROM_MAGIC;
            case SMITE:
                return SpriteID.PRAYER_SMITE;
            case REDEMPTION:
                return SpriteID.PRAYER_REDEMPTION;
            case RETRIBUTION:
                return SpriteID.PRAYER_RETRIBUTION;
            default:
                return -1;
        }
    }
}