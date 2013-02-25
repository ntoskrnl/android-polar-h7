package info.danshin.android.sandbox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import info.danshin.android.sandbox.db.HeartRateDataItemDAO;
import info.danshin.android.sandbox.model.HeartRateDataItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A fragment representing a single Session detail screen. This fragment is
 * either contained in a {@link SessionListActivity} in two-pane mode (on
 * tablets) or a {@link SessionDetailActivity} on handsets.
 */
public class SessionDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_SESSION_ID = "session_id";

	private Long sessionId;
	private GraphViewSeries series;
	private HeartRateDataItemDAO hrDAO;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SessionDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_SESSION_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			sessionId = getArguments().getLong(ARG_SESSION_ID, -1L);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_session_detail,
				container, false);
		if (sessionId == -1L) {
			return rootView;
		}
		
		// init example series data
		hrDAO = new HeartRateDataItemDAO(getActivity());
		hrDAO.open();
		List<HeartRateDataItem> items = hrDAO.getAllItemsOfSession(sessionId);
		if (!items.isEmpty()) {
			final DateFormat dateFormatter = new SimpleDateFormat("h:mm:ss");
			GraphViewData[] data = new GraphViewData[items.size()];
			for (int i = 0; i < data.length; i++) {
				data[i] = new GraphViewData(items.get(i).getTimeStamp().getTime(), items.get(i).getHeartBeatsPerMinute());
			}
			series = new GraphViewSeries(data);
			
			// TODO Get data from DB for session with sessionId
			
	
			GraphView graphView = new LineGraphView(getActivity(), "Heart Rate Graph: Session #" + sessionId) {
				@Override  
				protected String formatLabel(double value, boolean isValueX) {  
					if (isValueX) {
						// convert unix time to human time
						return dateFormatter.format(new Date((long) value));
					} else
						return super.formatLabel(value, isValueX); 
				}
				
				@Override
				protected double getMaxY() {
					return super.getMaxY() + 20;
				}
				
				@Override
				protected double getMinY() {
					return Math.max(0.0d, super.getMinY()-20);
				}
			};
			graphView.addSeries(series); // data
			graphView.setScalable(true);
			graphView.setScrollable(true);
			graphView.setViewPort(data[0].valueX, 60*1000);
			
			LinearLayout layout = (LinearLayout) rootView
					.findViewById(R.id.session_detail_layout);
			layout.addView(graphView);
		}

		return rootView;
	}
	
	private GraphViewData[] loadData() {
		List<HeartRateDataItem> items = hrDAO.getAllItemsOfSession(sessionId);
		if (!items.isEmpty()) {
			GraphViewData[] data = new GraphViewData[items.size()];
			for (int i = 0; i < data.length; i++) {
				data[i] = new GraphViewData(items.get(i).getTimeStamp().getTime(), items.get(i).getHeartBeatsPerMinute());
			}
			return data;
		}
		return new GraphViewData[] {};
	}
	
//	@Override
//	public void onResume() {
//		super.onResume();
//		series.resetData(loadData());
//	}
}
