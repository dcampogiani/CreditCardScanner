package com.danielecampogiani.creditcardscanner

import android.media.Image
import com.danielecampogiani.creditcardscanner.utils.await
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer

class ExtractDataUseCase(private val textRecognizer: FirebaseVisionTextRecognizer) {

    suspend operator fun invoke(image: Image, rotationDegrees: Int): CardDetails {
        val firebaseImage = FirebaseVisionImage.fromMediaImage(
            image,
            rotationDegreesToFirebaseRotation(rotationDegrees)
        )
        val text = textRecognizer.processImage(firebaseImage).await().text
        return Extractor.extractData(text)
    }

    private fun rotationDegreesToFirebaseRotation(rotationDegrees: Int): Int {
        return when (rotationDegrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw IllegalArgumentException("Not supported")
        }
    }
}