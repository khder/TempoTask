package com.tempo.task.data

class Resource {
    val STATUS_OK = "ok"
    val STATUS_ERROR = "error"
    lateinit var status:String
    lateinit var articles:List<News>
    var message:String = ""
    companion object{
        fun error(message:String):Resource{
           val resource= Resource()
            resource.message = message
            return resource
        }
    }
}