package fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Config.ConstValue;
import adapters.ServiceAdapter;
import ikbal_jimmy.shareservices.R;
import imgLoader.AnimateFirstDisplayListener;
import imgLoader.JSONParser;
import util.ConnectionDetector;
import util.ObjectSerializer;

//import com.servproapp.BookAppointmentActivity;
//import com.servproapp.R;

public class ServicesFragment extends Fragment{
	Activity act;
	public SharedPreferences settings;
	public ConnectionDetector cd;
	ArrayList<HashMap<String, String>> service_array;
	ServiceAdapter service_adapter;
	ListView service_listview;
	DisplayImageOptions options;
	ImageLoaderConfiguration imgconfig;

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		View rootView = inflater.inflate(R.layout.fragment_services, container, false);
		act =getActivity();
		settings = act.getSharedPreferences(ConstValue.MAIN_PREF, 0);
		cd=new ConnectionDetector(act);


		File cacheDir = StorageUtils.getCacheDirectory(act);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new SimpleBitmapDisplayer())
				.imageScaleType(ImageScaleType.NONE)
				.build();

		imgconfig = new ImageLoaderConfiguration.Builder(act)
				.build();
		ImageLoader.getInstance().init(imgconfig);

		service_array = new ArrayList<HashMap<String,String>>();
		/*
		try {
        	service_array = (ArrayList<HashMap<String,String>>) ObjectSerializer.deserialize(settings.getString("services", ObjectSerializer.serialize(new ArrayList<HashMap<String,String>>())));
            //'{"error":false,"services":[{"id_service":1,"titre":"1","active":8,"description":"b"},{"id_service":2,"titre":"Service :P","active":1,"description":"description"},{"id_service":3,"titre":"demenagement","active":1,"description":"aide  audemagemen"},{"id_service":4,"titre":"demenagement","active":1,"description":"aide  audemagemen"},{"id_service":5,"titre":"demenagement","active":1,"description":"aide  audemagemen"},{"id_service":6,"titre":"demenagement","active":1,"description":"aide  audemagemen"}]}';
        }catch (IOException e) {
            e.printStackTrace();
 		}
 		*/
		service_array = getLocalCategoryServices();


		service_listview = (ListView)rootView.findViewById(R.id.listView1);
		service_adapter = new ServiceAdapter(act, service_array);
		service_listview.setAdapter(service_adapter);
		service_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				// TODO Auto-generated method stub
					settings.edit().putString("choosen_date", null).commit();

					String  id_category = service_array.get(arg2).get("id_category_service");
					Fragment fragment = new ServiceCategoryFragment();

					Bundle args = new Bundle();
					args.putString("id_category", id_category);
					fragment.setArguments(args);
					//args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			}
		});

		new loadDrListTask().execute(true);

		return rootView;
	}


	public class loadDrListTask extends AsyncTask<Boolean, Void, ArrayList<HashMap<String, String>>> {

		JSONParser jParser;
		JSONObject json;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			// TODO Auto-generated method stub
			if (result!=null) {
				//adapter.notifyDataSetChanged();
			}
			try {
				settings.edit().putString("services",ObjectSerializer.serialize(service_array)).commit();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			service_adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled(ArrayList<HashMap<String, String>> result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
		}


		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				Boolean... params) {
			// TODO Auto-generated method stub

			try {
				jParser = new JSONParser();

				if(cd.isConnectingToInternet())
				{
					String urlstring = ConstValue.JSON_SERVICES;

					json = jParser.getJSONFromUrl(urlstring);
					if (json.has("services")) {

						if(json.get("services") instanceof JSONArray){

							JSONArray jsonDrList = json.getJSONArray("services");

							service_array.clear();

							for (int i = 0; i < jsonDrList.length(); i++) {
								JSONObject obj = jsonDrList.getJSONObject(i);
								HashMap<String, String> map = new HashMap<String, String>();


								map.put("id", obj.getString("id"));

								map.put("title", obj.getString("title"));
								map.put("image", obj.getString("image"));
								map.put("status", obj.getString("status"));
								map.put("order", obj.getString("order"));
								service_array.add(map);


							}
						}

					}
				}else
				{
					Toast.makeText(act, "Please connect mobile with working Internet", Toast.LENGTH_LONG).show();
				}

				jParser = null;
				json = null;

			} catch (Exception e) {
				// TODO: handle exception

				return null;
			}
			return null;
		}

	}

	public static ArrayList<HashMap<String,String>> getLocalCategoryServices(){
		ArrayList<HashMap<String,String>> category_service_array = new ArrayList<HashMap<String,String>>();

		String  id_category_service;
		String  name;
		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "8");
					put("name", "Service de nettoyage Maison");
					put("image", "1423488992.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);
		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "1");
					put("name", "Réparation d'appareils");
					put("image", "1422618574serviceappliancerepair2x.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "2");
					put("name", "Nettoyage");
					put("status", "1");
					put("image", "1422868163.jpg");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "3");
					put("name", "Electricité");
					put("image", "1423488805.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "4");
					put("name", "Poubelles");
					put("image", "1423488846.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "5");
					put("name", "Bricolare");
					put("image", "1423488877.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "6");
					put("name", "Chauffage ");
					put("image", "1423488922.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "7");
					put("name", "Lumière");
					put("image", "1423488957.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "8");
					put("name", "Service de nettoyage Maison");
					put("image", "1423488992.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "9");
					put("name", "Service Plomberie");
					put("image", "1423489020.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		category_service_array.add(
				new HashMap<String, String>() {{
					put("id_category_service", "10");
					put("name", "Service de déménagement");
					put("image", "1424064244.jpg");
					put("status", "1");
					put("order", "0");
				}}
		);

		return category_service_array;
	}

}
