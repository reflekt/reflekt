package se.jensim.reflekt.internal

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

internal class JarFileClassLocatorTest {

    @Rule
    @JvmField
    val tmpDir: TemporaryFolder = TemporaryFolder()

    @Before
    fun setUp() {
        val nestedJarPath = "${tmpDir.root}/nested.jar"
        val findMeClass = "FindMe.class"
        writeToZip(findMeClass, nestedJarPath, "i am a class".toByteArray())
        val nestedJarData = File(tmpDir.root, "nested.jar").readBytes()
        writeToZip("nested.jar", "${tmpDir.root}/outer.jar", nestedJarData)
    }

    @Test
    fun getClassFileFromNestedJar() {
        val classes = JarFileClassLocator.getClasses(ZipFile(File("${tmpDir.root}/outer.jar")), true)

        assertEquals(classes.toSet(), setOf("com.example.FindMe"))
    }

    private fun writeToZip(entryName: String, zipFile: String, data: ByteArray) {
        ZipOutputStream(FileOutputStream(File(zipFile))).use {
            it.putNextEntry(ZipEntry("com/example/$entryName"))
            it.write(data)
        }
    }
}
