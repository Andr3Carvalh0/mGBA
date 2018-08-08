package io.mgba.model.io

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Decoder {
    private val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    private fun getFileMD5(file: File?): ByteArray? {
        if (file == null) return null
        var dis: DigestInputStream? = null
        try {
            val fis = FileInputStream(file)
            var md = MessageDigest.getInstance("MD5")
            dis = DigestInputStream(fis, md)
            val buffer = ByteArray(1024 * 256)
            while (true) {
                if (dis.read(buffer) <= 0) break
            }
            md = dis.messageDigest
            return md.digest()
        } catch (e: NoSuchAlgorithmException) {

            if (dis != null) {
                try {
                    dis.close()
                } catch (e1: IOException) {
                    e.printStackTrace()
                }

            }
        } catch (e: IOException) {
            if (dis != null) {
                try {
                    dis.close()
                } catch (e1: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return null
    }


    fun getFileMD5ToString(file: File): String? {
        val bytes = Decoder.getFileMD5(file)
        return calculateMD5(bytes)
    }


    fun calculateMD5(bytes: ByteArray?): String? {
        if (bytes == null) return null
        val len = bytes.size
        if (len <= 0) return null
        val ret = CharArray(len shl 1)
        var i = 0
        var j = 0
        while (i < len) {
            ret[j++] = hexDigits[bytes[i].toInt().ushr(4) and 0x0f]
            ret[j++] = hexDigits[bytes[i].toInt() and 0x0f]
            i++
        }

        return String(ret)
    }

}
