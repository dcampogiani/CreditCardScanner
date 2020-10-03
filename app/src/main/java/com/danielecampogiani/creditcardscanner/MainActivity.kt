package com.danielecampogiani.creditcardscanner

import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.danielecampogiani.creditcardscanner.utils.*
import com.google.mlkit.vision.text.TextRecognition
import kotlinx.android.synthetic.main.activity_main.*

private const val CAMERA_PERMISSION = 7762

@SuppressLint("UnsafeExperimentalUsageError")
class MainActivity : AppCompatActivity() {

    private val useCase = ExtractDataUseCase(TextRecognition.getClient())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cameraPermissions()
    }

    private fun cameraPermissions() {
        if (hasPermission(CAMERA)
        ) {
            launchWhenResumed {
                bindUseCases(getCameraProvider())
            }
        } else {
            requestPermissions(arrayOf(CAMERA), CAMERA_PERMISSION)
        }
    }

    private fun bindUseCases(cameraProvider: ProcessCameraProvider) {
        val preview = buildPreview()
        val takePicture = buildTakePicture()
        val cameraSelector = buildCameraSelector()

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, takePicture)

        button.setOnClickListener {
            launchWhenResumed {
                val imageProxy = takePicture.takePicture(executor)
                val cardDetails = useCase(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
                bindCardDetails(cardDetails)
            }
        }

    }

    private fun buildPreview(): Preview = Preview.Builder()
        .build()
        .apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

    private fun buildCameraSelector(): CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    private fun buildTakePicture(): ImageCapture = ImageCapture.Builder()
        .setTargetRotation(previewView.display.rotation)
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        .build()

    @SuppressLint("SetTextI18n")
    private fun bindCardDetails(card: CardDetails) {
        owner.text = card.owner
        number.text = card.number
        date.text = "${card.expirationMonth}/${card.expirationYear}"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
            launchWhenResumed {
                bindUseCases(getCameraProvider())
            }
        }
    }
}
