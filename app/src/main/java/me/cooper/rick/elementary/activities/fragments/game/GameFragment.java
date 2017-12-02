package me.cooper.rick.elementary.activities.fragments.game;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.cooper.rick.elementary.R;
import me.cooper.rick.elementary.listeners.AcceleroListener;
import me.cooper.rick.elementary.models.ChemicalSymbolView;
import me.cooper.rick.elementary.models.ElementAnswerView;
import me.cooper.rick.elementary.models.Player;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment implements Runnable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout content;
    private ChemicalSymbolView chemicalSymbolView;
    private List<ElementAnswerView> answerViews = new ArrayList<>();
    private Player player;
    private Point size;
    private Point centre;
    private MovementManager movementManager;
    private QuizManager quizManager = QuizManager.getInstance();
    private Handler handler;
//    private DatabaseReference dbRef;

    Thread thread;
    boolean isRunning = true;

    private OnFragmentInteractionListener mListener;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        handler = new Handler();
        thread = new Thread(this);
//        dbRef = FirebaseDatabase.getInstance().getReference("scores");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);

        if(size == null) {
            size = new Point(container.getMeasuredWidth(), container.getMeasuredHeight());
            centre = new Point(size.x / 2, size.y / 2);
        }

        content = view.findViewById(R.id.game_space);
        content.setGravity(Gravity.CENTER);

        setAnswerViewReferences(view);
        addChemicalSymbolView();

        reset();

        return view;
    }

    private void setAnswerViewReferences(View view) {
        answerViews.add((ElementAnswerView) view.findViewById(R.id.answer_top));
        answerViews.add((ElementAnswerView) view.findViewById(R.id.answer_bottom));
        answerViews.add((ElementAnswerView) view.findViewById(R.id.answer_left));
        answerViews.add((ElementAnswerView) view.findViewById(R.id.answer_right));
    }

    private void addChemicalSymbolView() {
        chemicalSymbolView = new ChemicalSymbolView(getActivity(), centre, size);
        content.addView(chemicalSymbolView);
    }

    @Override
    public void onStart() {
        super.onStart();
        thread.start();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ElementAnswerView collidedView = checkCollisions();
                if (collidedView != null) {
                    if (quizManager.isCorrectAnswer(collidedView.getAnswer())) {
                        player.incrementLives();
                        Log.d("RICK", "YEEEEESSSSS");
                    } else {
                        player.decrementLives();
                        if (player.getLives() <= 0) {
                            Log.d("RICK", "NOOOOOOOOOOOOOOOOO");
                        }

                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            movementManager = new MovementManager(sensorManager, chemicalSymbolView);
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(new ShakeListener(), sensor, SensorManager.SENSOR_DELAY_GAME);
        }

//        thread = new Thread(this);
//        thread.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toggle) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        while (isRunning) {
            ElementAnswerView collidedView = checkCollisions();
            if (collidedView != null) {
                if (quizManager.isCorrectAnswer(collidedView.getAnswer())) {
                    Log.d("RICK", "YEEEEESSSSS");
                } else {
                    Log.d("RICK", "NOOOOOOOOOOOOOOOOO");
                }
            }
        }
    }

    private void reset() {
        quizManager.resetAnswers();

        resetChemicalSymbolView();
        resetAnswerViews();
    }

    private void resetChemicalSymbolView() {
        chemicalSymbolView.reset(quizManager.getTargetElement());
    }

    private void resetAnswerViews() {
        List<Pair<String, String>> answers = quizManager.getAnswers();
        Collections.shuffle(answers);

        for (int i = 0; i < 4; ++i) {
            answerViews.get(i).setAnswer(answers.get(i));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private ElementAnswerView checkCollisions() {
        for (ElementAnswerView view : answerViews) {
            if (view.isIntersecting(chemicalSymbolView)) {
                return view;
            }
        }
        return null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private final class ShakeListener extends AcceleroListener implements SensorEventListener {

        ShakeListener() {
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            super.onSensorChanged(event);

            if (event.sensor.getType() == SENSOR_TYPE && isShake()) {
                movementManager.activateNextMoveStrategy();
            }
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void makeToast(final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
