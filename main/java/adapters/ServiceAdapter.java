package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Config.ConstValue;
import ikbal_jimmy.shareservices.R;
import imgLoader.AnimateFirstDisplayListener;

public class ServiceAdapter extends BaseAdapter {
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private Context context;
	private ArrayList<HashMap<String, String>> postItems;
	public SharedPreferences settings;
	public final String PREFS_NAME = "Magazine";
	Double cLat,cLog;
	DisplayImageOptions options;
	ImageLoaderConfiguration imgconfig;
	int count = 0;
	TextView txtLikes;
	private int lastPosition = -1;
	public ServiceAdapter(Context context, ArrayList<HashMap<String, String>> arraylist){
		this.context = context;
		
		File cacheDir = StorageUtils.getCacheDirectory(context);
		options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new SimpleBitmapDisplayer())
				.imageScaleType(ImageScaleType.EXACTLY)
				.build();
		
		imgconfig = new ImageLoaderConfiguration.Builder(context)
		.build();
		ImageLoader.getInstance().init(imgconfig);
			
		postItems = arraylist;
		settings = context.getSharedPreferences(PREFS_NAME, 0);		
		
	}

	@Override
	public int getCount() {
		return postItems.size();
	}
	@Override
	public Object getItem(int position) {		
		return postItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater)
			context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.row_services, null);
		}

		Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		convertView.startAnimation(animation);
		lastPosition = position;

		HashMap<String, String> map = new HashMap<String, String>();
		map = postItems.get(position);

		TextView txttitle = (TextView)convertView.findViewById(R.id.textView1);
		txttitle.setText(map.get("name"));

		ImageView imgIcon = (ImageView)convertView.findViewById(R.id.imageView1);
		ImageLoader.getInstance().displayImage(ConstValue.IMAGE_PATH+map.get("image"), imgIcon, options, animateFirstListener);

        return convertView;
	}


}

