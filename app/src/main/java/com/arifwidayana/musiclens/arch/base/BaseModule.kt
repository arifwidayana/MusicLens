package com.arifwidayana.musiclens.arch.base

import org.koin.core.module.Module

/**
 * BaseModule created for every feature injection don't have duplicated module
 * in application level and simplify boilerplate code
 */
interface BaseModule {
    fun getModules(): List<Module>
}

interface FeatureModule : BaseModule {
    val dataSources: Module
    val repositories: Module
    val useCases: Module
    val networks: Module
    val viewModels: Module
}