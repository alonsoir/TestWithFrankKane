package avro

import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.avro.io.{BinaryEncoder, DatumReader, Decoder, DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}
import org.apache.avro.{Schema, SchemaBuilder}
import org.c02e.jpgpj._

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File, FileInputStream, FileOutputStream}

object EncryptPayload {

  case class testRecord (data:String)
  //
  val passphrase = "YOUR-PASSPHRASE"

  def main(args: Array[String]): Unit = {
    println("This is a test that try to encrypt an avro message")

    val schema : Schema = SchemaBuilder
      .record("testRecord").fields()
      .requiredString("data")
      .endRecord()

    val genericRecord: GenericRecord = new GenericData.Record(schema)
    genericRecord.put("data", "test data")

    val writer = new SpecificDatumWriter[GenericRecord](schema)
    val out = new ByteArrayOutputStream()
    val encoder: BinaryEncoder = EncoderFactory.get().binaryEncoder(out, null)
    writer.write(genericRecord, encoder)
    encoder.flush()
    out.close()

    val serializedAvroBytes: Array[Byte] = out.toByteArray()
    val byteArrayInputStream : ByteArrayInputStream = new ByteArrayInputStream(serializedAvroBytes)

    val encryptor = new Encryptor(new Key(new File("src/main/resources/public-key.gpg")))
    encryptor.setEncryptionAlgorithm(EncryptionAlgorithm.AES256)
    encryptor.setSigningAlgorithm(HashingAlgorithm.Unsigned)
    encryptor.setCompressionAlgorithm(CompressionAlgorithm.ZLIB)

    val encryptedFile : FileOutputStream= new FileOutputStream("src/main/resources/encrypted.pgp")
    encryptor.encrypt(byteArrayInputStream, encryptedFile)

    byteArrayInputStream.close()
    encryptedFile.close()

    println("encryptedFile should be encrypted.")

    val encrypted: FileInputStream = new FileInputStream("src/main/resources/encrypted.pgp")
    val decrypted: ByteArrayOutputStream = new ByteArrayOutputStream()
    val decryptor = new Decryptor(new Key(new File("src/main/resources/secret-key.pgp"),passphrase))
    decryptor.setVerificationRequired(false)

    val decription : DecryptionResult = decryptor.decryptWithFullDetails(encrypted,decrypted)

    println(decription.toString)
    // Deserialize and create generic record
    val message = decrypted.toByteArray
    val reader: DatumReader[GenericRecord] = new SpecificDatumReader[GenericRecord](schema)
    val decoder: Decoder = DecoderFactory.get().binaryDecoder(message, null)
    val someData: GenericRecord = reader.read(null, decoder)
    // Make user object
    val testdata = testRecord(someData.get("data").toString)
    println("The content should be test data. " + testdata + " " + testdata.data.equals("test data"))

    encrypted.close()
    decrypted.close()

  }

}
