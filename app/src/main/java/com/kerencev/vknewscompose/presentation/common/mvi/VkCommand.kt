package com.kerencev.vknewscompose.presentation.common.mvi

interface VkCommand

//Intermediate entity between Event and ViewState
//Event -> Action -> ViewState
interface VkAction : VkCommand

//Intermediate entity between Event и Shot
//Event -> Effect -> Shot
interface VkEffect : VkCommand