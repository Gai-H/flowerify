package dev.gaishi.flowerify.client

import com.mojang.brigadier.Command
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.server.command.CommandManager
import net.minecraft.text.Text

class FlowerifyClient : ClientModInitializer {

    override fun onInitializeClient() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(CommandManager.literal("flowerify").executes { context ->
                val player = context.source.player
                if (!context.source.isExecutedByPlayer || context.source.player == null || context.source.server.isRemote) Command.SINGLE_SUCCESS
                MinecraftClient.getInstance().execute {
                    MinecraftClient.getInstance().setScreen(GenericContainerScreen(GenericContainerScreenHandler.createGeneric9x1(42, player?.inventory), player?.inventory, Text.of("foo")))
                }
                Command.SINGLE_SUCCESS
            })
        }
    }
}
