# ─────────────────────────────────────────────────────
# ProGuard / R8 优化规则 — 网球抢票 (TennisRush) KMP
# ─────────────────────────────────────────────────────

# Ktor Client
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.tennisrush.model.**$$serializer { *; }
-keepclassmembers class com.tennisrush.model.** { *** Companion; }
-keepclasseswithmembers class com.tennisrush.model.** { kotlinx.serialization.KSerializer serializer(...); }

# Coil
-keep class coil3.** { *; }
-dontwarn coil3.**

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# TennisRush — keep model fields
-keepclassmembers class com.tennisrush.model.** {
    <fields>;
}

# ── 性能优化 ─────────────────────────────────────────
# 移除日志 (Release)
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
