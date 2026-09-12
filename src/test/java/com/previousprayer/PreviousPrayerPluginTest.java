package com.previousprayer;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PreviousPrayerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PreviousPrayerPlugin.class);
		RuneLite.main(args);
	}
}