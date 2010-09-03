#include <string.h>
#include <jni.h>

jstring JNICALL Java_com_easymorse_NdkHelloActivity_sayHello(JNIEnv* env,
		jobject thiz) {
	return (*env)->NewStringUTF(env, "你好，世界。来自JNI！");
}
