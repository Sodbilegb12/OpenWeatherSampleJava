cmake_minimum_required(VERSION 3.4.1)
add_library(signature SHARED signature.cpp)
find_library(log-lib log)
target_link_libraries(signature ${log-lib})