package com.example.itv_citas.errors

sealed class FileError(val message: String) {
    class FileNotFound(type: String): FileError("File $type not found")
    class FileNotReadable(type: String): FileError("File $type not readable")
    class FileNotWritable(type: String): FileError("File $type not writable")
}