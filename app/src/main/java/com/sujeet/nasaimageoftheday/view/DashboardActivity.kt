package com.sujeet.nasaimageoftheday.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.sujeet.nasaimageoftheday.BuildConfig
import com.sujeet.nasaimageoftheday.R
import com.sujeet.nasaimageoftheday.databinding.ActivityDashboardBinding
import com.sujeet.nasaimageoftheday.model.ImageResponse
import com.sujeet.nasaimageoftheday.utils.AppController
import com.sujeet.nasaimageoftheday.utils.BlockMultipleClick
import com.sujeet.nasaimageoftheday.utils.PreferenceHelper
import com.sujeet.nasaimageoftheday.utils.dialogBox.DescriptionDialog
import com.sujeet.nasaimageoftheday.utils.dialogBox.LoadingDialogue
import com.sujeet.nasaimageoftheday.viewModel.DashboardViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModel: DashboardViewModel

    var descriptionText:String = ""
    var videoUrl:String = ""
    var todayDate : String = ""

    private lateinit var playerView: PlayerView
    private lateinit var player: SimpleExoPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Image of the Day"


        binding.refresh.setOnClickListener(this)
        binding.description.setOnClickListener(this)
        binding.playVideo.setOnClickListener(this)

        playerView = binding.playerView
        player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player


        /*** To fetch the current date & convert in YYYY-MM-DD format to pass in the API call ***/
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
         todayDate = currentDate.format(formatter)

        /*** Checking If Data is available in the cache, display it ***/
        if (PreferenceHelper.getImageDetails(this) != null) {
            setUpUI(PreferenceHelper.getImageDetails(this)!!)
            Log.d("dataFetchedFrom","local data fetched")

        }else{
            /*** Call Image API If Data is not available in the cache ***/
            if (AppController.isThereInternetSpeed(this)) {
                callImageApi(todayDate)
            }else {
                Toast.makeText(this, "Please check your internet connection.Your internet speed is less than 16 kbps!", Toast.LENGTH_SHORT).show()
            }

        }



        /*** Image API Observer ***/
        viewModel.imageLiveData.observe(this, Observer {
            LoadingDialogue.dismissDialog()
            if (it != null){

                /*** storing response in PreferenceHelper for local use ***/
                PreferenceHelper.setImageDetails(this, it)

                setUpUI(it)
                Log.d("dataFetchedFrom","API data fetched")

            }else{
                Toast.makeText(this, "Something went wrong, please try again later!", Toast.LENGTH_SHORT).show()
            }
        })


    }

    /*** Here Created one common function to set UI data, called in both place API & Local ***/
    private fun setUpUI(data: ImageResponse) {

        if (data.media_type.equals("video",true)){

            binding.playVideo.visibility = View.VISIBLE
            binding.playerView.visibility = View.VISIBLE
            binding.image.visibility = View.GONE
            videoUrl = data.hdurl.toString()

        }else{
            binding.playVideo.visibility = View.GONE
            binding.playerView.visibility = View.GONE
            binding.image.visibility = View.VISIBLE

            if (data.url != null){
                Glide.with(this)
                    .load(data.url)
                    .placeholder(R.drawable.ic_default_img)
                    .fitCenter()
                    .into(binding.image)
            }
        }


        binding.title.text = data.title
        binding.date.text = AppController.dateAPIFormats(data.date)
        descriptionText = data.explanation.toString()
    }

    /*** Here created function to call the API */
    private fun callImageApi(todayDate: String) {
        LoadingDialogue.showDialog(this)
        viewModel.getImageData(
            BuildConfig.APIKey, todayDate
        )
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.refresh ->{
                if(BlockMultipleClick.click()) return
                /*** Call Image API on Click of "Refresh" button ***/
                if (AppController.isThereInternetSpeed(this)) {
                    callImageApi(todayDate)
                }else {
                    Toast.makeText(this, "Please check your internet connection.Your internet speed is less than 16 kbps!", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.description ->{
                if(BlockMultipleClick.click()) return
                if (!descriptionText.isNullOrEmpty()){
                    /*** Display Popup for Image Description ***/
                    DescriptionDialog.showDescriptionDialog(this,descriptionText, object : DescriptionDialog.DescriptionDialogCallBack{
                        override fun onOk() {}
                    })
                }else{
                    Toast.makeText(this, "Image description not found!", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.playVideo ->{
                if(BlockMultipleClick.click()) return

                if (player.isPlaying){
                    player.pause()
                    binding.playVideo.text = "Start Video"

                }else{
                    val mediaItem = MediaItem.fromUri(videoUrl)
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.play()
                }

            }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        LoadingDialogue.dismissDialog()
        player.release()
    }

}