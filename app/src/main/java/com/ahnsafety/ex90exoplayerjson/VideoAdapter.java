package com.ahnsafety.ex90exoplayerjson;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<VideoItem> videoItems;

    DataSource.Factory factory;
    ProgressiveMediaSource.Factory mediaFactory;

    public VideoAdapter(Context context, ArrayList<VideoItem> videoItems) {
        this.context = context;
        this.videoItems = videoItems;

        factory= new DefaultDataSourceFactory(context, "Ex90Exoplayer");
        mediaFactory= new ProgressiveMediaSource.Factory(factory);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        VH holder= new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh= (VH)holder;

        VideoItem videoItem= videoItems.get(position);
        vh.tvTitle.setText(videoItem.getTitle());
        vh.tvSubTitle.setText(videoItem.getSubTitle());
        vh.tvDesc.setText(videoItem.getDesc());

        //플레이어가 실행할 비디오데이터 소스객체 생성( CD or LP ), 미디어팩토리로 부터....
        ProgressiveMediaSource mediaSource= mediaFactory.createMediaSource(Uri.parse(videoItem.videoUrl));
        //위에서 만든 비디오데이터 소스를 플레이어에게 로딩하도록....
        vh.player.prepare(mediaSource);

    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    //inner class..
    class VH extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvSubTitle;
        TextView tvDesc;

        PlayerView pv;
        SimpleExoPlayer player;

        Button btnFull;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvTitle= itemView.findViewById(R.id.tv_title);
            tvSubTitle= itemView.findViewById(R.id.tv_subtitle);
            tvDesc= itemView.findViewById(R.id.tv_desc);

            pv= itemView.findViewById(R.id.pv);
            player= ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
            pv.setPlayer(player);


            btnFull= itemView.findViewById(R.id.btn);
            btnFull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    VideoItem videoItem= videoItems.get(getLayoutPosition());
                    String videoUrl= videoItem.getVideoUrl();
                    long currentPos= player.getCurrentPosition();

                    //전체화면 액티비티로 전환
                    Intent intent= new Intent(context, FullScreenActivity.class);
                    intent.putExtra("videoUrl", videoUrl);
                    intent.putExtra("currentPos", currentPos);
                    context.startActivity(intent);
                }
            });

        }
    }


    //아이템뷰가 화면에서 보이지 않을 때...
    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        VH vh= (VH)holder;

        //일시정지..
        vh.player.setPlayWhenReady(false);
    }

    //화면에 보여질때..
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        VH vh= (VH)holder;
        //vh.player.setPlayWhenReady(true);
    }
}
