package com.chicman.service

import com.chicman.ChicManUI
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import kotlin.text.Charsets.UTF_8

class CloudStorageService {

    companion object {

        fun testGoogleCloudStorage() {
            try {
                val log = LoggerFactory.getLogger(ChicManUI::class.java)
                val serviceAccount =
                    FileInputStream("/Users/mutnemom/.chicman-firebase-admin/chicman-cloud-storage-key.json")

                val storage = StorageOptions
                    .newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()
                    .service

                val buckets = storage.list()
                for (bucket in buckets.iterateAll()) {
                    log.error("-> found bucket: " + bucket.name)
                }

                val bucket = storage[
                    "chicman-image-server.appspot.com",
                    Storage.BucketGetOption.fields(*Storage.BucketField.values())
                ]

                val blob = storage[bucket.name, "airport1.jpg"]
                log.error(" ")
                log.error("-> blob bucket: " + blob.bucket)
                log.error("-> blob name: " + blob.name)
                log.error("-> blob size: " + blob.size)
                log.error("-> blob content type: " + blob.contentType)
                log.error(" ")

                val blobId = BlobId.of(bucket.name, "test-upload.txt")
                val blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build()
                val uploaded = storage.create(blobInfo, "Hello, Chanathip Yordkam".toByteArray(UTF_8))
                log.error("-> upload " + uploaded.name)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
