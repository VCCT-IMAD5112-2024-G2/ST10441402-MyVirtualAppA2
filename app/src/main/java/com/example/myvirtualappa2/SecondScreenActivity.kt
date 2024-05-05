package com.example.myvirtualappa2

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myvirtualappa2.R

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var petImageView: ImageView
    private lateinit var eatingImageView: ImageView
    private lateinit var cleaningImageView: ImageView
    private lateinit var playingImageView: ImageView
    private lateinit var mainImageView: ImageView
    private lateinit var sadImageView: ImageView
    private lateinit var feedButton: Button
    private lateinit var cleanButton: Button
    private lateinit var playButton: Button
    private lateinit var healthProgressBar: ProgressBar
    private lateinit var hungerProgressBar: ProgressBar
    private lateinit var cleanlinessProgressBar: ProgressBar
    private lateinit var playProgressBar: ProgressBar

    private var pet: Pet = Pet()
    private var isFeeding: Boolean = false
    private var isCleaning: Boolean = false
    private var isPlaying: Boolean = false
    private var healthDecreaseHandler = Handler()
    private var healthDecreaseRunnable: Runnable? = null
    private var fullHealthHandler = Handler()
    private var fullHealthRunnable: Runnable? = null
    private var activeClicks = 0
    private var imageVisibilityHandler = Handler()
    private var mainImageViewHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_screen_activity)
        enableEdgeToEdge()

        petImageView = findViewById(R.id.petImageView)
        eatingImageView = findViewById(R.id.eatingImageView)
        playingImageView = findViewById(R.id.playingImageView)
        mainImageView = findViewById(R.id.mainImageView)
        sadImageView = findViewById(R.id.sadImageView)
        feedButton = findViewById(R.id.feedButton)
        cleanButton = findViewById(R.id.cleanButton)
        playButton = findViewById(R.id.playButton)
        healthProgressBar = findViewById(R.id.healthProgressBar)
        hungerProgressBar = findViewById(R.id.eatingProgressBar)
        cleanlinessProgressBar = findViewById(R.id.cleaningProgressBar)
        playProgressBar = findViewById(R.id.playingProgressBar)
        cleaningImageView = findViewById(R.id.cleaningImageView)

        eatingImageView.visibility = View.GONE
        playingImageView.visibility = View.GONE
        petImageView.visibility = View.INVISIBLE
        sadImageView.visibility = View.INVISIBLE
        mainImageView.visibility = View.VISIBLE
        cleaningImageView.visibility = View.GONE

        feedButton.setOnClickListener {
            Log.d("SecondScreenActivity", "Feed button clicked")
            pet.feed()
            isFeeding = true
            isCleaning = false
            isPlaying = false
            updateImageVisibility()
            updateHealthProgressBar()
            updateHungerProgressBar()
            updateActiveClicks()
            // Hide sad and main image view
            hideSadAndMainImageView()

            // Show eatingImageView and hide other image views
            eatingImageView.visibility = View.VISIBLE
            cleaningImageView.visibility = View.INVISIBLE
            playingImageView.visibility = View.INVISIBLE
            mainImageView.visibility = View.INVISIBLE
        }

        cleanButton.setOnClickListener {
            Log.d("SecondScreenActivity", "Clean button clicked")
            pet.clean()
            isCleaning = true
            isFeeding = false
            isPlaying = false
            updateImageVisibility()
            updateHealthProgressBar()
            updateCleanlinessProgressBar()
            updateActiveClicks()
            // Hide sad and main image view
            hideSadAndMainImageView()

            // Show cleaningImageView and hide other image views
            cleaningImageView.visibility = View.VISIBLE
            eatingImageView.visibility = View.GONE
            playingImageView.visibility = View.GONE
            mainImageView.visibility = View.INVISIBLE
        }

        playButton.setOnClickListener {
            Log.d("SecondScreenActivity", "Play button clicked")
            pet.play()
            isPlaying = true
            isFeeding = false
            isCleaning = false
            updateImageVisibility()
            updateHealthProgressBar()
            updateHungerProgressBar()
            updateCleanlinessProgressBar()
            updatePlayProgressBar()
            updateActiveClicks()
            // Hide sad and main image view
            hideSadAndMainImageView()

            // Show playingImageView and hide other image views
            playingImageView.visibility = View.VISIBLE
            eatingImageView.visibility = View.GONE
            cleaningImageView.visibility = View.GONE
            mainImageView.visibility = View.INVISIBLE
        }

        updateHealthProgressBar()
        updateHungerProgressBar()
        updateCleanlinessProgressBar()
        updatePlayProgressBar()

        // Hide main image view after 3 seconds if no buttons are active
        Handler().postDelayed({
            if (activeClicks == 0) {
                mainImageView.visibility = View.GONE

            }
        }, 3000)

        // Show main image view after 10 seconds
        mainImageViewHandler.postDelayed({
            mainImageView.visibility = View.VISIBLE
            playingImageView.visibility = View.INVISIBLE
            eatingImageView.visibility = View.INVISIBLE
            cleaningImageView.visibility = View.INVISIBLE
        }, 10000)

        // Schedule health decrease task every 4 seconds
        healthDecreaseRunnable = Runnable {
            pet.decreaseHealth(5)
            updateHealthProgressBar()
            healthDecreaseHandler.postDelayed(healthDecreaseRunnable!!, 4000)
        }
        healthDecreaseHandler.postDelayed(healthDecreaseRunnable!!, 4000)
    }

    private fun updateImageVisibility() {
        if (activeClicks == 0) {
            eatingImageView.visibility = View.GONE
            cleaningImageView.visibility = View.GONE
            playingImageView.visibility = View.GONE
            mainImageView.visibility = View.VISIBLE
        } else {
            eatingImageView.visibility = if (isFeeding) View.VISIBLE else View.GONE
            cleaningImageView.visibility = if (isCleaning) View.VISIBLE else View.GONE
            playingImageView.visibility = if (isPlaying) View.VISIBLE else View.GONE
        }
    }

    private fun updateHealthProgressBar() {
        healthProgressBar.progress = pet.health
        Log.d("SecondScreenActivity", "Health ProgressBar updated")

        // Show full health indicator for 8 seconds when health is full
        if (pet.health == 100) {
            fullHealthHandler.removeCallbacksAndMessages(null)
            fullHealthRunnable = Runnable {
                healthProgressBar.visibility = View.INVISIBLE
                petImageView.visibility = View.VISIBLE
                sadImageView.visibility = View.INVISIBLE
            }
            fullHealthHandler.postDelayed(fullHealthRunnable!!, 8000)
        } else {
            healthProgressBar.visibility = View.VISIBLE
            fullHealthHandler.removeCallbacksAndMessages(null)

            // Show sad image when health is below half
            if (pet.health <= 50) {
                sadImageView.visibility = View.VISIBLE
                mainImageView.visibility = View.INVISIBLE
                playingImageView.visibility = View.INVISIBLE
                eatingImageView.visibility = View.INVISIBLE
                cleaningImageView.visibility = View.INVISIBLE
            } else {
                sadImageView.visibility = View.INVISIBLE
                mainImageView.visibility = View.VISIBLE
                playingImageView.visibility = View.INVISIBLE
                eatingImageView.visibility = View.INVISIBLE
                cleaningImageView.visibility = View.INVISIBLE
            }
        }
    }

    private fun updateHungerProgressBar() {
        hungerProgressBar.progress = pet.hunger
        Log.d("SecondScreenActivity", "Hunger ProgressBar updated")
    }

    private fun updateCleanlinessProgressBar() {
        cleanlinessProgressBar.progress = pet.cleanliness
        Log.d("SecondScreenActivity", "Cleanliness ProgressBar updated")
    }

    private fun updatePlayProgressBar() {
        playProgressBar.progress = pet.playfulness
        Log.d("SecondScreenActivity", "Play ProgressBar updated")
    }

    private fun updateActiveClicks() {
        activeClicks = listOf(isFeeding, isCleaning, isPlaying).count { it }
    }

    private fun hideSadAndMainImageView() {
        sadImageView.visibility = View.INVISIBLE
        mainImageView.visibility = View.INVISIBLE
    }

    class Pet {
        var health: Int = 100
        var hunger: Int = 0
        var cleanliness: Int = 0
        var playfulness: Int = 0

        fun feed() {
            health += 10
            if (health > 100) {
                health = 100
            }
            hunger -= 20
            if (hunger < 0) {
                hunger = 0
            }
        }

        fun clean() {
            cleanliness += 20
            if (cleanliness > 100) {
                cleanliness = 100
            }
        }

        fun play() {
            health -= 10
            if (health < 0) {
                health = 0
            }
            hunger += 20
            if (hunger > 100) {
                hunger = 100
            }
            cleanliness -= 10
            if (cleanliness < 0) {
                cleanliness = 0
            }
            playfulness += 30
            if (playfulness > 100) {
                playfulness = 100
            }
        }

        fun decreaseHealth(amount: Int) {
            health -= amount
            if (health < 0) {
                health = 0
            }
        }
    }
}
