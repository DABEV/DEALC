package mx.edu.utez.dealc.provider

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import mx.edu.utez.dealc.R
import mx.edu.utez.dealc.view.MenuActivity

class NotificationProvider : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()) {
            println("FB: Message ${message.data.toString()}")
        }
        message.notification?.let {
            println("FB: Notificación ${message.notification.toString()}")
            crearNotificacion("Mensaje", "")
        }
    }

    fun enviarToken(token: String) {}

    fun crearNotificacion(mensaje: String, requestId: String) {
        println("FB: Noti")
        val intent = Intent(this, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val canal = getString(R.string.app_name)
        val sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, canal)
            .setSmallIcon(R.drawable.ic_logo_deal)
            .setContentTitle("UTEZ")
            .setContentText(mensaje)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(requestId)
            )
            .setAutoCancel(true)
            .setSound(sonido)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var canal1 = NotificationChannel(
                canal, getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(canal1)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}