package dev.gaishi.flowerify.client.screen

import dev.gaishi.flowerify.client.Video
import dev.gaishi.flowerify.client.widget.VideoListEntry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.DirectionalLayoutWidget
import net.minecraft.client.gui.widget.ElementListWidget
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget
import net.minecraft.text.Text
import net.minecraft.util.Util
import kotlin.io.path.createDirectories
import kotlin.io.path.notExists

@Environment(EnvType.CLIENT)
class VideoListScreen(title: Text?) : Screen(title) {
    val layout = ThreePartsLayoutWidget(this)

    override fun init() {
        println("Init")
        val videoDirectory = MinecraftClient
            .getInstance()
            .runDirectory
            .toPath()
            .resolve("config")
            .resolve("flowerify")
            .resolve("videos")
        if (videoDirectory.notExists()) {
            videoDirectory.createDirectories()
        }
        val videos = videoDirectory
            .toFile()
            .listFiles { file -> file.isFile }
            ?.filter { file -> file.name.endsWith(".mp4") }
            ?.map { file -> Video(file) }
            ?.toSet() ?: emptySet()
        println(videos)

        layout.addHeader(Text.of("Videos"), this.textRenderer)

        layout.addBody(VideoListWidget(videos))

        val footer = layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8))
        footer.add(ButtonWidget.builder(Text.of("Open Folder")) { _ ->
            Util.getOperatingSystem().open(videoDirectory)
        }.build())
        footer.add(ButtonWidget.builder(Text.of("Close")) { _ -> this.close() }.build())

        layout.forEachChild { element ->
            addDrawableChild(element)
        }
        this.initTabNavigation()
    }

    override fun initTabNavigation() {
        layout.refreshPositions()
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta)

        // Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
        // We'll subtract the font height from the Y position to make the text appear above the button.
        // Subtracting an extra 10 pixels will give the text some padding.
        // textRenderer, text, x, y, color, hasShadow
    }

    @Environment(EnvType.CLIENT)
    inner class VideoListWidget(videos: Set<Video>): ElementListWidget<VideoListEntry>(
        MinecraftClient.getInstance(),
        this@VideoListScreen.width,
        this@VideoListScreen.layout.contentHeight,
        this@VideoListScreen.layout.headerHeight,
        48,
    ) {
        init {
            for (video in videos) {
                this.addEntry(VideoListEntry(video))
            }
        }

        override fun getRowWidth(): Int {
            return 400
        }
    }
}
