package dev.gaishi.flowerify.client

import com.mojang.brigadier.Command
import dev.gaishi.flowerify.client.screen.MenuScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.server.command.CommandManager
import net.minecraft.text.Text

class FlowerifyClient : ClientModInitializer {

    override fun onInitializeClient() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(CommandManager.literal("flowerify").executes { context ->
                if (!context.source.isExecutedByPlayer || context.source.server.isRemote) Command.SINGLE_SUCCESS
                MinecraftClient.getInstance().execute {
                    MinecraftClient.getInstance().setScreen(MenuScreen(Text.empty()))
                }
                Command.SINGLE_SUCCESS
            })
        }
    }
}
