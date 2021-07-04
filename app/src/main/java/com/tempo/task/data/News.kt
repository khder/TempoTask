package com.tempo.task.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class News() : Parcelable {
    private lateinit var source:Map<String,String>
    private lateinit var title:String
    private lateinit var description:String
    @SerializedName("urlToImage")
    private lateinit var image:String
    private lateinit var author:String
    private lateinit var publishedAt:String
    private lateinit var url:String
    private lateinit var content:String

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()!!
        description = parcel.readString()!!
        image = parcel.readString()!!
        author = parcel.readString()!!
        publishedAt = parcel.readString()!!
        url = parcel.readString()!!
        content = parcel.readString()!!
    }

    fun setSource(source:Map<String,String>){
        this.source = source
    }
    fun getSource():Map<String,String>{
        return source
    }
    fun setTitle(title:String){
        this.title = title
    }
    fun getTitle():String{
        return title
    }
    fun setDescription(description:String){
        this.description = description
    }
    fun getDescription():String{
        return description
    }
    fun setImage(image:String){
        this.image = image
    }
    fun getImage():String{
        return image
    }
    fun setAuthor(author:String){
        this.author = author
    }
    fun getAuthor():String{
        return author
    }
    fun setPublishedAt(publishedAt:String){
        this.publishedAt = publishedAt
    }
    fun getPublishedAt():String{
        return publishedAt
    }
    fun setUrl(url:String){
        this.url = url
    }
    fun getUrl():String{
        return url
    }
    fun setContent(content:String){
        this.content = content
    }
    fun getContent():String{
        return content
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(author)
        parcel.writeString(publishedAt)
        parcel.writeString(url)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<News> {
        override fun createFromParcel(parcel: Parcel): News {
            return News(parcel)
        }

        override fun newArray(size: Int): Array<News?> {
            return arrayOfNulls(size)
        }
    }
}