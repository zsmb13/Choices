package co.zsmb.choices

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform