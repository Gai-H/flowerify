package dev.gaishi.flowerify.client.widget

import dev.gaishi.flowerify.client.Video
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.Selectable
import net.minecraft.client.gui.widget.ClickableWidget
import net.minecraft.client.gui.widget.ElementListWidget

@Environment(EnvType.CLIENT)
class VideoListEntry(private val video: Video): ElementListWidget.Entry<VideoListEntry>() {
    private val children = ArrayList<ClickableWidget>()

    override fun render(
        context: DrawContext,
        index: Int,
        y: Int,
        x: Int,
        entryWidth: Int,
        entryHeight: Int,
        mouseX: Int,
        mouseY: Int,
        hovered: Boolean,
        tickDelta: Float
    ) {
        context.drawText(MinecraftClient.getInstance().textRenderer, video.name, x, y, 0xFFFFFFFF.toInt(), true)
    }

    override fun children(): MutableList<out Element> {
        return this.children
    }

    override fun selectableChildren(): MutableList<out Selectable> {
        return this.children
    }

}
