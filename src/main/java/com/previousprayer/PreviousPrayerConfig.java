package com.previousprayer;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("previousprayer")
public interface PreviousPrayerConfig extends Config {

    enum IconSize {
        SMALL(32),
        MEDIUM(48),
        LARGE(64);

        private final int pixelSize;

        IconSize(int pixelSize) {
            this.pixelSize = pixelSize;
        }

        public int getPixelSize() {
            return pixelSize;
        }
    }

    enum DisplayedPrayer {
        RECENT("Recent"),
        PREVIOUS("Previous");

        private final String name;

        DisplayedPrayer(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    enum UpdateTiming {
        INSTANT("Instant"),
        WHEN_SWITCHED("When switched");

        private final String name;

        UpdateTiming(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @ConfigItem(
        keyName = "iconSize",
        name = "Icon Size",
        description = "Kies het formaat van het icoon",
        position = 1
    )
    default IconSize iconSize() {
        return IconSize.MEDIUM;
    }

    @ConfigItem(
        keyName = "showPrayer",
        name = "Show Prayer",
        description = "Kies of je de meest recente of de vorige prayer wilt tonen",
        position = 2
    )
    default DisplayedPrayer showPrayer() {
        return DisplayedPrayer.PREVIOUS;
    }

    @ConfigItem(
        keyName = "updateTiming",
        name = "Update",
        description = "Instant update direct bij elke klik. When switched wacht tot je prayer switched of uitgaat.",
        position = 3
    )
    default UpdateTiming updateTiming() {
        return UpdateTiming.INSTANT;
    }
}