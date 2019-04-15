package com.example.vkinfo.utils

import android.net.Uri
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import java.util.*

public class NetworkUtils{
    companion object {
        private val VK_API_BASE_URL: String = "https://api.vk.com"
        private val VK_USERS_GET: String = "/method/users.get"
        private val PARAM_USER_ID: String = "user_id"
        private val PARAM_VERSION: String = "v"
        private var ACCESS_TOKEN: String? = "access_token"

        public fun generateURL(userId: String): URL {
            var builtUri: Uri = Uri.parse(VK_API_BASE_URL + VK_USERS_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_ID, userId)
                .appendQueryParameter(PARAM_VERSION, "5.8")
                .appendQueryParameter(ACCESS_TOKEN, "91771490917714909177149027911ee5289917791771490cdc6f6832d10981da340b90d")
                .build()
            var url: URL? = null
            //try {
            url = URL(builtUri.toString())
            /*}catch (MalformedURLException e){
            e.printStackTrace()
        }*/

            return url
        }


        public fun getResponseFromURL(url: URL): String? {
            var urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                var inn: InputStream = urlConnection.getInputStream()
                var scanner: Scanner = Scanner(inn)
                scanner.useDelimiter("\\A")
                var hasInput: Boolean = scanner.hasNext()
                if (hasInput) {
                    return scanner.next()
                } else {
                    return null
                }
            } catch (e: UnknownHostException) {
                return null
            }finally {
                    urlConnection.disconnect()
            }
        }
    }
}
