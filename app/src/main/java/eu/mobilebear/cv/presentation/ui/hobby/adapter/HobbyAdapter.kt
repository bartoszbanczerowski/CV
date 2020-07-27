package eu.mobilebear.cv.presentation.ui.hobby.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import eu.mobilebear.cv.R
import eu.mobilebear.cv.networking.response.Hobby
import kotlinx.android.synthetic.main.item_hobby.view.*

class HobbyAdapter : ListAdapter<Hobby, HobbyViewHolder>(DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hobby, parent, false)
        return HobbyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFFER = object : DiffUtil.ItemCallback<Hobby>() {
            override fun areItemsTheSame(oldItem: Hobby, newItem: Hobby): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Hobby, newItem: Hobby): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class HobbyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetJavaScriptEnabled")
    fun bind(hobby: Hobby) {
        view.hobbyItemName.text = hobby.name
        view.hobbyItemDescription.text = hobby.description
        view.hobbyItemYoutube.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(hobby.url, 0f)
            }
        })
    }
}

