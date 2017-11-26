package com.linegrillpresent.studybuddy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import user.Student;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyprofileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyprofileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyprofileFragment extends Fragment implements View.OnClickListener {

    private String username;
    private String name;
    private String email;
    private int numGroups;

    private OnFragmentInteractionListener mListener;

    public MyprofileFragment() {
        // Required empty public constructor
    }

    public static MyprofileFragment newInstance(Student stu) {
        MyprofileFragment fragment = new MyprofileFragment();
        // Bundle args = new Bundle();
        // args.putSerializable("student", stu);
        // fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Student student = (Student) getArguments().getSerializable(
                "student");
    */
        Student student = Student.getInstance();
        name = student.getName();
        username = student.getUsername();
        email = student.getEmail();
        numGroups = student.getNumberOfGroups();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_myprofile, container, false);


        Button r = (Button) view.findViewById(R.id.btn_refreshProfile);
        r.setOnClickListener(this);

        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvUsername = (TextView) view.findViewById(R.id.tv_username);
        TextView tvEmail = (TextView) view.findViewById(R.id.tv_email);
        TextView tvnumGroups = (TextView) view.findViewById(R.id.tvnumGroup);

        tvName.setText("Hello " + name + ",");
        tvUsername.setText("Your username is: " + username);
        tvEmail.setText("Your email is: " + email);
        tvnumGroups.setText("You are currently in " + numGroups + " groups!");

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

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "BUTTON5", Toast.LENGTH_SHORT).show();
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
