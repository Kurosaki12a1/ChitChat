package utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object KotlinLocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter = LocalDateTime.Formats.ISO

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("KotlinLocalDateTimeSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val instant = value.toInstant(TimeZone.currentSystemDefault())
        encoder.encodeString(instant.toEpochMilliseconds().toString())
    }
}
