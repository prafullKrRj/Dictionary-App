package com.prafullkumar.dictionary.domain.models

import com.google.gson.annotations.SerializedName

class WordInfo : ArrayList<WordInfoItem>()


data class WordInfoItem(
    @SerializedName("license")
    val license: License,

    @SerializedName("meanings")
    val meanings: List<Meaning>,

    @SerializedName("phonetic")
    val phonetic: String,

    @SerializedName("phonetics")
    val phonetics: List<Phonetic>,

    @SerializedName("sourceUrls")
    val sourceUrls: List<String>,

    @SerializedName("word")
    val word: String
)data class Definition(
    @SerializedName("antonyms")
    val antonyms: List<Any>,

    @SerializedName("definition")
    val definition: String,

    @SerializedName("example")
    val example: String,

    @SerializedName("synonyms")
    val synonyms: List<String>
)data class License(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)data class Meaning(
    @SerializedName("antonyms")
    val antonyms: List<Any>,

    @SerializedName("definitions")
    val definitions: List<Definition>,

    @SerializedName("partOfSpeech")
    val partOfSpeech: String,

    @SerializedName("synonyms")
    val synonyms: List<String>
)data class Phonetic(
    @SerializedName("audio")
    val audio: String,

    @SerializedName("license")
    val license: License,

    @SerializedName("sourceUrl")
    val sourceUrl: String,

    @SerializedName("text")
    val text: String
)