package dev.gaishi.flowerify.client

import java.io.File

class Video(file: File) {
    val name = file.nameWithoutExtension
}
