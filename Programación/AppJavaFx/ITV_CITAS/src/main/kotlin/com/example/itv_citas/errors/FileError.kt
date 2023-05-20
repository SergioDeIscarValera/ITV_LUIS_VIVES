package com.example.itv_citas.errors

sealed class FileError(val message: String) {
    class FileNotFound(type: String): FileError("Error file $type not found")
    class FileNotReadable(type: String): FileError("Error file $type not readable")
    class FileNotWritable(type: String): FileError("Error file $type not writable")
}