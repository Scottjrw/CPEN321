package com.linegrillpresent.studybuddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import user.Student;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MycourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MycourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MycourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MycourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MycourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MycourseFragment newInstance(String param1, String param2) {
        MycourseFragment fragment = new MycourseFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mycourse, container, false);
        ListView listView = (ListView) view.findViewById(R.id.course_listview);
        TextView overview = (TextView) view.findViewById(R.id.course_tv_overview);
        Button btm = (Button) view.findViewById(R.id.btm_jnc);
        Student student = Student.getInstance();
        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(getActivity(), RegisterNewCourse.class);
                getActivity().startActivity(userMainIntent);
            }
        });

        overview.setText("You have registered for " + student.getNumberOfCourses() + " courses:");
        Log.d("onclickcourse", "onCreateView: " + student.getNumberOfCourses());
        final String[] listItems = new String[student.getNumberOfCourses()];
        for (int i = 0; i < student.getNumberOfCourses(); i++)
            listItems[i] = student.getCourses().get(i).getFullName();
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.list_view, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectCourseName = listItems[position];
                Log.d("click", selectCourseName);
                Intent showGroupInfoIntent = new Intent(getActivity(), ShowClassInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("course_name", selectCourseName);
                showGroupInfoIntent.putExtras(bundle);
                getActivity().startActivity(showGroupInfoIntent);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
}
