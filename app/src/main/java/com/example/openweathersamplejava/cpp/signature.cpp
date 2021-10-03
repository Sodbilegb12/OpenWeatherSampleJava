#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_openweathersamplejava_ui_detail_WeatherActivity_getAPIKey(JNIEnv *env,
                                                                           jclass clazz) {
    std::string apiKey = "80f64cfaeb3dc2c2642a308dab1123d4";
    return env->NewStringUTF(apiKey.c_str());
}