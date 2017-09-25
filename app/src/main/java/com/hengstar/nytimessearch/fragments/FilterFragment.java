package com.hengstar.nytimessearch.fragments;


import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.hengstar.nytimessearch.R;
import com.hengstar.nytimessearch.models.FilterOptions;
import com.hengstar.nytimessearch.utils.Constants;
import com.hengstar.nytimessearch.utils.Utils;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends DialogFragment
                            implements DatePickerDialog.OnDateSetListener,
                                       EditText.OnClickListener {

    private Unbinder unbinder;
    // Store query to pass back
    private String query;

    DatePickerFragment datePickerFragment;

    @BindView(R.id.etBeginDate) EditText etBeginDate;
    @BindView(R.id.spSortOrder) Spinner spSortOrder;
    @BindView(R.id.btnSave) Button btnSave;
    @BindView(R.id.cbArt) CheckBox cbArt;
    @BindView(R.id.cbFashionStyle) CheckBox cbFashionStyle;
    @BindView(R.id.cbSports) CheckBox cbSports;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(Constants.IntentParams.QUERY);
        }
    }

    // Defines the listener interface with a method passing back data result.
    public interface FilterOptionsDialogListener {
        void onFilter(String query, FilterOptions filterOptions);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilterFragment.
     */
    public static FilterFragment newInstance(@NonNull String query) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(Constants.IntentParams.QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        unbinder = ButterKnife.bind(this, view);
        etBeginDate.setOnClickListener(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveFilter(view);
            }
        });

        return view;
    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        etBeginDate.setText(Utils.getSimpleDateFormat().format(c.getTime()));
    }

    @Override
    public void onClick(View view) {
        if (datePickerFragment == null) {
            datePickerFragment = DatePickerFragment.newInstance();
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(datePickerFragment, Constants.Tags.DATE_PICKER_FRAGMENT).commit();
    }


    private void onSaveFilter(View view) {
        FilterOptions filterOptions = new FilterOptions();
        try {
            filterOptions.setBeginDate(Utils.getSimpleDateFormat()
                    .parse(etBeginDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sortOrderString = spSortOrder.getSelectedItem().toString();
        FilterOptions.SortOrder sortOrder;
        Resources resources = view.getContext().getResources();
        if (sortOrderString.equals(resources.getString(R.string.oldest))) {
            sortOrder = FilterOptions.SortOrder.OLDEST;
        } else if (sortOrderString.equals(resources.getString(R.string.newest))){
            sortOrder = FilterOptions.SortOrder.NEWEST;
        } else {
            sortOrder = FilterOptions.SortOrder.NONE;
        }
        filterOptions.setSortOrder(sortOrder);

        filterOptions.setNewsDeskValue(FilterOptions.NewsDeskValue.ART, cbArt.isChecked());
        filterOptions.setNewsDeskValue(FilterOptions.NewsDeskValue.FASHION_STYLE, cbFashionStyle.isChecked());
        filterOptions.setNewsDeskValue(FilterOptions.NewsDeskValue.SPORTS, cbSports.isChecked());

        ((FilterOptionsDialogListener) getActivity()).onFilter(query, filterOptions);
        dismiss();
    }
}
