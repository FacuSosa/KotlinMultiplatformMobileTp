package com.example.tppokedex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun initLogger()