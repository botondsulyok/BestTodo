package com.example.besttodo.ui.todos

import co.zsmb.rainbowcake.base.OneShotEvent

class Failed(val message: String) : OneShotEvent

class ActionSuccess(val message: String) : OneShotEvent