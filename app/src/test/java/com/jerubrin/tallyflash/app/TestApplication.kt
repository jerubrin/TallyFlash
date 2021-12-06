package com.jerubrin.tallyflash.app

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(Application::class)
interface TestApplication