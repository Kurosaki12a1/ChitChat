package com.kuro.chitchat.common.conveter

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.BsonDateTime
import org.bson.BsonString
import org.bson.BsonType
import org.bson.codecs.kotlinx.BsonDecoder
import org.bson.codecs.kotlinx.BsonEncoder

object KotlinLocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter = LocalDateTime.Formats.ISO

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("KotlinLocalDateTimeSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return when (decoder) {
            is BsonDecoder -> {
                val bsonValue = decoder.decodeBsonValue()
                when (bsonValue.bsonType) {
                    BsonType.DATE_TIME -> {
                        val dateTime = (bsonValue as BsonDateTime).value
                        Instant.fromEpochMilliseconds(dateTime).toLocalDateTime(TimeZone.UTC)
                    }
                    BsonType.STRING -> {
                        val value = (bsonValue as BsonString).value
                        LocalDateTime.parse(value, formatter)
                    }
                    else -> {
                        throw SerializationException("Expected DATE_TIME but found ${bsonValue.bsonType}")
                    }
                }
            }

            else -> {
                LocalDateTime.parse(decoder.decodeString(), formatter)
            }
        }
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        when (encoder) {
            is BsonEncoder -> {
                val instant = value.toInstant(TimeZone.currentSystemDefault())
                val bsonDateTime = BsonDateTime(instant.toEpochMilliseconds())
                encoder.encodeBsonValue(bsonDateTime)
            }

            else -> {
                val instant = value.toInstant(TimeZone.currentSystemDefault())
                encoder.encodeString(instant.toEpochMilliseconds().toString())
            }
        }
    }
}
