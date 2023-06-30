package com.kerencev.vknewscompose.presentation.common.mvi

interface VkMarker

//Event coming from the UI
interface VkEvent : VkMarker

//A ONE-TIME event incoming in the UI
interface VkShot : VkMarker

//Incoming UI change state
interface VkState : VkMarker