package com.example.applicationnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

lateinit var notificationChannel: NotificationChannel
lateinit var notificationManager: NotificationManager
lateinit var builder: NotificationCompat.Builder

//Método para disparar a notification
fun Context.showNotification(channelId: String, title: String, body: String){
    //Responsável por gerenciar todas as notificações responsaveis pelo aplicativo
    notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // "esse método vai trabalhar com notificações
    //Responsável pelo evento de click da notificação
    val intent = Intent(this, MainActivity::class.java)
    //Vai pegar a Intent criada e passar para a notificação | o getActivities vai identificar a activity que vai para a notificação
    val pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT) // A flag simboliza que toda vez que soltar essa notificação, vai poder entrar na activity de onde estiver
    //Verificação para usar um emulador de uma api(android Q) que tem uma versão superior ao android O
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        //Responsável por manipular o objeto da notificação
        notificationChannel = NotificationChannel(channelId,body, NotificationManager.IMPORTANCE_HIGH).apply {
            lightColor = Color.BLUE
            enableVibration(true)
        }
    // IMPORTANCE_HIGH faz a notificação aparecer na tela.

        notificationManager.createNotificationChannel(notificationChannel) //Avisa ao notificationManager que foi criado uma nova notificação que é a variavel notificationChannel
        builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_refresh)
            setContentTitle(title)
            setContentText(body)
            setAutoCancel(true)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
            setContentIntent(pendingIntent) // Setando o Click, toda vez que ela for clicada vai ser aberto o MainActivity
        }
    }
    // Avisa ao notificationManager que a notificação foi criada e que pode ser disparada
    notificationManager.notify(channelId.toInt(), builder.build()) // passando o Id e o builder que é a notificação criada
}