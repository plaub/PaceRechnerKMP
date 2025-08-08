package de.pierrelaub.pace_rechner

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform