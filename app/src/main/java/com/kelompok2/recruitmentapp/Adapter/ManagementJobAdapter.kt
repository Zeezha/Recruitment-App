package com.kelompok2.recruitmentapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.kelompok2.recruitmentapp.JobdetailFragment
import com.kelompok2.recruitmentapp.Model.Job
import com.kelompok2.recruitmentapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ManagementJobAdapter(private var mContext: Context,
                 private  var mJob: List<Job>,
                private var uid : String): RecyclerView.Adapter<ManagementJobAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagementJobAdapter.ViewHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.management_job_item_layout, parent, false)
        return ManagementJobAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mJob.size
    }

    override fun onBindViewHolder(holder: ManagementJobAdapter.ViewHolder, position: Int) {
        val job = mJob[position]
        Picasso.get().load(job.getImage()).placeholder(R.drawable.bag).into(holder.joblogo)
        holder.jobtitletextview.text = job.getJobtitle()
        holder.jobtimetextview.text = job.getJobtype()
        holder.jobcategorytextview.text = job.getCategory()
        holder.jobsalarytextview.text = job.getSalary()
        holder.jobcompanynametextview.text = job.getCompanyname()
        holder.joblocationtextview.text = job.getJoblocation()


        val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

       if(job.getUid()==firebaseUser.uid){
           holder.deletebutton.visibility = View.VISIBLE
           holder.deletebutton.setOnClickListener(View.OnClickListener {

               val ref = FirebaseDatabase.getInstance().reference.child("Jobs").child(job.getCategory()).child(job.getTime()
               )
               ref.removeValue().addOnCompleteListener { task ->
                   if (task.isSuccessful)
                   {
                       Toast.makeText(mContext,"Job deleted successfully..", Toast.LENGTH_LONG).show()

                   }
               }
           })
       }else{
           holder.deletebutton.visibility = View.GONE
       }



    }

    class ViewHolder(@NonNull itemView : View): RecyclerView.ViewHolder(itemView)
    {
        var joblogo: CircleImageView = itemView.findViewById(R.id.logo)
        var jobtitletextview: TextView = itemView.findViewById(R.id.text_job)
        var jobtimetextview: TextView = itemView.findViewById(R.id.time)
        var jobcategorytextview: TextView = itemView.findViewById(R.id.industry)
        var jobsalarytextview: TextView = itemView.findViewById(R.id.money)
        var jobcompanynametextview: TextView = itemView.findViewById(R.id.companyname)
        var joblocationtextview: TextView = itemView.findViewById(R.id.place)
        var deletebutton:ImageView = itemView.findViewById(R.id.delete)

    }

}