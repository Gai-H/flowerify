package dev.gaishi.flowerify.client

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text
import net.minecraft.util.Util
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

@Environment(EnvType.CLIENT)
class MenuScreen(title: Text?) : Screen(title) {

    override fun init() {
        val buttonWidget = ButtonWidget.builder(Text.of("Open Video Folder")) { btn ->
            val runDirectoryPath = MinecraftClient.getInstance().runDirectory.toPath()
            val videoDirectoryPath = runDirectoryPath.resolve("config").resolve("flowerify").resolve("videos")

            if (videoDirectoryPath.notExists()) {
                videoDirectoryPath.createDirectories()
            }

            Util.getOperatingSystem().open(videoDirectoryPath)
        }.dimensions(40, 40, 120, 20).build()
        // x, y, width, height
        // It's recommended to use the fixed height of 20 to prevent rendering issues with the button textures.

        // Register the button widget.
        this.addDrawableChild(buttonWidget)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        // Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
        // We'll subtract the font height from the Y position to make the text appear above the button.
        // Subtracting an extra 10 pixels will give the text some padding.
        // textRenderer, text, x, y, color, hasShadow
        context.drawText(
            this.textRenderer,
            "Flowerify Menu",
            40,
            40 - this.textRenderer.fontHeight - 10,
            0xFFFFFFFF.toInt(),
            true
        )
    }
}
