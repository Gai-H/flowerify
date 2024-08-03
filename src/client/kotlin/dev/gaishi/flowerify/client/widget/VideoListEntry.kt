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
import net.minecraft.text.OrderedText
import net.minecraft.text.StringVisitable
import net.minecraft.text.Text
import net.minecraft.util.Language

@Environment(EnvType.CLIENT)
class VideoListEntry(private val video: Video): ElementListWidget.Entry<VideoListEntry>() {
    object VideoListEntryConstants {
        const val ELLIPSIS = "..."
    }
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
        context.drawText(MinecraftClient.getInstance().textRenderer, trimTextToWidth(Text.of(video.name)), x, y, 0xFFFFFFFF.toInt(), true)
        context.drawBorder(x, y, entryWidth, entryHeight, 0xFFFFFFFF.toInt())
    }

    override fun children(): MutableList<out Element> {
        return this.children
    }

    override fun selectableChildren(): MutableList<out Selectable> {
        return this.children
    }

    companion object {
        fun trimTextToWidth(text: Text): OrderedText? {
            val client = MinecraftClient.getInstance()
            val i: Int = client.textRenderer.getWidth(text as StringVisitable)
            if (i > 250) {
                val stringVisitable = StringVisitable.concat(
                    *arrayOf<StringVisitable>(
                        client.textRenderer.trimToWidth(
                            text as StringVisitable,
                            250 - client.textRenderer.getWidth(VideoListEntryConstants.ELLIPSIS)
                        ), StringVisitable.plain(VideoListEntryConstants.ELLIPSIS)
                    )
                )
                return Language.getInstance().reorder(stringVisitable)
            }
            return text.asOrderedText()
        }
    }
}
