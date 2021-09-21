package com.mackerel.api.model.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.Timestamp
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.mackerel.api.model.Status
import com.mackerel.api.model.StatusFromFrontend
import java.text.SimpleDateFormat


class FirestoreProcess {
    private val db: Firestore

    init {
        // Environment variable GOOGLE_APPLICATION_CREDENTIALS must be set.
        // export GOOGLE_APPLICATION_CREDENTIALS="/home/user/Downloads/service-account-file.json"

        val apps = FirebaseApp.getApps()
        if (apps.size == 0) {
            val projectId = "mackerel-app"
            val credentials = GoogleCredentials.getApplicationDefault()
            val options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
            FirebaseApp.initializeApp(options)
        }

        this.db = FirestoreClient.getFirestore()
    }

    fun getActiveServers(): List<Server> {
        val serverList = mutableListOf<Server>()

        val usersFuture = db.collection("users").get()
        val users = usersFuture.get().documents

        for (user in users) {
            val serversFuture = db.collection("users").document(user.id).collection("servers").get()
            val servers = serversFuture.get().documents

            for (server in servers) {
                if (server.data["active"] as Boolean)
                    serverList.add(
                        Server(
                            id = server.id,
                            url = server.data["url"] as String
                        )
                    )
            }
        }
        return serverList
    }

    fun getLogs(serverId: String): List<StatusFromFrontend> {
        val serverFuture = db.document("logs/${serverId}").collection("statuses").orderBy("createdAt").limitToLast(25).get()
        val statuses = serverFuture.get().documents

        return statuses.map {
            StatusFromFrontend(SimpleDateFormat("HH:mm").format((it.data["createdAt"] as Timestamp).toDate()), ttfb = it.data["ttfb"] as Number)
        }
    }

    fun setStatus(serverId: String, status: Status) {
        val docRef = db.document("logs/${serverId}/statuses/${status.createdAt}")
        docRef.set(status)
    }
}