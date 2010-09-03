LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := helloworld-jni
LOCAL_SRC_FILES := helloworld-jni.c

include $(BUILD_SHARED_LIBRARY)