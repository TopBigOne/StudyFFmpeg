
cmake_minimum_required(VERSION 3.22.1)


project("learn-ffmpeg")

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")
set(jnilibs ${CMAKE_CURRENT_SOURCE_DIR}/../jniLibs)

# 第三方代码
include_directories(
        include
        glm
        common
        ${CMAKE_CURRENT_SOURCE_DIR}/recorder
        ${CMAKE_CURRENT_SOURCE_DIR}/util
        ${CMAKE_CURRENT_SOURCE_DIR}/player
        ${CMAKE_CURRENT_SOURCE_DIR}/player/decoder
        ${CMAKE_CURRENT_SOURCE_DIR}/player/render
        ${CMAKE_CURRENT_SOURCE_DIR}/player/render/audio
        ${CMAKE_CURRENT_SOURCE_DIR}/player/render/video)


# 内部代码
file(GLOB src-files
        ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/util/*.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/common/*.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/player/*.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/player/decoder/*.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/player/render/video/*.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/player/render/audio/*.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/recorder/SingleVideoRecorder.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/recorder/SingleAudioRecorder.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/recorder/MediaRecorderContext.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/recorder/GLCameraRender.cpp
        ${CMAKE_CURRENT_SOURCE_DIR}/recorder/MediaRecorder.cpp
        )

# 链接第三方so
#link_libraries(${jnilibs}/${CMAKE_ANDROID_ARCH_ABI})


add_library(avcodec STATIC IMPORTED)
set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION ${jnilibs}/${CMAKE_ANDROID_ARCH_ABI}/libavcodec.a)


add_library(avfilter STATIC IMPORTED)
set_target_properties(avfilter PROPERTIES IMPORTED_LOCATION ${jnilibs}/${CMAKE_ANDROID_ARCH_ABI}/libavfilter.a)


add_library(avformat STATIC IMPORTED)
set_target_properties(avformat PROPERTIES IMPORTED_LOCATION ${jnilibs}/${CMAKE_ANDROID_ARCH_ABI}/libavformat.a)


add_library(avutil STATIC IMPORTED)
set_target_properties(avutil PROPERTIES IMPORTED_LOCATION ${jnilibs}/${CMAKE_ANDROID_ARCH_ABI}/libavutil.a)

add_library(fdk-aac STATIC IMPORTED)
set_target_properties(fdk-aac PROPERTIES IMPORTED_LOCATION ${jnilibs}/${CMAKE_ANDROID_ARCH_ABI}/libfdk-aac.a)


add_library(swresample STATIC IMPORTED)
set_target_properties(swresample PROPERTIES IMPORTED_LOCATION ${jnilibs}/${CMAKE_ANDROID_ARCH_ABI}/libswresample.a)

add_library(swscale STATIC IMPORTED)
set_target_properties(swscale PROPERTIES IMPORTED_LOCATION ${jnilibs}/${CMAKE_ANDROID_ARCH_ABI}/libswscale.a)


add_library(x264 STATIC IMPORTED)
set_target_properties(x264 PROPERTIES IMPORTED_LOCATION ${jnilibs}/${CMAKE_ANDROID_ARCH_ABI}/libx264.a)




add_library(
        ${CMAKE_PROJECT_NAME}
        SHARED
        ${src-files})


# 音视频相关第三方库
set(third-party-libs
        avformat
        avcodec
        avfilter
        swresample
        swscale
        avutil
        fdk-aac
        x264)


set(native-libs
        android
        mediandk
        EGL
        GLESv3
        OpenSLES
        log
        m
        z
        )

message(STATUS "my native lib is ${CMAKE_PROJECT_NAME}")

target_link_libraries(
        ${CMAKE_PROJECT_NAME}
        ${third-party-libs}
        ${native-libs}
        ${log-lib}

)