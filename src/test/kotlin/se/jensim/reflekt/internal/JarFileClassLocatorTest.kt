package se.jensim.reflekt.internal

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class JarFileClassLocatorTest {

    @Rule
    @JvmField
    val tmpDir: TemporaryFolder = TemporaryFolder()

    @Test
    fun getClassFileFromNestedJar() {
        val nestedJarPath = "${tmpDir.root}/nested.jar"
        val findMeClass = "FindMe.class"
        writeToZip(findMeClass, nestedJarPath, "i am a class".toByteArray())
        val nestedJarData = File(tmpDir.root, "nested.jar").readBytes()
        writeToZip("nested.jar", "${tmpDir.root}/outer.jar", nestedJarData)

        val classes = JarFileClassLocator.getClassFiles(ZipFile(File("${tmpDir.root}/outer.jar")))

        assertEquals(classes, setOf("com.example.FindMe"))
    }

    private fun writeToZip(entryName: String, zipFile: String, data: ByteArray) {
        ZipOutputStream(FileOutputStream(File(zipFile))).use {
            it.putNextEntry(ZipEntry("com/example/$entryName"))
            it.write(data)
        }
    }
}
