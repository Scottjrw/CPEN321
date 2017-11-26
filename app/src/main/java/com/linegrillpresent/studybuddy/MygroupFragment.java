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
 * {@link MygroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MygroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MygroupFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Student student;

    public MygroupFragment() {
        // Required empty public constructor
    }

    public static MygroupFragment newInstance(Student stu) {
        MygroupFragment fragment = new MygroupFragment();
        // Bundle args = new Bundle();
        // args.putSerializable("student", stu);
        // fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        student = Student.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mygroup, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        TextView overview = (TextView) view.findViewById(R.id.tv_overview);
        Button btm = (Button) view.findViewById(R.id.btm_rng);

        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userMainIntent = new Intent(getActivity(), RegisterNewGroup.class);
                getActivity().startActivity(userMainIntent);
            }
        });

        overview.setText("You have registered for " + student.getNumberOfGroups() + " groups:");


        final String[] listItems = new String[student.getNumberOfGroups()];
        for (int i = 0; i < student.getNumberOfGroups(); i++)
            listItems[i] = student.getGroups().get(i);
        //ArrayAdapter adapter = new ArrayAdapter(view, list_view, listItems);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.list_view, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectGroupName = listItems[position];
                Log.d("click", selectGroupName);
                Intent showGroupInfoIntent = new Intent(getActivity(), ShowGroupInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("group_name", selectGroupName);
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
