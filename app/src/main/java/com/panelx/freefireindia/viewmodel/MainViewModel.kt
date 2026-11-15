package com.panelx.freefireindia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.panelx.freefireindia.data.local.AppDatabase
import com.panelx.freefireindia.data.model.DeviceInfo
import com.panelx.freefireindia.data.model.PanelFeature
import com.panelx.freefireindia.data.model.PanelFeatures
import com.panelx.freefireindia.data.model.PlayStyle
import com.panelx.freefireindia.data.model.SensitivityProfile
import com.panelx.freefireindia.data.model.SensitivityRecommendation
import com.panelx.freefireindia.data.repository.PreferencesRepository
import com.panelx.freefireindia.data.repository.ProfileRepository
import com.panelx.freefireindia.utils.DeviceDetector
import com.panelx.freefireindia.utils.SensitivityEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val profileRepo = ProfileRepository(db.profileDao())
    private val prefsRepo = PreferencesRepository(application)

    private val _deviceInfo = MutableStateFlow(DeviceInfo())
    val deviceInfo: StateFlow<DeviceInfo> = _deviceInfo.asStateFlow()

    private val _playStyle = MutableStateFlow(PlayStyle.BALANCED)
    val playStyle: StateFlow<PlayStyle> = _playStyle.asStateFlow()

    private val _recommendation = MutableStateFlow<SensitivityRecommendation?>(null)
    val recommendation: StateFlow<SensitivityRecommendation?> = _recommendation.asStateFlow()

    private val _customGeneral = MutableStateFlow(100)
    val customGeneral: StateFlow<Int> = _customGeneral.asStateFlow()
    private val _customRedDot = MutableStateFlow(100)
    val customRedDot: StateFlow<Int> = _customRedDot.asStateFlow()
    private val _custom2x = MutableStateFlow(100)
    val custom2x: StateFlow<Int> = _custom2x.asStateFlow()
    private val _custom4x = MutableStateFlow(100)
    val custom4x: StateFlow<Int> = _custom4x.asStateFlow()
    private val _customSniper = MutableStateFlow(100)
    val customSniper: StateFlow<Int> = _customSniper.asStateFlow()
    private val _customFreeLook = MutableStateFlow(100)
    val customFreeLook: StateFlow<Int> = _customFreeLook.asStateFlow()

    private val _panelFeatures = MutableStateFlow(PanelFeatures.defaultFeatures)
    val panelFeatures: StateFlow<List<PanelFeature>> = _panelFeatures.asStateFlow()

    private val _demoMessage = MutableStateFlow<String?>(null)
    val demoMessage: StateFlow<String?> = _demoMessage.asStateFlow()

    val profiles: StateFlow<List<SensitivityProfile>> = profileRepo.getAllProfiles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val darkMode = prefsRepo.darkMode.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val language = prefsRepo.language.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "en")

    private val _dpiPreference = MutableStateFlow("Balanced")
    val dpiPreference: StateFlow<String> = _dpiPreference.asStateFlow()

    init {
        loadDeviceInfo()
        generateRecommendation()
    }

    private fun loadDeviceInfo() {
        _deviceInfo.value = DeviceDetector.detect(getApplication())
    }

    fun setPlayStyle(style: PlayStyle) {
        _playStyle.value = style
        viewModelScope.launch { prefsRepo.setLastPlayStyle(style.label) }
        if (style != PlayStyle.CUSTOM) {
            generateRecommendation()
        }
    }

    fun generateRecommendation() {
        val rec = SensitivityEngine.recommend(_deviceInfo.value, _playStyle.value)
        _recommendation.value = rec
        _customGeneral.value = rec.general
        _customRedDot.value = rec.redDot
        _custom2x.value = rec.scope2x
        _custom4x.value = rec.scope4x
        _customSniper.value = rec.sniper
        _customFreeLook.value = rec.freeLook
    }

    fun resetSensitivity() {
        _playStyle.value = PlayStyle.BALANCED
        generateRecommendation()
    }

    fun updateCustomValue(type: String, value: Int) {
        when (type) {
            "general" -> _customGeneral.value = value.coerceIn(10, 200)
            "redDot" -> _customRedDot.value = value.coerceIn(10, 200)
            "2x" -> _custom2x.value = value.coerceIn(10, 200)
            "4x" -> _custom4x.value = value.coerceIn(10, 200)
            "sniper" -> _customSniper.value = value.coerceIn(10, 200)
            "freeLook" -> _customFreeLook.value = value.coerceIn(10, 200)
        }
        _playStyle.value = PlayStyle.CUSTOM
    }

    fun getShareText(): String {
        return SensitivityEngine.formatForShare(
            deviceName = _deviceInfo.value.displayName,
            general = _customGeneral.value,
            redDot = _customRedDot.value,
            scope2x = _custom2x.value,
            scope4x = _custom4x.value,
            sniper = _customSniper.value,
            freeLook = _customFreeLook.value
        )
    }

    fun toggleFeature(id: String) {
        _panelFeatures.value = _panelFeatures.value.map { feature ->
            if (feature.id == id) feature.copy(isToggled = !feature.isToggled) else feature
        }
        val feature = _panelFeatures.value.find { it.id == id }
        _demoMessage.value = feature?.description ?: "VISUAL DEMO ONLY — No game interaction."
    }

    fun clearDemoMessage() {
        _demoMessage.value = null
    }

    fun saveProfile(name: String) {
        viewModelScope.launch {
            val profile = SensitivityProfile(
                name = name.ifBlank { "Profile ${System.currentTimeMillis() % 10000}" },
                playStyle = _playStyle.value.label,
                general = _customGeneral.value,
                redDot = _customRedDot.value,
                scope2x = _custom2x.value,
                scope4x = _custom4x.value,
                sniper = _customSniper.value,
                freeLook = _customFreeLook.value
            )
            profileRepo.saveProfile(profile)
        }
    }

    fun loadProfile(profile: SensitivityProfile) {
        _playStyle.value = PlayStyle.entries.find { it.label == profile.playStyle } ?: PlayStyle.CUSTOM
        _customGeneral.value = profile.general
        _customRedDot.value = profile.redDot
        _custom2x.value = profile.scope2x
        _custom4x.value = profile.scope4x
        _customSniper.value = profile.sniper
        _customFreeLook.value = profile.freeLook
        _recommendation.value = SensitivityRecommendation(
            general = profile.general,
            redDot = profile.redDot,
            scope2x = profile.scope2x,
            scope4x = profile.scope4x,
            sniper = profile.sniper,
            freeLook = profile.freeLook
        )
    }

    fun deleteProfile(profile: SensitivityProfile) {
        viewModelScope.launch { profileRepo.deleteProfile(profile) }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch { prefsRepo.setDarkMode(enabled) }
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch { prefsRepo.setLanguage(lang) }
    }

    fun resetAllSettings() {
        viewModelScope.launch {
            prefsRepo.resetAll()
            profileRepo.deleteAll()
            resetSensitivity()
        }
    }

    fun setDpiPreference(pref: String) {
        _dpiPreference.value = pref
    }

    fun getDpiSuggestion(): String {
        return when (_dpiPreference.value) {
            "Low" -> "Suggested DPI range: 320 – 400\nFire button size: Medium-Large for comfort."
            "High" -> "Suggested DPI range: 480 – 560+\nFire button size: Small-Medium for precision."
            else -> "Suggested DPI range: 400 – 480\nFire button size: Medium for balanced control."
        }
    }
}
