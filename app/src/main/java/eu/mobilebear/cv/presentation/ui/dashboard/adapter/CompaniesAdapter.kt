package eu.mobilebear.cv.presentation.ui.dashboard.adapter

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.mobilebear.cv.R
import eu.mobilebear.cv.networking.response.Company
import kotlinx.android.synthetic.main.item_company.view.*

class CompaniesAdapter : ListAdapter<Company, CompanyViewHolder>(DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_company, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFFER = object : DiffUtil.ItemCallback<Company>() {
            override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CompanyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(company: Company) {
        Glide.with(view.context).load(company.logo).into(view.companyItemLogo)
        view.companyItemName.text = company.name
        view.companyItemRole.text = company.role
        view.companyItemTime.text = company.dateFromTo
        view.companyItemLocation.text = company.location
        view.companyItemDescription.text = company.description
        view.companyItemTechnologies.text = company.technologies
        view.companyItemGooglePlayUrl.text =
            Html.fromHtml("<a href=\"" + company.googlePlayUrl + "\">" + view.context.getString(R.string.go_to_google_play) + "</a>")
        view.companyItemGooglePlayUrl.movementMethod = LinkMovementMethod.getInstance()
    }
}
