package com.example;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.HeadIcon;
import net.runelite.api.Prayer;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.Deque;

@Slf4j
@PluginDescriptor(name = "Previous Prayer Tracker")
public class PreviousPrayerPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private PreviousPrayerConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private PreviousPrayerOverlay overlay;

    private final Deque<HeadIcon> prayerHistory = new ArrayDeque<>();
    private HeadIcon currentActiveIcon = null;
    private HeadIcon storedPreviousIcon = null;

    public Deque<HeadIcon> getPrayerHistory() {
        return prayerHistory;
    }

    @Provides
    PreviousPrayerConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(PreviousPrayerConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(overlay);
        prayerHistory.clear();
        currentActiveIcon = null;
        storedPreviousIcon = null;
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
        prayerHistory.clear();
    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged event) {
        processPrayerUpdate();
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        processPrayerUpdate();
    }

    private void processPrayerUpdate() {
        HeadIcon activeIcon = getActiveOverhead();

        if (activeIcon != currentActiveIcon) {
            HeadIcon oldIcon = currentActiveIcon;
            currentActiveIcon = activeIcon;

            if (oldIcon != null) {
                storedPreviousIcon = oldIcon;
            }

            if (config.updateTiming() == PreviousPrayerConfig.UpdateTiming.WHEN_SWITCHED) {
                boolean isSwitched = (oldIcon != null && activeIcon != null && oldIcon != activeIcon);
                boolean isTurnedOff = (oldIcon != null && activeIcon == null);

                if (isSwitched || isTurnedOff) {
                    applyDisplay(oldIcon);
                }
            } else {
                if (config.showPrayer() == PreviousPrayerConfig.DisplayedPrayer.RECENT) {
                    if (activeIcon != null) {
                        applyDisplay(activeIcon);
                    }
                } else {
                    if (storedPreviousIcon != null) {
                        applyDisplay(storedPreviousIcon);
                    }
                }
            }
        }
    }

    private void applyDisplay(HeadIcon icon) {
        if (icon == null) return;
        prayerHistory.clear();
        prayerHistory.addFirst(icon);
    }

    private HeadIcon getActiveOverhead() {
        if (client.isPrayerActive(Prayer.PROTECT_FROM_MELEE)) return HeadIcon.MELEE;
        if (client.isPrayerActive(Prayer.PROTECT_FROM_MISSILES)) return HeadIcon.RANGED;
        if (client.isPrayerActive(Prayer.PROTECT_FROM_MAGIC)) return HeadIcon.MAGIC;
        if (client.isPrayerActive(Prayer.SMITE)) return HeadIcon.SMITE;
        if (client.isPrayerActive(Prayer.REDEMPTION)) return HeadIcon.REDEMPTION;
        if (client.isPrayerActive(Prayer.RETRIBUTION)) return HeadIcon.RETRIBUTION;
        return null;
    }
}